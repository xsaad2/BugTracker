package com.bugtracker.back.Services;

import com.bugtracker.back.dto.CommentDTO;
import com.bugtracker.back.exceptions.NotFoundException;
import com.bugtracker.back.models.Comment;
import com.bugtracker.back.models.Ticket;
import com.bugtracker.back.models.User;
import com.bugtracker.back.repositories.CommentRepo;
import com.bugtracker.back.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private CommentRepo commentRepo;

    private TicketService ticketService;

    private SecurityService securityService;

    private UserService userService;

    @Override
    public Comment createComment(CommentDTO commentDto) {
        Comment newComment = new Comment(commentDto.getContent(),securityService.getCurrentUser());
        Ticket ticket = ticketService.findTicketById(commentDto.getCommentedTicketId());
        if (ticket == null) {
            throw new NotFoundException("Ticket not found with ID: " + commentDto.getCommentedTicketId());
        }
        newComment.setCommentedTicket(ticket);
        return commentRepo.save(newComment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        if (!commentRepo.existsById(comment.getId())) {
            throw new NotFoundException("Comment not found with ID: " + comment.getId());
        }
        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepo.findAll();
    }

    @Override
    public List<Comment> getAllCurrentUserComments() {
        return commentRepo.findAllByCommentator(securityService.getCurrentUser());
    }

    @Override
    public List<Comment> getCommentsByCommentator(String commentatorId) {
        User commentator = userService.findUserById(Long.parseLong(commentatorId));
        if (commentator == null) {
            throw new NotFoundException("User not found with ID: " + commentatorId);
        }
        return commentRepo.findAllByCommentator(commentator);
    }

    @Override
    public List<Comment> getCommentsByTicket(String ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found with ID: " + ticketId);
        }
        return commentRepo.findAllByCommentedTicketOrderByCreationDateDesc(ticket);
    }

    @Override
    public Comment getCommentById(String id) {
        UUID commentUUID = null;
        try {
            commentUUID = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Invalid UUID for comment ID: " + id);
        }
        return commentRepo.findById(commentUUID)
                .orElseThrow(() -> new NotFoundException("Comment not found with ID: "+id));
    }

    @Override
    public void deleteCommentById(String id) {
        UUID commentUUID = null;
        try {
            commentUUID = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Invalid UUID for comment ID: " + id);
        }
        if (!commentRepo.existsById(commentUUID)) {
            throw new NotFoundException("Comment not found with ID: " + id);
        }
        commentRepo.deleteById(commentUUID);
    }
}
