package com.innova.ws.comment;

import com.innova.ws.comment.vm.CommentUpdateVM;
import com.innova.ws.comment.vm.CommentVM;
import com.innova.ws.configuration.CustomUserDetails;
import com.innova.ws.shared.CurrentUser;
import com.innova.ws.shared.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comments/{noteId}")
    GenericResponse saveComment(@Valid @RequestBody Comment comment, @PathVariable long noteId, @CurrentUser CustomUserDetails user) {
        commentService.save(comment, noteId, user);
        return new GenericResponse("Comment saved");
    }

    @GetMapping("/users/comments/{noteId}")
    Page<CommentVM> getCommentsOfNote(@PathVariable long noteId, @PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable page,
                                      @CurrentUser CustomUserDetails user) {
        return commentService.getCommentsOfNote(noteId, page, user).map(CommentVM::new);
    }

    @GetMapping({"/users/{noteId}/comments/{id:[0-9]+}"})
    ResponseEntity<?> getCommentsRelative(@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable page, @PathVariable long id,
                                          @PathVariable long noteId, @CurrentUser CustomUserDetails user) {

        return ResponseEntity.ok(commentService.getOldComments(id, noteId, page, user).map(CommentVM::new));
    }

    @DeleteMapping("/comments/{id:[0-9]+}")
    @PreAuthorize("@commentSecurity.isAllowedToDelete(#id, principal)")
    GenericResponse deleteComment(@PathVariable long id) {
        commentService.delete(id);
        return new GenericResponse("Comment removed");
    }

    @PutMapping("/comments/{username}/{commentId}")
    @PreAuthorize("#username == principal.username")
    CommentVM updateComment(@RequestBody CommentUpdateVM updatedComment, @PathVariable String username, @PathVariable long commentId) {
        Comment comment = commentService.updateComment(commentId, updatedComment);
        return new CommentVM(comment);
    }
}