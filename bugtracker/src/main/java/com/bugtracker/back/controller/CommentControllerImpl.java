package com.bugtracker.back.controller;

import com.bugtracker.back.Services.CommentService;
import com.bugtracker.back.dto.CommentDTO;
import com.bugtracker.back.models.Comment;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor
public class CommentControllerImpl implements CommentController{

    private CommentService commentService;

    @Override
    public ResponseEntity<Comment> createComment(CommentDTO commentDTO) {
        try {
            Comment createdComment = commentService.createComment(commentDTO);
            return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<Comment> updateComment(Comment comment) {
        Comment updatedComment = commentService.updateComment(comment);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Comment> findCommentById(String id) {
        try {
            Comment foundComment = commentService.getCommentById(id);
            return new ResponseEntity<>(foundComment, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Comment>> findCommentsByTicket(String ticketId) {
        try {
            return new ResponseEntity<>(commentService.getCommentsByTicket(ticketId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<Comment>> findAllComments() {
        try {
            return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteCommentById(String commentId) {
        try {
            commentService.deleteCommentById(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
