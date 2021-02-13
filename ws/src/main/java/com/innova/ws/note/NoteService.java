package com.innova.ws.note;

import com.innova.ws.user.User;
import com.innova.ws.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.DoubleStream;

@Service
public class NoteService {

    NoteRepository noteRepository;
    UserService userService;

    public NoteService(NoteRepository noteRepository, UserService userService) {
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public void save(Note note, User user) {
        Note newNote = new Note();
        newNote.setContent(note.getContent());
        newNote.setTitle(note.getTitle());
        newNote.setTimestamp(new Date());
        newNote.setUser(user);
        noteRepository.save(newNote);
    }

    public Page<Note> getNotesOfUser(String username, Pageable page) {
        User inDB = userService.getByUsername(username);
        return noteRepository.findByUser(inDB, page);
    }

    public Page<Note> getOldNotes(long id, String username, Pageable page) {
        Specification<Note> specification = idLessThan(id);

        if(username != null) {
            User inDB = userService.getByUsername(username);
            specification = specification.and(userIs(inDB));
        }

        return noteRepository.findAll(specification, page);
    }

    Specification<Note> idLessThan(long id) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThan(root.get("id"), id);
        };
    }

    Specification<Note> userIs(User user) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("user"), user);
        };
    }
}
