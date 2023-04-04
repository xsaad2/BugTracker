package com.bugtracker.back.Services;

import com.bugtracker.back.dto.TicketDto;
import com.bugtracker.back.exceptions.NotFoundException;
import com.bugtracker.back.models.Project;
import com.bugtracker.back.models.Ticket;
import com.bugtracker.back.models.User;
import com.bugtracker.back.repositories.TicketRepo;
import com.bugtracker.back.security.SecurityService;
import com.bugtracker.back.utils.AppUserRoles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepo ticketRepo;
    private final SecurityService securityService;
    private final UserService userService;

    private final ProjectService projectService;

    @Override
    public Ticket createTicket(TicketDto ticketDto, String projectId) {
        System.out.println("in ticket creation");
        Ticket newTicket = new Ticket(ticketDto.getTitle(),ticketDto.getDescription());
        newTicket.setTicketCreator(securityService.getCurrentUser());
        Ticket createdticket = ticketRepo.save(newTicket);
        createdticket.setProject(projectService.getProjectById(projectId));
       for(Long devId : ticketDto.getAssigneesIds()){
            assignDevToTicket(createdticket.getId().toString(), devId);
       }
       return ticketRepo.save(newTicket);
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    @Override
    public List<Ticket> findTickets() {
        return ticketRepo.findAll();
    }

    @Override
    public List<Ticket> findTicketsByProjectId(String projectId){
        Project project = projectService.getProjectById(projectId);
        return ticketRepo.findAllByProject(project);
    }

    @Override
    public Ticket findTicketById(String id) {
        return ticketRepo.findTicketById(Long.parseLong(id))
                .orElseThrow(()-> new NotFoundException("Ticket by id " + id + " was not found"));
    }

    @Override
    public List<Ticket> getAllCurrentUserTickets() {
        User currentUser = securityService.getCurrentUser();
        boolean isAdminOrManager = securityService.currentUserRoleIs(AppUserRoles.ADMIN.toString())
                || securityService.currentUserRoleIs(AppUserRoles.Manager.toString());
        if (isAdminOrManager) {
            return findTickets();
        } else {
            return ticketRepo.findAllByAssigneesOrTicketCreator(currentUser,currentUser);
        }
    }

    @Override
    public List<Ticket> getTicketsByAssigneeId(String assigneeId) {
        User wantedUser = userService.findUserById(Long.parseLong(assigneeId));
        return ticketRepo.findAllByAssigneesOrTicketCreator(wantedUser,wantedUser);
    }

    @Override
    public List<User> getTicketProjectTeamMembers(String ticketId) {
        return findTicketById(ticketId).getProject().getMembers();
    }

    public Ticket changeTicketStatus(TicketDto ticketDto) {
        Ticket foundTicket = findTicketById(ticketDto.getId());
        foundTicket.setStatus(ticketDto.getStatus());
        return updateTicket(foundTicket);
    }

    @Override
    public void deleteTicketById(Long id) {
        ticketRepo.deleteTicketById(id);
    }

    @Override
    public Ticket assignDevToTicket(String ticketId, Long devId) {
        Ticket ticketToUpdate = findTicketById(ticketId);
        User newAssignee = userService.findUserById(devId);
        newAssignee.getAssignedTickets().add(ticketToUpdate);
        userService.updateUser(newAssignee);
        return ticketToUpdate;
    }

    @Override
    public Ticket removeDevFromTicket(String ticketId, Long devId) {
        Ticket ticketToUpdate = findTicketById(ticketId);
        User newAssignee = userService.findUserById(devId);
        newAssignee.getAssignedTickets().remove(ticketToUpdate);
        userService.updateUser(newAssignee);
        return ticketToUpdate;
    }

    @Override
    public Ticket assignDevsToTicket(String ticketId, Long[] devsIds) {
        Ticket ticketToUpdate = findTicketById(ticketId);
        for(Long devId : devsIds){
            User newAssignee = userService.findUserById(devId);
            newAssignee.getAssignedTickets().add(ticketToUpdate);
            userService.updateUser(newAssignee);
        }
        return updateTicket(ticketToUpdate);
    }
}
