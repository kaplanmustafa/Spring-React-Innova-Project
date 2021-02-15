package com.innova.ws.note;

import com.innova.ws.comment.Comment;
import com.innova.ws.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 1000)
    private String content;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "note", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}

