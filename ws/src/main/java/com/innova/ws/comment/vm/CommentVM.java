package com.innova.ws.comment.vm;

import com.innova.ws.comment.Comment;
import lombok.Data;

import java.util.Date;

@Data
public class CommentVM {

    private long id;

    private String comment;

    private Date timestamp;

    public CommentVM(Comment comment) {
        this.setId(comment.getId());
        this.setComment(comment.getComment());
        this.setTimestamp(comment.getTimestamp());
    }
}
