package com.ng.crud.repo;

import com.ng.crud.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//SQL
//@Repository
//public interface EventRepo extends PagingAndSortingRepository<Event, String>
//MongoDB:
public interface EventRepo extends MongoRepository<Event, String>
{
	
}
