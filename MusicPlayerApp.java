package com.musicplayer.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.*;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;

public class MusicPlayerApp extends Application {

    // Base URL for Spring Boot backend
    private static final String BASE_URL = "http://localhost:8080/api";

    // HTTP Client
    private final HttpClient httpClient = HttpClient.newHttpClient();

    // UI Components
    private ListView<String> songListView;
    private ListView<String> playlistView;
    private Label nowPlayingLabel;
    private Label statusLabel;
    private TextField titleField, artistField, genreField, durationField;
    private ObservableList<String> songs = FXCollections.observableArrayList();
    private ObservableList<String> playlistSongs = FXCollections.observableArrayList();

    // Track current song IDs for operations
    private java.util.List<Long> songIds = new java.util.ArrayList<>();
    private java.util.List<Long> playlistSongIds = new java.util.ArrayList<>();
    private Long currentPlaylistId = 1L;
    private int currentIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("🎵 Music Player - Spring Boot + JavaFX");

        // ---- TOP: Now Playing ----
        VBox topBar = createTopBar();

        // ---- CENTER: Song List + Playlist ----
        HBox centerPanel = createCenterPanel();

        // ---- BOTTOM: Controls + Add Song ----
        VBox bottomPanel = createBottomPanel();

        // ---- STATUS BAR ----
        statusLabel = new Label("Ready. Make sure Spring Boot is running on port 8080.");
        statusLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 11px;");
        statusLabel.setPadding(new Insets(5, 10, 5, 10));

        // ---- ROOT LAYOUT ----
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(centerPanel);
        root.setBottom(new VBox(bottomPanel, statusLabel));
        root.setStyle("-fx-background-color: #1a1a2e;");

        Scene scene = new Scene(root, 900, 650);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial data
        loadAllSongs();
        initPlaylist();
    }

    // ==================== UI BUILDERS ====================

    private VBox createTopBar() {
        VBox topBar = new VBox(5);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #16213e;");

        Label appTitle = new Label("🎵 Music Player");
        appTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        appTitle.setTextFill(Color.WHITE);

        nowPlayingLabel = new Label("♪ No song playing");
        nowPlayingLabel.setFont(Font.font("Arial", 14));
        nowPlayingLabel.setTextFill(Color.LIGHTGREEN);

        topBar.getChildren().addAll(appTitle, nowPlayingLabel);
        return topBar;
    }

    private HBox createCenterPanel() {
        HBox centerPanel = new HBox(15);
        centerPanel.setPadding(new Insets(15));

        // -- All Songs Panel --
        VBox allSongsPanel = new VBox(8);
        allSongsPanel.setPrefWidth(420);

        Label allSongsLabel = new Label("📋 All Songs");
        allSongsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        allSongsLabel.setTextFill(Color.WHITE);

        songListView = new ListView<>(songs);
        songListView.setPrefHeight(300);
        songListView.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white;");

        Button addToPlaylistBtn = createStyledButton("➕ Add to Playlist", "#4caf50");
        Button deleteFromLibraryBtn = createStyledButton("🗑 Delete Song", "#f44336");
        Button refreshBtn = createStyledButton("🔄 Refresh", "#2196F3");

        addToPlaylistBtn.setOnAction(e -> addSelectedSongToPlaylist());
        deleteFromLibraryBtn.setOnAction(e -> deleteSelectedSong());
        refreshBtn.setOnAction(e -> loadAllSongs());

        HBox songButtons = new HBox(8, addToPlaylistBtn, deleteFromLibraryBtn, refreshBtn);
        allSongsPanel.getChildren().addAll(allSongsLabel, songListView, songButtons);

        // -- Playlist Panel --
        VBox playlistPanel = new VBox(8);
        playlistPanel.setPrefWidth(420);

        Label playlistLabel = new Label("🎶 My Playlist");
        playlistLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        playlistLabel.setTextFill(Color.WHITE);

        playlistView = new ListView<>(playlistSongs);
        playlistView.setPrefHeight(300);
        playlistView.setStyle("-fx-background-color: #0f3460; -fx-text-fill: white;");

        Button removeFromPlaylistBtn = createStyledButton("➖ Remove", "#ff9800");
        Button loadPlaylistBtn = createStyledButton("🔄 Load Playlist", "#9c27b0");

        removeFromPlaylistBtn.setOnAction(e -> removeSelectedFromPlaylist());
        loadPlaylistBtn.setOnAction(e -> loadPlaylist());

        HBox playlistButtons = new HBox(8, removeFromPlaylistBtn, loadPlaylistBtn);
        playlistPanel.getChildren().addAll(playlistLabel, playlistView, playlistButtons);

        centerPanel.getChildren().addAll(allSongsPanel, playlistPanel);
        return centerPanel;
    }

    private VBox createBottomPanel() {
        VBox bottomPanel = new VBox(10);
        bottomPanel.setPadding(new Insets(10, 15, 10, 15));
        bottomPanel.setStyle("-fx-background-color: #16213e;");

        // -- Playback Controls --
        Label controlLabel = new Label("▶ Playback Controls");
        controlLabel.setTextFill(Color.WHITE);
        controlLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Button prevBtn = createStyledButton("⏮ Previous", "#607d8b");
        Button playBtn = createStyledButton("▶ Play", "#4caf50");
        Button nextBtn = createStyledButton("⏭ Next", "#607d8b");

        prevBtn.setOnAction(e -> playPrevious());
        playBtn.setOnAction(e -> playSelected());
        nextBtn.setOnAction(e -> playNext());

        HBox controls = new HBox(10, prevBtn, playBtn, nextBtn);
        controls.setAlignment(Pos.CENTER_LEFT);

        // -- Add Song Form --
        Label addLabel = new Label("➕ Add New Song");
        addLabel.setTextFill(Color.WHITE);
        addLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        titleField = createStyledTextField("Song Title");
        artistField = createStyledTextField("Artist Name");
        genreField = createStyledTextField("Genre");
        durationField = createStyledTextField("Duration (seconds)");

        Button addSongBtn = createStyledButton("💾 Save Song", "#4caf50");
        addSongBtn.setOnAction(e -> addNewSong());

        HBox addSongForm = new HBox(8,
            titleField, artistField, genreField, durationField, addSongBtn);

        bottomPanel.getChildren().addAll(
            new Separator(),
            controlLabel, controls,
            new Separator(),
            addLabel, addSongForm
        );
        return bottomPanel;
    }

    // ==================== REST API CALLS ====================

    private void loadAllSongs() {
        setStatus("Loading songs...");
        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/songs"))
                    .GET()
                    .build();
                HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

                JSONArray jsonArray = new JSONArray(response.body());
                Platform.runLater(() -> {
                    songs.clear();
                    songIds.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject song = jsonArray.getJSONObject(i);
                        songs.add("🎵 " + song.getString("title")
                            + " — " + song.getString("artist"));
                        songIds.add(song.getLong("id"));
                    }
                    setStatus("Loaded " + songs.size() + " songs.");
                });
            } catch (Exception e) {
                Platform.runLater(() -> setStatus("❌ Error: " + e.getMessage()
                    + " — Is Spring Boot running?"));
            }
        });
    }

    private void addNewSong() {
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String genre = genreField.getText().trim();
        String durationText = durationField.getText().trim();

        if (title.isEmpty() || artist.isEmpty()) {
            setStatus("⚠ Title and Artist are required!");
            return;
        }

        int duration = 0;
        try {
            duration = Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            duration = 180; // default 3 min
        }

        final int finalDuration = duration;
        setStatus("Adding song...");

        String jsonBody = String.format(
            "{\"title\":\"%s\",\"artist\":\"%s\",\"genre\":\"%s\",\"duration\":%d}",
            title, artist, genre, finalDuration);

        final String body = jsonBody;
        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/songs"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                Platform.runLater(() -> {
                    setStatus("✅ Song added successfully!");
                    titleField.clear();
                    artistField.clear();
                    genreField.clear();
                    durationField.clear();
                    loadAllSongs();
                });
            } catch (Exception e) {
                Platform.runLater(() -> setStatus("❌ Error adding song: " + e.getMessage()));
            }
        });
    }

    private void deleteSelectedSong() {
        int index = songListView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            setStatus("⚠ Please select a song to delete.");
            return;
        }
        Long songId = songIds.get(index);
        setStatus("Deleting song...");

        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/songs/" + songId))
                    .DELETE()
                    .build();
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                Platform.runLater(() -> {
                    setStatus("✅ Song deleted!");
                    loadAllSongs();
                });
            } catch (Exception e) {
                Platform.runLater(() -> setStatus("❌ Error: " + e.getMessage()));
            }
        });
    }

    private void initPlaylist() {
        // Create default playlist if not exists
        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/playlists"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"My Playlist\"}"))
                    .build();
                HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
                JSONObject playlist = new JSONObject(response.body());
                currentPlaylistId = playlist.getLong("id");
                Platform.runLater(() -> loadPlaylist());
            } catch (Exception e) {
                Platform.runLater(() -> loadPlaylist());
            }
        });
    }

    private void loadPlaylist() {
        setStatus("Loading playlist...");
        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/playlists/" + currentPlaylistId))
                    .GET()
                    .build();
                HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
                JSONObject playlist = new JSONObject(response.body());
                JSONArray songsArray = playlist.getJSONArray("songs");

                Platform.runLater(() -> {
                    playlistSongs.clear();
                    playlistSongIds.clear();
                    for (int i = 0; i < songsArray.length(); i++) {
                        JSONObject song = songsArray.getJSONObject(i);
                        playlistSongs.add("🎶 " + song.getString("title")
                            + " — " + song.getString("artist"));
                        playlistSongIds.add(song.getLong("id"));
                    }
                    setStatus("Playlist loaded with " + playlistSongs.size() + " songs.");
                });
            } catch (Exception e) {
                Platform.runLater(() -> setStatus("Playlist empty or error: " + e.getMessage()));
            }
        });
    }

    private void addSelectedSongToPlaylist() {
        int index = songListView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            setStatus("⚠ Please select a song to add.");
            return;
        }
        Long songId = songIds.get(index);
        setStatus("Adding to playlist...");

        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/playlists/"
                        + currentPlaylistId + "/songs/" + songId))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                Platform.runLater(() -> {
                    setStatus("✅ Song added to playlist!");
                    loadPlaylist();
                });
            } catch (Exception e) {
                Platform.runLater(() -> setStatus("❌ Error: " + e.getMessage()));
            }
        });
    }

    private void removeSelectedFromPlaylist() {
        int index = playlistView.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            setStatus("⚠ Please select a song to remove.");
            return;
        }
        Long songId = playlistSongIds.get(index);
        setStatus("Removing from playlist...");

        CompletableFuture.runAsync(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/playlists/"
                        + currentPlaylistId + "/songs/" + songId))
                    .DELETE()
                    .build();
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                Platform.runLater(() -> {
                    setStatus("✅ Song removed from playlist!");
                    loadPlaylist();
                });
            } catch (Exception e) {
                Platform.runLater(() -> setStatus("❌ Error: " + e.getMessage()));
            }
        });
    }

    // ==================== PLAYBACK CONTROLS ====================

    private void playSelected() {
        int index = playlistView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            currentIndex = index;
            nowPlayingLabel.setText("♪ Now Playing: " + playlistSongs.get(currentIndex));
            setStatus("▶ Playing: " + playlistSongs.get(currentIndex));
        } else if (!playlistSongs.isEmpty()) {
            currentIndex = 0;
            playlistView.getSelectionModel().select(currentIndex);
            nowPlayingLabel.setText("♪ Now Playing: " + playlistSongs.get(currentIndex));
        } else {
            setStatus("⚠ Playlist is empty. Add songs first!");
        }
    }

    private void playNext() {
        if (playlistSongs.isEmpty()) return;
        currentIndex = (currentIndex + 1) % playlistSongs.size();
        playlistView.getSelectionModel().select(currentIndex);
        nowPlayingLabel.setText("♪ Now Playing: " + playlistSongs.get(currentIndex));
        setStatus("⏭ Next: " + playlistSongs.get(currentIndex));
    }

    private void playPrevious() {
        if (playlistSongs.isEmpty()) return;
        currentIndex = (currentIndex - 1 + playlistSongs.size()) % playlistSongs.size();
        playlistView.getSelectionModel().select(currentIndex);
        nowPlayingLabel.setText("♪ Now Playing: " + playlistSongs.get(currentIndex));
        setStatus("⏮ Previous: " + playlistSongs.get(currentIndex));
    }

    // ==================== HELPERS ====================

    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: " + color + "; "
            + "-fx-text-fill: white; "
            + "-fx-font-weight: bold; "
            + "-fx-border-radius: 5; "
            + "-fx-background-radius: 5;");
        return btn;
    }

    private TextField createStyledTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle("-fx-background-color: #0f3460; "
            + "-fx-text-fill: white; "
            + "-fx-prompt-text-fill: gray;");
        tf.setPrefWidth(150);
        return tf;
    }

    private void setStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText("ℹ " + message);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
