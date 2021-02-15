package com.innova.ws.comment;

import com.innova.ws.shared.CurrentUser;
import com.innova.ws.shared.GenericResponse;
import com.innova.ws.user.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class CommentController {

    @PostMapping("/notes")
    GenericResponse saveComment(@Valid @RequestBody Comment comment, @CurrentUser User user) {
        // to do
        return new GenericResponse("Comment saved");
    }
}