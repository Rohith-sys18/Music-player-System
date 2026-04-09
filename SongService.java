package com.musicplayer.service;

import com.musicplayer.model.Song;
import com.musicplayer.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    // Get all songs
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    // Get song by ID
    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    // Add new song
    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    // Update existing song
    public Song updateSong(Long id, Song updatedSong) {
        return songRepository.findById(id).map(song -> {
            song.setTitle(updatedSong.getTitle());
            song.setArtist(updatedSong.getArtist());
            song.setGenre(updatedSong.getGenre());
            song.setDuration(updatedSong.getDuration());
            song.setAlbum(updatedSong.getAlbum());
            return songRepository.save(song);
        }).orElseThrow(() -> new RuntimeException("Song not found with id: " + id));
    }

    // Delete song
    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            throw new RuntimeException("Song not found with id: " + id);
        }
        songRepository.deleteById(id);
    }

    // Search songs by artist
    public List<Song> searchByArtist(String artist) {
        return songRepository.findByArtistContainingIgnoreCase(artist);
    }

    // Search songs by title
    public List<Song> searchByTitle(String title) {
        return songRepository.findByTitleContainingIgnoreCase(title);
    }
}
