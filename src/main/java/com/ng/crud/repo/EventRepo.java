package com.ng.crud.repo;

import com.ng.crud.model.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends PagingAndSortingRepository<Event, String>
{
	
}
