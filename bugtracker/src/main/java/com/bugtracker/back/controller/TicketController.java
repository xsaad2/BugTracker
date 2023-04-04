package com.bugtracker.back.controller;

import com.bugtracker.back.dto.TicketDto;
import com.bugtracker.back.models.Ticket;
import com.bugtracker.back.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  The TicketController interface defines the REST API endpoints for managing tickets.
 */
public interface TicketController {

    /**
     *Creates a new ticket for the specified project and returns it as a ResponseEntity object.
     *
     @param ticketDto the TicketDto object containing the ticket data to be created.
     @param projectId the ID of the project for which the ticket is being created.
     @return a ResponseEntity object containing the newly created Ticket object.
     */
    @PostMapping("/ticket/{projectId}")
    ResponseEntity<Ticket> createTicket(@RequestBody TicketDto ticketDto,@PathVariable String projectId);

    /**
     *Updates an existing ticket and returns it as a ResponseEntity object.
     *
     @param ticket the Ticket object containing the updated ticket data.
     @return a ResponseEntity object containing the updated Ticket object.
     */
    @PutMapping("/ticket")
    ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket);

    /**
     *Finds and returns a ticket by its ID as a ResponseEntity object.
     *
     @param id the ID of the ticket to be retrieved.
     @return a ResponseEntity object containing the Ticket object with the specified ID.
     */
    @GetMapping ("/ticket/{id}")
    ResponseEntity<Ticket> findTicketById(@PathVariable String id);

    /**
     *Finds and returns a list of tickets for the specified project as a ResponseEntity object.
     *
     * @param projectId the ID of the project for which tickets are to be retrieved.
     * @return a ResponseEntity object containing a list of Ticket objects for the specified project.
     */
    @GetMapping ("/tickets/by/project/{projectId}")
    ResponseEntity<List<Ticket>> findTicketsByProjectId(@PathVariable String projectId);

    /**
     * Finds and returns a list of tickets for the current user as a ResponseEntity object.
     *
     * @return  a ResponseEntity object containing a list of Ticket objects for the current user.
     */
    @GetMapping("/tickets")
    ResponseEntity<List<Ticket>> findAllCurrentUserTickets();

    /**
     * Finds and returns a list of tickets assigned to the specified user as a ResponseEntity object.
     *
     * @param assigneeId the ID of the user to whom tickets are assigned.
     * @return a ResponseEntity object containing a list of Ticket objects assigned to the specified user.
     */
    @GetMapping("/tickets/by/assignee/{assigneeId}")
    ResponseEntity<List<Ticket>> findTicketsByAssigneeId(@PathVariable String assigneeId);

    /**
     * Adds a developer to the specified ticket and returns the updated ticket as a ResponseEntity object.
     *
     * @param ticketId the ID of the ticket to which the developer is to be added.
     * @param devId devId the ID of the developer to be added to the ticket.
     * @return a ResponseEntity object containing the updated Ticket object with the developer added.
     */
    @PutMapping("/ticket/{ticketId}/{devId}")
    ResponseEntity<Ticket> addDevToTicket(@PathVariable String ticketId, @PathVariable Long devId);

    /**
     * Updates the status of the specified ticket and returns the updated ticket as a ResponseEntity object.
     *
     * @param ticketDto the TicketDto object containing the updated ticket status.
     * @return a ResponseEntity object containing the updated Ticket object with the new status.
     */
    @PutMapping("/ticket/status/{ticketId}")
    ResponseEntity<Ticket> updateTicketStatus(@RequestBody TicketDto ticketDto);

    /**
     * Adds multiple developers to the specified ticket and returns the updated ticket as a ResponseEntity object.
     *
     * @param ticketId the ID of the ticket to which the developers are to be added.
     * @param devIds an array of developer IDs to be added to the ticket.
     * @return a ResponseEntity object containing the updated Ticket object with the developers added.
     */
    @PutMapping("/ticket/{ticketId}")
    ResponseEntity<Ticket> addDevsToTicket(@PathVariable String ticketId, @RequestBody Long[] devIds);

    /**
     * Retrieves and returns the list of project team members for the specified ticket as a ResponseEntity object.
     *
     * @param ticketId the ID of the ticket for which the project team members are to be retrieved.
     * @return a ResponseEntity object containing a list of User objects representing the project team members.
     */
    @GetMapping("/ticket/project/members/{ticketId}")
    ResponseEntity<List<User>> getTicketProjectTeam(@PathVariable String ticketId);

    /**
     * Removes a developer from the specified ticket and returns the updated ticket as a ResponseEntity object.
     *
     * @param ticketId the ID of the ticket from which the developer is to be removed.
     * @param devId the ID of the developer to be removed from the ticket.
     * @return a ResponseEntity object containing the updated Ticket object with the developer removed.
     */
    @DeleteMapping("/ticket/{ticketId}/{devId}")
    ResponseEntity<Ticket> removeDevFromTicket(@PathVariable String ticketId, @PathVariable Long devId);

    /**
     * Deletes the specified ticket and returns a ResponseEntity object with a void response.
     *
     * @param id the ID of the ticket to be deleted.
     * @return a ResponseEntity object with a void response.
     */
    @DeleteMapping("/ticket/{id}")
    ResponseEntity<Void> deleteTicketById(@PathVariable Long id);
}
