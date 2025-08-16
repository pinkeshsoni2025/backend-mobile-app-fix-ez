package com.coders.jwt.repository;



import com.coders.jwt.models.Rating;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Comment repository.
 *
 * @author 
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
	
	@Query("SELECT rating FROM Rating rating WHERE rating.ticket_id = ?1")
	List<Rating> findByTicketId(Long ticketId);
}