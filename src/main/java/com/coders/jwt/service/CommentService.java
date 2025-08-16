package com.coders.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.coders.jwt.models.Comment;
import com.coders.jwt.payload.request.CommentRequest;
import com.coders.jwt.repository.CommentRepository;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  public Page<Comment> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

      return commentRepository.findAll(pageable);
  }
  
  public List<Comment> listAll() {
      return commentRepository.findAll(Sort.by("message").ascending());
  }



public List<Comment> getAllComments() {
	// TODO Auto-generated method stub
	return this.listAll();
}



}
