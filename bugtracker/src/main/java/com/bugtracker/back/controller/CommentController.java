package com.bugtracker.back.controller;

import com.bugtracker.back.dto.CommentDTO;
import com.bugtracker.back.models.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 The CommentController interface defines the REST API endpoints for managing comments.
 */
public interface CommentController {

    /**
     *
     Creates a new comment.
     @param commentDTO the comment DTO to create the comment from
     @return a ResponseEntity with the created comment and the HTTP status code
     */
    @PostMapping("/comment")
    ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO);

    /**
     *
     Updates an existing comment.
     @param comment the comment to update
     @return a ResponseEntity with the updated comment and the HTTP status code
     */
    @PutMapping("/comment")
    ResponseEntity<Comment> updateComment(@RequestBody  Comment comment);


    /**
     *
     Finds a comment by its ID.
     @param commentId the ID of the comment to find
     @return a ResponseEntity with the found comment and the HTTP status code
     */
    @GetMapping("/comment/{commentId}")
    ResponseEntity<Comment> findCommentById(@PathVariable String commentId);

    /**
     *
     Finds all comments for a given ticket.
     @param ticketId the ID of the ticket to find comments for
     @return a ResponseEntity with the list of comments for the given ticket and the HTTP status code
     */
    @GetMapping ("/comments/by/ticket/{ticketId}")
    ResponseEntity<List<Comment>> findCommentsByTicket(@PathVariable String ticketId);

    /**
     *
     Finds all comments.
     @return a ResponseEntity with the list of all comments and the HTTP status code
     */
    @GetMapping("/comments")
    ResponseEntity<List<Comment>> findAllComments();

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to delete
     * @return  a ResponseEntity with the HTTP status code
     */
    @DeleteMapping("/comment/{commentId}")
    ResponseEntity<Void> deleteCommentById(@PathVariable String commentId);
}
