package com.coders.jwt.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.coders.jwt.exception.ResourceNotFoundException;
import com.coders.jwt.models.Comment;
import com.coders.jwt.repository.CommentRepository;
import com.coders.jwt.service.CommentService;

import jakarta.validation.Valid;

import com.coders.jwt.payload.request.CommentRequest;
import com.coders.jwt.payload.response.CommentResponse;
//import com.example.supportticket.service.TicketService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type User controller.
 *
 * @author
 */
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
	@Autowired
	private CommentRepository commentRepository;
    
    @Autowired
	  private CommentService commentService;
    
    

    @PostMapping()
    public Comment createTicket(@Valid @RequestBody Comment comment) {
      System.out.println(comment);
      return commentRepository.save(comment);
    }

    @GetMapping
    public List<Comment> getAllComments(
            ) {
    	return (List<Comment>) commentService.listAll();
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable(value = "id") Long commentId)
        throws ResourceNotFoundException {
    	Comment comment =
          
    			commentRepository.findById(commentId)
              .orElseThrow(() -> new ResourceNotFoundException("comment not found on :: " + commentId));
      return ResponseEntity.ok().body(comment);
    }


    @GetMapping("/ticket/{ticketId}")
    public List<Comment> getCommentByTicket(@PathVariable(value = "ticketId") Long ticketId)
        throws ResourceNotFoundException {
    	return (List<Comment>) commentRepository.findByTicketId(ticketId);
    }
    
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(
        @PathVariable(value = "id") Long commentId, @Valid @RequestBody Comment commentDetail)
        throws ResourceNotFoundException {

    	Comment comment =
    	          
    			commentRepository.findById(commentId)
              .orElseThrow(() -> new ResourceNotFoundException("comment not found on :: " + commentId));

      comment.setMessage(commentDetail.getMessage());
      comment.setTicket_id(commentDetail.getTicket_id());
      comment.setCreated_by("pinkeshsoni1");
      
      final Comment comment1 = commentRepository.save(comment);
      return ResponseEntity.ok(comment1);
    }
    
   
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteRole(@PathVariable(value = "id") Long ticketId) throws Exception {
    	Comment comment =
    			commentRepository
                .findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(""
                		+ ""
                		+ ""
                		+ "comment not found on :: " + ticketId));

    	commentRepository.delete(comment);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);
      return response;
    }


}