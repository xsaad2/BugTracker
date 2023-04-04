package com.bugtracker.back.repositories;

import com.bugtracker.back.models.Comment;
import com.bugtracker.back.models.Ticket;
import com.bugtracker.back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByCommentator(User commentator);

    List<Comment> findAllByCommentedTicketOrderByCreationDateDesc(Ticket commentedTicket);

}
