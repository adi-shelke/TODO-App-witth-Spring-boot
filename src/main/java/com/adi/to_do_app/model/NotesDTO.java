package com.adi.to_do_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class NotesDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String tag;
    private LocalDateTime date;


}
