package com.innova.ws.note;

import com.innova.ws.configuration.CustomUserDetails;
import com.innova.ws.note.vm.NoteUpdateVM;
import com.innova.ws.note.vm.NoteVM;
import com.innova.ws.shared.CurrentUser;
import com.innova.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping("/notes")
    GenericResponse saveNote(@Valid @RequestBody Note note, @CurrentUser CustomUserDetails user) {
        noteService.save(note, user);
        return new GenericResponse("Note saved");
    }

    @GetMapping("/users/{username}/notes")
    @PreAuthorize("#username == principal.username")
    Page<NoteVM> getUserNotes(@PathVariable String username, @PageableDefault(sort = "timestamp", direction = Direction.DESC) Pageable page) {
        return noteService.getNotesOfUser(username, page).map(NoteVM::new);
    }

    @GetMapping("/users/{username}/notes/{id:[0-9]+}")
    @PreAuthorize("#username == principal.username")
    ResponseEntity<?> getNotesRelative(@PageableDefault(sort = "timestamp", direction = Direction.DESC) Pageable page, @PathVariable long id,
                                       @PathVariable String username) {

        return ResponseEntity.ok(noteService.getOldNotes(id, username, page).map(NoteVM::new));
    }

    @GetMapping("/users/notes/{id}")
    NoteVM getNoteByNoteId(@PathVariable long id, @CurrentUser CustomUserDetails user) {
        Note note = noteService.getNoteByNoteId(id, user);
        return new NoteVM(note);
    }

    @PutMapping("/notes/{username}/{noteId}")
    @PreAuthorize("#username == principal.username")
    NoteVM updateNote(@RequestBody NoteUpdateVM updatedNote, @PathVariable String username, @PathVariable long noteId, @CurrentUser CustomUserDetails user) {
        Note note = noteService.updateNote(noteId, updatedNote, user);
        return new NoteVM(note);
    }

    @DeleteMapping("/notes/{id:[0-9]+}")
    @PreAuthorize("@noteSecurity.isAllowedToDelete(#id, principal)")
    GenericResponse deleteNote(@PathVariable long id) {
        noteService.delete(id);
        return new GenericResponse("Note removed");
    }
}