package com.innova.ws.comment;

import com.innova.ws.comment.vm.CommentUpdateVM;
import com.innova.ws.error.ForbiddenException;
import com.innova.ws.error.NotFoundException;
import com.innova.ws.note.Note;
import com.innova.ws.note.NoteService;
import com.innova.ws.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

        if(note.getUser().getId() != user.getId()) {
            throw new ForbiddenException();
        }

        Comment newComment = new Comment();
        newComment.setComment(comment.getComment());
        newComment.setTimestamp(new Date());
        newComment.setNote(note);
        commentRepository.save(newComment);
    }

    public Page<Comment> getCommentsOfNote(long noteId, Pageable page) {
        Note inDB = noteService.getNoteByNoteId(noteId);
        return commentRepository.findByNote(inDB, page);
    }

    public Page<Comment> getOldComments(long id, long noteId, Pageable page) {
        Comment comment = commentRepository.getOne(id);
        Specification<Comment> specification = timestampLessThan(comment.getTimestamp());

        Note inDB = noteService.getNoteByNoteId(noteId);
        specification = specification.and(noteIs(inDB));

        return commentRepository.findAll(specification, page);
    }

    public void delete(long id) {
        commentRepository.deleteById(id);
    }

    public Comment getCommentByCommentId(long id) {
        Optional<Comment> inDB = commentRepository.findById(id);

        return inDB.orElse(null);
    }

    public Comment updateComment(long commentId, CommentUpdateVM updatedComment) {
        Comment inDB = getCommentByCommentId(commentId);
        inDB.setComment(updatedComment.getComment());
        inDB.setTimestamp(new Date());

        return commentRepository.save(inDB);
    }

    Specification<Comment> timestampLessThan(Date timestamp) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("timestamp"), timestamp);
    }

    Specification<Comment> noteIs(Note note) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("note"), note);
    }
}
