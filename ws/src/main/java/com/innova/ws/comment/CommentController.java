package com.innova.ws.comment;

import com.innova.ws.shared.CurrentUser;
import com.innova.ws.shared.GenericResponse;
import com.innova.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comments/{noteId}")
    GenericResponse saveComment(@Valid @RequestBody Comment comment, @PathVariable long noteId, @CurrentUser User user) {
        commentService.save(comment, noteId, user);
        return new GenericResponse("Comment saved");
    }
}