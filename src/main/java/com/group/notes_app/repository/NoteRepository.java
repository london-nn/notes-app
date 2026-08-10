package com.group.notes_app.repository;

import com.group.notes_app.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    public Note findByTitle(String title);
}
