package com.innova.ws.note;

import com.innova.ws.comment.Comment;
import com.innova.ws.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "{ws.constraints.note.NotNull.message}")
    @Size(min = 1, max= 1000)
    private String content;

    @Size(max= 255)
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "note", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}

