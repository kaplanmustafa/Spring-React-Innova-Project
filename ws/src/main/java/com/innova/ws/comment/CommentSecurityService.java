package com.innova.ws.comment;

import com.innova.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "commentSecurity")
public class CommentSecurityService {

    @Autowired
    CommentRepository commentRepository;

    public boolean isAllowedToDelete(long id, User loggedInUser) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if(!optionalComment.isPresent()) {
            return false;
        }

        Comment comment = optionalComment.get();

        return comment.getNote().getUser().getId() == loggedInUser.getId();
    }

}
