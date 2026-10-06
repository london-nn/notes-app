package com.group.notes_app.controller;

import com.group.notes_app.dto.NoteDto;
import com.group.notes_app.dto.NoteRequest;
import com.group.notes_app.entity.Note;
import com.group.notes_app.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("{userId}/notes")
    public ResponseEntity<NoteDto> createNote(@PathVariable Long userId, @RequestBody NoteRequest noteRequest) {
        NoteDto savedNote = noteService.CreateNote(userId, noteRequest);
        return ResponseEntity.ok().body(savedNote);
    }

    @GetMapping("/id")
    public ResponseEntity<NoteDto> getNoteById(@RequestParam int id) {
        NoteDto note = noteService.getNoteById(id);
        return ResponseEntity.ok().body(note);
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        List<NoteDto> notes = noteService.getAllNotes();
        return ResponseEntity.ok().body(notes);
    }

    @DeleteMapping
    public ResponseEntity deleteNoteById(@RequestParam int id) {
        noteService.deleteNoteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<NoteDto> updateNote(@RequestParam long id,@RequestBody NoteRequest noteRequest) {
        NoteDto note = noteService.updateNote(id, noteRequest);
        return ResponseEntity.ok().body(note);
    }

    @GetMapping("/title")
    public ResponseEntity<NoteDto> getNoteByTitle(@RequestParam String title) {
        NoteDto note = noteService.getNoteByTitle(title);
        return ResponseEntity.ok().body(note);
    }

}
