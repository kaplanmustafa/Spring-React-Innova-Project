package com.innova.ws.note;

import com.innova.ws.note.vm.NoteUpdateVM;
import com.innova.ws.user.User;
import com.innova.ws.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    public Note updateNote(long noteId, NoteUpdateVM updatedNote) {
        Note inDB = getNoteByNoteId(noteId);
        inDB.setContent(updatedNote.getContent());
        inDB.setTitle(updatedNote.getTitle());
        inDB.setTimestamp(new Date());

        return noteRepository.save(inDB);
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

    public Note getNoteByNoteId(long id) {
        Optional<Note> inDB = noteRepository.findById(id);

        return inDB.orElse(null);
    }

    Specification<Note> idLessThan(long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), id);
    }

    Specification<Note> userIs(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }
}
