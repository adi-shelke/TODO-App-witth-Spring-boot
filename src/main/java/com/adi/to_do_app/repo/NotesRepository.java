package com.adi.to_do_app.repo;


import com.adi.to_do_app.model.MyUser;
import com.adi.to_do_app.model.Note;
import com.adi.to_do_app.model.NotesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {

    @Query("SELECT new com.adi.to_do_app.model.NotesDTO(n.id, n.user.id, n.title, n.description, n.tag, n.date) FROM Note n WHERE n.user = :user")
    List<NotesDTO> findByUser(MyUser user);
}
