package com.coders.jwt.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.coders.jwt.exception.ResourceNotFoundException;
import com.coders.jwt.models.Rating;
import com.coders.jwt.repository.RatingRepository;
import com.coders.jwt.service.RatingService;

import jakarta.validation.Valid;

import com.coders.jwt.payload.request.RatingRequest;
import com.coders.jwt.payload.response.RatingResponse;
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
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
	@Autowired
	private RatingRepository ratingRepository;
    
    @Autowired
	  private RatingService ratingService;
    
    

    @PostMapping()
    public Rating createRating(@Valid @RequestBody Rating rating) {
      System.out.println(rating);
      return ratingRepository.save(rating);
    }

    @GetMapping
    public List<Rating> getAllRatings(
            ) {
    	return (List<Rating>) ratingService.listAll();
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable(value = "id") Long ratingId)
        throws ResourceNotFoundException {
    	Rating rating = ratingRepository.findById(ratingId).orElseThrow(() -> new ResourceNotFoundException("rating not found on :: " + ratingId));
      return ResponseEntity.ok().body(rating);
    }


    @GetMapping("/ticket/{ticketId}")
    public List<Rating> getRatingByTicket(@PathVariable(value = "ticketId") Long ticketId)
        throws ResourceNotFoundException {
    	return (List<Rating>) ratingRepository.findByTicketId(ticketId);
    }
    
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Rating> updateRating(
        @PathVariable(value = "id") Long ratingId, @Valid @RequestBody Rating ratingDetail)
        throws ResourceNotFoundException {

    	Rating rating =
    	          
    			ratingRepository.findById(ratingId)
              .orElseThrow(() -> new ResourceNotFoundException("rating not found on :: " + ratingId));

    	rating.setComment(ratingDetail.getComment());
    	rating.setRating(ratingDetail.getRating());
      
      final Rating rating1 = ratingRepository.save(rating);
      return ResponseEntity.ok(rating1);
    }
    
   
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteRating(@PathVariable(value = "id") Long ratingId) throws Exception {
    	Rating rating =
  	          
    			ratingRepository.findById(ratingId)
              .orElseThrow(() -> new ResourceNotFoundException("rating not found on :: " + ratingId));
    	
    	ratingRepository.delete(rating);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);
      return response;
    }


}