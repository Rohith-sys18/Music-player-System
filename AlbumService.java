package com.musicplayer.service;

import com.musicplayer.model.Album;
import com.musicplayer.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    // Get all albums
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    // Get album by ID
    public Optional<Album> getAlbumById(Long id) {
        return albumRepository.findById(id);
    }

    // Add new album
    public Album addAlbum(Album album) {
        return albumRepository.save(album);
    }

    // Update existing album
    public Album updateAlbum(Long id, Album updatedAlbum) {
        return albumRepository.findById(id).map(album -> {
            album.setName(updatedAlbum.getName());
            album.setArtist(updatedAlbum.getArtist());
            album.setReleaseYear(updatedAlbum.getReleaseYear());
            return albumRepository.save(album);
        }).orElseThrow(() -> new RuntimeException("Album not found with id: " + id));
    }

    // Delete album
    public void deleteAlbum(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new RuntimeException("Album not found with id: " + id);
        }
        albumRepository.deleteById(id);
    }
}
