package com.musicplayer.controller;

import com.musicplayer.model.Playlist;
import com.musicplayer.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "*")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    // GET all playlists
    // URL: GET http://localhost:8080/api/playlists
    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    // GET playlist by ID
    // URL: GET http://localhost:8080/api/playlists/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST create new playlist
    // URL: POST http://localhost:8080/api/playlists
    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist);
    }

    // PUT update playlist
    // URL: PUT http://localhost:8080/api/playlists/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlist) {
        try {
            return ResponseEntity.ok(playlistService.updatePlaylist(id, playlist));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE playlist
    // URL: DELETE http://localhost:8080/api/playlists/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        try {
            playlistService.deletePlaylist(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST add song to playlist
    // URL: POST http://localhost:8080/api/playlists/{playlistId}/songs/{songId}
    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Playlist> addSongToPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        try {
            return ResponseEntity.ok(playlistService.addSongToPlaylist(playlistId, songId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE remove song from playlist
    // URL: DELETE http://localhost:8080/api/playlists/{playlistId}/songs/{songId}
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Playlist> removeSongFromPlaylist(
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        try {
            return ResponseEntity.ok(playlistService.removeSongFromPlaylist(playlistId, songId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
