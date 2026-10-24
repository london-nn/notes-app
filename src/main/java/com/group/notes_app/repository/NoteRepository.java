package com.group.notes_app.repository;

import com.group.notes_app.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    public Optional<Note> findByTitle(String title);
}
