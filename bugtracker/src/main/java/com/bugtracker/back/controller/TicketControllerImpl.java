package com.bugtracker.back.controller;

import com.bugtracker.back.Services.TicketService;
import com.bugtracker.back.dto.TicketDto;
import com.bugtracker.back.models.Ticket;
import com.bugtracker.back.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TicketControllerImpl implements TicketController{

    private final TicketService ticketService;

    @Override
    public ResponseEntity<Ticket> createTicket(TicketDto ticketDto,String projectId) {
        return new ResponseEntity<>(ticketService.createTicket(ticketDto,projectId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Ticket> updateTicket(Ticket ticket) {
        Ticket createdTicket = ticketService.updateTicket(ticket);
        return new ResponseEntity<>(createdTicket, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Ticket> findTicketById(String id) {
        Ticket foundTicket = ticketService.findTicketById(id);
        return new ResponseEntity<>(foundTicket, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Ticket> addDevToTicket( String projectId,  Long devId){
        try {
            return new ResponseEntity<>(ticketService.assignDevToTicket(projectId,devId),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Ticket> updateTicketStatus(TicketDto ticketDto) {
        try {
            return new ResponseEntity<>(ticketService.changeTicketStatus(ticketDto),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Ticket> addDevsToTicket(String ticketId, Long[] devsIds){
        try {
            return new ResponseEntity<>(ticketService.assignDevsToTicket(ticketId,devsIds),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<User>> getTicketProjectTeam(String ticketId) {
        try {
            return new ResponseEntity<>(ticketService.getTicketProjectTeamMembers(ticketId),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Ticket> removeDevFromTicket(String ticketId,Long devId){
        try {
            return new ResponseEntity<>(ticketService.removeDevFromTicket(ticketId,devId),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<List<Ticket>> findTicketsByProjectId(String projectId) {
        try {
            return new ResponseEntity<>(ticketService.findTicketsByProjectId(projectId),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Ticket>> findAllCurrentUserTickets() {
        try {
            return new ResponseEntity<>(ticketService.getAllCurrentUserTickets(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Ticket>> findTicketsByAssigneeId(String assigneeId) {
        try {
            ticketService.getTicketsByAssigneeId(assigneeId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTicketById(Long id) {
        try {
            ticketService.deleteTicketById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
