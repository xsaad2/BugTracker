package com.bugtracker.back.models;

import com.bugtracker.back.utils.AppUserRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private AppUserRoles role;
    private boolean locked = false;
    private boolean enabled = true;


    @ManyToMany
    @JoinTable(
            name = "user_tickets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    @JsonIgnore
    private List<Ticket> assignedTickets;

    @ManyToMany
    @JoinTable(
            name = "project_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnore
    private List<Project> projects;

    @OneToMany(mappedBy = "commentator")
    @JsonIgnore
    private List<Comment> comments;


    @OneToMany(mappedBy = "ticketCreator")
    @JsonIgnore
    private List<Ticket> createdTickets;

    @OneToMany(mappedBy = "projectCreator")
    @JsonIgnore
    private List<Project> createdProjects;

    public User(String firstName, String lastName, String email, String password, AppUserRoles role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", projects ='" + projects.toString() + '\'' +
                '}';
    }
}
