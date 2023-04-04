package com.bugtracker.back.Services;

import com.bugtracker.back.dto.CommentDTO;
import com.bugtracker.back.models.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(CommentDTO commentDto);

    Comment updateComment(Comment comment);

    List<Comment> getAllComments();

    List<Comment> getAllCurrentUserComments();

    List<Comment> getCommentsByCommentator(String commentatorId);

    List<Comment> getCommentsByTicket(String ticketId);

    Comment getCommentById(String id);

    void deleteCommentById(String id);
}
