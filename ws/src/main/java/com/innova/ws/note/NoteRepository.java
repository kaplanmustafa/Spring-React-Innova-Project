package com.innova.ws.note;

import com.innova.ws.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoteRepository  extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
    Page<Note> findByUser(User user, Pageable page);
}
