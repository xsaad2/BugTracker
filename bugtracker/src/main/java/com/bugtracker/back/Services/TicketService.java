package com.bugtracker.back.Services;

import com.bugtracker.back.dto.TicketDto;
import com.bugtracker.back.models.Ticket;
import com.bugtracker.back.models.User;

import java.util.List;

public interface TicketService {

    Ticket createTicket(TicketDto ticketDto,String projectId);

    Ticket updateTicket(Ticket ticket);

    List<Ticket> findTickets();
    List<Ticket> findTicketsByProjectId(String projectId);

    Ticket findTicketById(String id);

    List<Ticket> getAllCurrentUserTickets();

    List<Ticket> getTicketsByAssigneeId(String assigneeId);

    List<User> getTicketProjectTeamMembers(String ticketId);

    void deleteTicketById(Long id);

    Ticket changeTicketStatus(TicketDto ticketDto);
    Ticket assignDevToTicket(String ticketId, Long devId);

    Ticket removeDevFromTicket(String ticketId, Long devId);

    Ticket assignDevsToTicket(String ticketId, Long[] devsIds);
}
