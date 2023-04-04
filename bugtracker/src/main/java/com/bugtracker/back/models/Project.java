package com.bugtracker.back.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String description;

    @ManyToOne
    private User projectCreator;

    @ManyToMany(mappedBy = "projects",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<User> members;

    @OneToMany(mappedBy = "project")
    private List<Ticket> tickets;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((projectCreator == null) ? 0 : projectCreator.hashCode());
        return result;
    }
    public String toString() {
        return "Project{id=" + id + ", title='" + title + "' devs = [" + members.stream().map(user -> String.valueOf(user.getId()))
                .collect(Collectors.joining(", "))+"]" + "'}" ;
    }


}
