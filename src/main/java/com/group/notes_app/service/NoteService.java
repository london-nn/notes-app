package com.group.notes_app.service;

import com.group.notes_app.dto.NoteDto;
import com.group.notes_app.dto.NoteRequest;
import com.group.notes_app.entity.Note;
import com.group.notes_app.entity.User;
import com.group.notes_app.exceptions.TaskListIsEmpty;
import com.group.notes_app.exceptions.TaskNotFoundException;
import com.group.notes_app.repository.NoteRepository;
import com.group.notes_app.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    public NoteDto CreateNote(Long userId, NoteRequest noteRequest) {
        Note noteEntity = new Note();
        BeanUtils.copyProperties(noteRequest, noteEntity);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь с id: " + userId + "не найден."));
        noteEntity.setUser(user);
        Note savedNote = noteRepository.save(noteEntity);
        NoteDto noteDto = new NoteDto();
        BeanUtils.copyProperties(savedNote, noteDto);
        return noteDto;
    }

    public NoteDto getNoteById(long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new TaskNotFoundException("Note not found with id " + id);
        }
        NoteDto noteDto = new NoteDto();
        BeanUtils.copyProperties(note.get(), noteDto);
        return noteDto;
    }

    public List<NoteDto> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        if (notes.isEmpty()) {
            throw new TaskListIsEmpty("Task list is empty");
        }
        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note note : notes) {
            NoteDto noteDto = new NoteDto();
            BeanUtils.copyProperties(note, noteDto);
            noteDtos.add(noteDto);
        }
        return noteDtos;
    }

    public void deleteNoteById(long id) {
        if (!noteRepository.existsById(id)) {
            throw new TaskNotFoundException("Note not found with id " + id);
        }
        noteRepository.deleteById(id);
    }

    public NoteDto updateNote(long id , NoteRequest noteRequest) {
        Note noteEntity = new Note();
        BeanUtils.copyProperties(noteRequest, noteEntity);
        Note noteToUpdate = noteRepository.findById(id).get();
        if (!noteRepository.existsById(id)) {
            throw new TaskNotFoundException("Note not found with id " + id);
        }
        BeanUtils.copyProperties(noteRequest, noteToUpdate);
        Note savedNote = noteRepository.saveAndFlush(noteToUpdate);
        NoteDto noteDto = new NoteDto();
        BeanUtils.copyProperties(noteToUpdate, noteDto);
        return noteDto;
    }

    public NoteDto getNoteByTitle(String title) {
        Note note = noteRepository.findByTitle(title);
        if (note == null) {
            throw new TaskNotFoundException("Note not found with title " + title);
        }
        NoteDto noteDto = new NoteDto();
        BeanUtils.copyProperties(note, noteDto);
        return noteDto;
    }
}
