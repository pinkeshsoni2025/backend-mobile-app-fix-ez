package com.coders.jwt.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.coders.jwt.exception.ResourceNotFoundException;
import com.coders.jwt.models.Ticket;
import com.coders.jwt.repository.TicketRepository;
import com.coders.jwt.service.TicketService;

import jakarta.validation.Valid;

import com.coders.jwt.payload.request.TicketRequest;
import com.coders.jwt.payload.response.TicketResponse;
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
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
	@Autowired
    private TicketRepository tikcetRepository;
    
    @Autowired
	  private TicketService ticketService;
    
    
    @PostMapping()
    public Ticket createTicket(@Valid @RequestBody Ticket ticket) {
      System.out.println(ticket);
      return tikcetRepository.save(ticket);
    }

    @GetMapping
    public List<Ticket> getAllTickets(
            ) {
    	return (List<Ticket>) ticketService.getAllTickets();
    }
    
   
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable(value = "id") Long ticketId)
        throws ResourceNotFoundException {
    	Ticket ticket =
          
    			tikcetRepository.findById(ticketId)
              .orElseThrow(() -> new ResourceNotFoundException("Ticket not found on :: " + ticketId));
      return ResponseEntity.ok().body(ticket);
    }
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateRoles(
        @PathVariable(value = "id") Long ticketId, @Valid @RequestBody Ticket ticketDetail)
        throws ResourceNotFoundException {

      Ticket ticket =
    		  tikcetRepository
              .findById(ticketId)
              .orElseThrow(() -> new ResourceNotFoundException(""
              		+ ""
              		+ ""
              		+ "ticket not found on :: " + ticketId));

      ticket.setTitle(ticketDetail.getTitle());
      ticket.setCategory(ticketDetail.getCategory());
      ticket.setDescription(ticketDetail.getDescription());
      ticket.setUpdated_by("pinkeshsoni1");
      
      final Ticket ticket1 = tikcetRepository.save(ticket);
      return ResponseEntity.ok(ticket1);
    }
    
   
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteRole(@PathVariable(value = "id") Long ticketId) throws Exception {
    	Ticket ticket =
      		  tikcetRepository
                .findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException(""
                		+ ""
                		+ ""
                		+ "ticket not found on :: " + ticketId));

    	tikcetRepository.delete(ticket);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);
      return response;
    }


}