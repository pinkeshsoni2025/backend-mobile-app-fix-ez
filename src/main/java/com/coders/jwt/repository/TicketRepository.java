package com.coders.jwt.repository;
import com.coders.jwt.models.Ticket;
import com.itextpdf.text.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface Ticket repository.
 *
 * @author 
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	
}