package com.adi.to_do_app.Service;

import com.adi.to_do_app.model.MyUser;
import com.adi.to_do_app.model.Note;
import com.adi.to_do_app.model.NotesDTO;
import com.adi.to_do_app.repo.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                savedNote.getCreatedAt(),
                savedNote.getUpdatedAt()
        );
    }

    public List<NotesDTO> getNotes(MyUser user){
        if(user==null){
            throw new UsernameNotFoundException("User 404");
        }
        return notesRepository.findByUser(user);
    }

    public ResponseEntity<String> deleteNoteById(String id, MyUser user) {
        Long noteId;
        try {
            noteId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid note ID format");
        }

        Optional<Note> note = notesRepository.findById(noteId);

        if (note.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }

        if (!Objects.equals(note.get().getUser().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to delete this note");
        }

        notesRepository.deleteById(noteId);
        return ResponseEntity.ok("Note Deleted Successfully");
    }

    public ResponseEntity<NotesDTO> updateNoteById(String id, Note note, MyUser user) {
        Long noteId;
        try{
            noteId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Note> existingNote = notesRepository.findById(noteId);
        if (existingNote.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(!Objects.equals(existingNote.get().getUser().getId(), user.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Note updatedNote = existingNote.get();
        updatedNote.setTitle(note.getTitle());
        updatedNote.setDescription(note.getDescription());
        updatedNote.setTag(note.getTag());
        Note savedNote = notesRepository.save(updatedNote);

        NotesDTO updatedNoteDTO = new NotesDTO(savedNote.getId(),
                savedNote.getUser().getId(),
                savedNote.getTitle(),
                savedNote.getDescription(),
                savedNote.getTag(),
                savedNote.getCreatedAt(),
                savedNote.getUpdatedAt()
        );
        return ResponseEntity.status(HttpStatus.OK).body(updatedNoteDTO);
    }
}
