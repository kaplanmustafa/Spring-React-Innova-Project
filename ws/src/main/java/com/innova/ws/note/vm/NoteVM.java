package com.innova.ws.note.vm;

import com.innova.ws.note.Note;
import lombok.Data;

import java.util.Date;

@Data
public class NoteVM {

    private long id;

    private String content;

    private String title;

    private Date timestamp;

    public NoteVM(Note note) {
        this.setId(note.getId());
        this.setContent(note.getContent());
        this.setTitle(note.getTitle());
        this.setTimestamp(note.getTimestamp());
    }
}
