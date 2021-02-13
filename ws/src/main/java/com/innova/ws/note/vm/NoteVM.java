package com.innova.ws.note.vm;

import com.innova.ws.note.Note;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
public class NoteVM {

    private long id;

    private String content;

    private String title;

    private String date;

    private String time;

    public NoteVM(Note note) {
        this.setId(note.getId());
        this.setContent(note.getContent());
        this.setTitle(note.getTitle());

        SimpleDateFormat sdfDate = new SimpleDateFormat("MMMM dd yyyy ", new Locale("en"));
        String date = sdfDate.format(new Date(note.getTimestamp().getTime()));
        this.setDate(date);
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a", new Locale("en"));
        String time = sdfTime.format(new Date(note.getTimestamp().getTime()));
        this.setTime(time);
    }
}
