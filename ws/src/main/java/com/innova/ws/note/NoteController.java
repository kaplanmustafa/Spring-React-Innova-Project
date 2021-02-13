package com.innova.ws.note;

import com.innova.ws.note.vm.NoteVM;
import com.innova.ws.shared.CurrentUser;
import com.innova.ws.shared.GenericResponse;
import com.innova.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/{username}/notes")
    Page<NoteVM> getUserNotes(@PathVariable String username, @PageableDefault(sort = "id", direction = Direction.DESC) Pageable page) {
        return noteService.getNotesOfUser(username, page).map(NoteVM::new);
    }

    @GetMapping({ "/users/{username}/notes/{id:[0-9]+}"})
    ResponseEntity<?> getNotesRelative(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page, @PathVariable long id,
                                        @PathVariable String username) {

        return ResponseEntity.ok(noteService.getOldNotes(id, username, page).map(NoteVM::new));
    }
}