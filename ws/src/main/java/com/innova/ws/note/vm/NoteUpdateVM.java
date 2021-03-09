package com.innova.ws.note.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NoteUpdateVM {

    @NotNull(message = "{ws.constraints.note.NotNull.message}")
    @Size(min = 1, max = 1000)
    private String content;

    @Size(max = 255)
    private String title;
}
