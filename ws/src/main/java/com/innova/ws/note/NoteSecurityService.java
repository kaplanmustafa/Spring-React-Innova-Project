package com.innova.ws.note;

import com.innova.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "noteSecurity")
public class NoteSecurityService {

    @Autowired
    NoteRepository noteRepository;

    public boolean isAllowedToDelete(long id, User loggedInUser) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if(!optionalNote.isPresent()) {
            return false;
        }

        Note note = optionalNote.get();

        return note.getUser().getId() == loggedInUser.getId();
    }

}
