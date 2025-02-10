package com.adi.to_do_app.controller;//package com.adi.to_do_app.controller;
import com.adi.to_do_app.Service.NotesService;
import com.adi.to_do_app.Service.UserService;
import com.adi.to_do_app.model.MyUser;
import com.adi.to_do_app.model.Note;
import com.adi.to_do_app.model.NotesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @Autowired
    private UserService userService;

    @PostMapping("addnote")
    public NotesDTO addNote(@RequestBody Note note, @AuthenticationPrincipal UserDetails userDetails){
        MyUser user = userService.findByUsername(userDetails.getUsername());
        return notesService.addNote(note, user);
    }

    @GetMapping("fetchallnotes")
    public List<NotesDTO> getNotes (@AuthenticationPrincipal UserDetails userDetails){
        MyUser user = userService.findByUsername(userDetails.getUsername());
        return notesService.getNotes(user);
    }

    @DeleteMapping("/deletenote/{id}")
    public void deleteNote(@PathVariable String id){
        notesService.deleteNoteById(id);
    }
}