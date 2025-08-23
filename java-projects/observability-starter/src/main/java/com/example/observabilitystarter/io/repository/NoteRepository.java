package com.example.observabilitystarter.io.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.observabilitystarter.io.entity.Note;

public interface NoteRepository extends JpaRepository<Note, String> {
}
