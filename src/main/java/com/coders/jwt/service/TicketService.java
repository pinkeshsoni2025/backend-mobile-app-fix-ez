package com.coders.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.coders.jwt.models.Ticket;
import com.coders.jwt.payload.request.TicketRequest;
import com.coders.jwt.repository.TicketRepository;

@Service
public class TicketService {

  @Autowired
  private TicketRepository tikcetRepository;

  public Page<Ticket> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

      return tikcetRepository.findAll(pageable);
  }
  
  public List<Ticket> listAll() {
      return tikcetRepository.findAll(Sort.by("title").ascending());
  }



public List<Ticket> getAllTickets() {
	// TODO Auto-generated method stub
	return this.listAll();
}

}
