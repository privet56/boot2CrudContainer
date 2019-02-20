package com.ng.crud.controller;

import org.springframework.data.domain.PageRequest;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.ng.crud.model.Event;
import com.ng.crud.repo.EventRepo;

@RestController
@RequestMapping(value = "/api/event")
public class EventController
{
	@Autowired
	private EventRepo eventRepo;

	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
//GET - ALL
	/* without paging
	@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Event>> events()
	{
		ArrayList<Event> l = Lists.newArrayList(eventRepo.findAll());
        return new ResponseEntity<>(l, HttpStatus.OK);	//TODO: return page attributes too
    }
	*/
	@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Page<Event> events(
    		@RequestParam(value = "sortBy", required = false) 			String sortBy,
    		@RequestParam(value = "sortDirection", required = false) 	String sortDirection,
    		@RequestParam(value = "page", required = false) 			Integer page,
    		@RequestParam(value = "hitsperpage", required = false) 		Integer hitsperpage)
	{
		if(StringUtils.isBlank(sortDirection))sortDirection = "DESC";
		if(StringUtils.isBlank(sortBy))sortBy = "id";
		if(page == null)page = 0;
		if(hitsperpage == null)hitsperpage = 10;
		
		Pageable pageable = PageRequest.of(page, hitsperpage, Sort.by("DESC".equalsIgnoreCase(sortDirection) ? Direction.DESC : Direction.ASC, sortBy));
		return this.eventRepo.findAll(pageable); //page has totalpages & totalelements
    }
//GET - ONE
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Event> event(@PathVariable("id") String id, HttpServletResponse response)
	{
		if(StringUtils.isBlank(id))
		{
			logger.warn("event-get(1) - no/invalid id:"+id);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Optional<Event> e = eventRepo.findById(id);
		if(!e.isPresent())
		{
			logger.warn("event-get(1) - id-not-found id:"+id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
        return new ResponseEntity<>(e.get(), HttpStatus.OK);
    }
//CREATE
	@PostMapping()
	ResponseEntity<String> newEvent(@Valid @RequestBody Event newEvent, HttpServletResponse response, BindingResult bindingResult) throws ValidationException
	{
		if(bindingResult.hasErrors())
		{
			logger.warn("event-post - new event should be valid:"+bindingResult.toString()+" << "+(newEvent == null ? "null" : newEvent.toString()));
			
			return new ResponseEntity<>(bindingResult.toString(), HttpStatus.BAD_REQUEST);
			//throw new ValidationException(bindingResult.toString());	= alternative
	    }
		
		if(!StringUtils.isBlank(newEvent.getId()))
		{
			logger.warn("event-post - new event should have no id:"+newEvent.getId());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		newEvent.setId(UUID.randomUUID().toString());
		Event savedEvent = eventRepo.save(newEvent);
		return new ResponseEntity<>(savedEvent.getId(), HttpStatus.OK);
	}
//EDIT
	@PutMapping(value = "/{id}")
	ResponseEntity<Boolean> editEvent(@RequestBody Event event, HttpServletResponse response, BindingResult bindingResult) throws ValidationException
	{
		if(bindingResult.hasErrors())
		{
			logger.warn("event-put - event should be valid:"+bindingResult.toString()+" << "+(event == null ? "null" : event.toString()));
			
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
			//throw new ValidationException(bindingResult.toString());	= alternative
	    }

		if(StringUtils.isBlank(event.getId()))
		{
			logger.warn("event-put - event to be edited, should have id:"+event.getId());
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
		}

		eventRepo.save(event);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}
//DEL
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> delEvent(@PathVariable("id") String id, HttpServletResponse response)
	{
		if(StringUtils.isBlank(id))
		{
			logger.warn("event-del - event to be deleted, should have id:"+id);
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
		}

		eventRepo.deleteById(id);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}	
}
