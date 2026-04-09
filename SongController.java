package com.musicplayer.controller;

import com.musicplayer.model.Song;
import com.musicplayer.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*")
public class SongController {

    @Autowired
    private SongService songService;

    // GET all songs
    // URL: GET http://localhost:8080/api/songs
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    // GET song by ID
    // URL: GET http://localhost:8080/api/songs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        return songService.getSongById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST add new song
    // URL: POST http://localhost:8080/api/songs
    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return songService.addSong(song);
    }

    // PUT update song
    // URL: PUT http://localhost:8080/api/songs/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song song) {
        try {
            return ResponseEntity.ok(songService.updateSong(id, song));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE song
    // URL: DELETE http://localhost:8080/api/songs/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        try {
            songService.deleteSong(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET search songs by artist
    // URL: GET http://localhost:8080/api/songs/search/artist?name=Arijit
    @GetMapping("/search/artist")
    public List<Song> searchByArtist(@RequestParam String name) {
        return songService.searchByArtist(name);
    }

    // GET search songs by title
    // URL: GET http://localhost:8080/api/songs/search/title?name=Shape
    @GetMapping("/search/title")
    public List<Song> searchByTitle(@RequestParam String name) {
        return songService.searchByTitle(name);
    }
}
