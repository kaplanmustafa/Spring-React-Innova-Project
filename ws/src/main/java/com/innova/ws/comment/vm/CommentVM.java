package com.innova.ws.comment.vm;

import com.innova.ws.comment.Comment;
import lombok.Data;

@Data
public class CommentVM {

    private long id;

    private String comment;

    private long timestamp;

    public CommentVM(Comment comment) {
        this.setId(comment.getId());
        this.setComment(comment.getComment());
        this.setTimestamp(comment.getTimestamp().getTime());
    }
}
