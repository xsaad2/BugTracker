package com.bugtracker.back.repositories;

import com.bugtracker.back.models.Project;
import com.bugtracker.back.models.Ticket;
import com.bugtracker.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface TicketRepo extends JpaRepository<Ticket,Long> {
    Optional<Ticket> findTicketById(Long id);
    List<Ticket> findAllByTicketCreator(User user);
    List<Ticket> findAllByProject(Project project);
    List<Ticket> findAllByAssigneesOrTicketCreator(User Assignee, User ticketCreator);
    void deleteTicketById(Long id);
}
