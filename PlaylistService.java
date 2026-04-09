package com.musicplayer.service;

import com.musicplayer.model.Playlist;
import com.musicplayer.model.Song;
import com.musicplayer.repository.PlaylistRepository;
import com.musicplayer.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    // Get all playlists
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    // Get playlist by ID
    public Optional<Playlist> getPlaylistById(Long id) {
        return playlistRepository.findById(id);
    }

    // Create new playlist
    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    // Update playlist name
    public Playlist updatePlaylist(Long id, Playlist updatedPlaylist) {
        return playlistRepository.findById(id).map(playlist -> {
            playlist.setName(updatedPlaylist.getName());
            return playlistRepository.save(playlist);
        }).orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));
    }

    // Delete playlist
    public void deletePlaylist(Long id) {
        if (!playlistRepository.existsById(id)) {
            throw new RuntimeException("Playlist not found with id: " + id);
        }
        playlistRepository.deleteById(id);
    }

    // Add song to playlist
    public Playlist addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
            .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + playlistId));
        Song song = songRepository.findById(songId)
            .orElseThrow(() -> new RuntimeException("Song not found with id: " + songId));
        playlist.addSong(song);
        return playlistRepository.save(playlist);
    }

    // Remove song from playlist
    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId)
            .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + playlistId));
        Song song = songRepository.findById(songId)
            .orElseThrow(() -> new RuntimeException("Song not found with id: " + songId));
        playlist.removeSong(song);
        return playlistRepository.save(playlist);
    }
}
