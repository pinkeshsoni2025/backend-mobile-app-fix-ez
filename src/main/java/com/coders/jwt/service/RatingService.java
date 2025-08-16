package com.coders.jwt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.coders.jwt.models.Rating;
import com.coders.jwt.repository.RatingRepository;

@Service
public class RatingService {

  @Autowired
  private RatingRepository ratingRepository;

  public Page<Rating> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
      Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
      Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

      return ratingRepository.findAll(pageable);
  }
  
  public List<Rating> listAll() {
      return ratingRepository.findAll(Sort.by("comment").ascending());
  }

public List<Rating> getAllRatings() {
	// TODO Auto-generated method stub
	return this.listAll();
}

}
