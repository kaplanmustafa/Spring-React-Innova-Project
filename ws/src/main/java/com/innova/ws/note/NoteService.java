package com.innova.ws.note;

import com.innova.ws.user.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NoteService {

    NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void save(Note note, User user) {
        Note newNote = new Note();
        newNote.setContent(note.getContent());
        newNote.setTitle(note.getTitle());
        newNote.setTimestamp(new Date());
        newNote.setUser(user);
        noteRepository.save(newNote);
    }
}
