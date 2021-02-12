package com.innova.ws.note;

import com.innova.ws.shared.CurrentUser;
import com.innova.ws.shared.GenericResponse;
import com.innova.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping("/notes")
    GenericResponse saveNote(@Valid @RequestBody Note note, @CurrentUser User user) {
        noteService.save(note, user);
        return new GenericResponse("Note saved");
    }
}