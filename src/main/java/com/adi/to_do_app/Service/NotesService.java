package com.adi.to_do_app.Service;

import com.adi.to_do_app.model.MyUser;
import com.adi.to_do_app.model.Note;
import com.adi.to_do_app.model.NotesDTO;
import com.adi.to_do_app.repo.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public NotesDTO addNote(Note note, MyUser user){
        note.setUser(user);
        Note savedNote = notesRepository.save(note);
        return new NotesDTO(savedNote.getId(),
                savedNote.getUser().getId(),
                savedNote.getTitle(),
                savedNote.getDescription(),
                savedNote.getTag(),
                savedNote.getDate()
        );
    }
}
