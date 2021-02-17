package com.innova.ws.note;

import com.innova.ws.error.ForbiddenException;
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

    public Note updateNote(long noteId, NoteUpdateVM updatedNote, User user) {
        Note inDB = getNoteByNoteId(noteId, user);
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
        Note note = noteRepository.getOne(id);
        Specification<Note> specification = timestampLessThan(note.getTimestamp());

        if(username != null) {
            User inDB = userService.getByUsername(username);
            specification = specification.and(userIs(inDB));
        }

        return noteRepository.findAll(specification, page);
    }

    public Note getNoteByNoteId(long id, User user) {
        Optional<Note> inDB = noteRepository.findById(id);

        if(inDB.isPresent()) {
            Note note = inDB.get();

            if(note.getUser().getId() != user.getId()) {
                throw new ForbiddenException();
            }

            return note;
        }

        return null;
    }

    public void delete(long id) {
        noteRepository.deleteById(id);
    }

    Specification<Note> timestampLessThan(Date timestamp) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("timestamp"), timestamp);
    }

    Specification<Note> userIs(User user) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
    }
}
