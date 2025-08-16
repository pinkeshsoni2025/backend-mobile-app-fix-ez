package com.coders.jwt.repository;



import com.coders.jwt.models.Comment;

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
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query("SELECT coment FROM Comment coment WHERE coment.ticket_id = ?1")
	List<Comment> findByTicketId(Long ticketId);
}