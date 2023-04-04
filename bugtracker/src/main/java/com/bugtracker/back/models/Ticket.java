package com.bugtracker.back.models;

import com.bugtracker.back.utils.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Ticket {

    public Ticket(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime addedOn = LocalDateTime.now();

    private TicketStatus status = TicketStatus.OPEN;

    @OneToMany(mappedBy = "commentedTicket")
    private List<Comment> ticketComments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Project project;

    @ManyToOne
    private User ticketCreator;

    @ManyToMany(mappedBy = "assignedTickets",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<User> assignees;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", project=" + project +
                '}';
    }
}
