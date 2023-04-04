package com.bugtracker.back.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    public Comment(String content, User commentator) {
        this.content = content;
        this.commentator = commentator;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private LocalDateTime creationDate = LocalDateTime.now();

    private String content;

    @ManyToOne
    private User commentator;

    @ManyToOne
    @JsonIgnore
    private Ticket commentedTicket;

}
