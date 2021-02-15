package com.innova.ws.comment;

import com.innova.ws.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    CommentRepository commentRepository;
    UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }
}
