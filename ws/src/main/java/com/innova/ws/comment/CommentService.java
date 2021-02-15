package com.innova.ws.comment;

import com.innova.ws.error.NotFoundException;
import com.innova.ws.note.Note;
import com.innova.ws.note.NoteService;
import com.innova.ws.user.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {

    CommentRepository commentRepository;
    NoteService noteService;

    public CommentService(CommentRepository commentRepository, NoteService noteService) {
        this.commentRepository = commentRepository;
        this.noteService = noteService;
    }

    public void save(Comment comment, long noteId, User user) {
        Note note = noteService.getNoteByNoteId(noteId);
        if(note == null) {
            throw new NotFoundException();
        }

        Comment newComment = new Comment();
        newComment.setComment(comment.getComment());
        newComment.setTimestamp(new Date());
        newComment.setNote(note);
        commentRepository.save(newComment);
    }
}
