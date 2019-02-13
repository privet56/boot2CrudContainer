package com.ng.crud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ng.crud.model.Event;

@RestController
@RequestMapping(value = "/api/event")
public class EventController
{
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
//GET - ALL
	@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Event> events()
	{
		List<Event> l = new ArrayList<Event>();
        return l;
    }
//GET - ONE
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    //public @ResponseBody Event event(@RequestParam("id") Long id)
    public @ResponseBody Event event(@PathVariable("id") Long id, HttpServletResponse response)
	{
		if((id == null) || (id.equals(0L)))
		{
			logger.warn("event-get(1) - no/invalid id:"+id);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
        return new Event();
    }
//CREATE
	@PostMapping()
	String newEvent(@RequestBody Event newEvent, HttpServletResponse response)
	{
		if((newEvent.getId() != null) && !newEvent.getId().equals(0L))
		{
			logger.warn("event-post - new event should have no id:"+newEvent.getId());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		//return repository.save(newEvent);
		return newEvent.getId();
	}
//EDIT
	@PutMapping(value = "/{id}")
	boolean editEvent(@RequestBody Event event, HttpServletResponse response)
	{
		if((event.getId() == null) || event.getId().equals(0L))
		{
			logger.warn("event-put - event to be edited, should have id:"+event.getId());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return false;
		}

		//return repository.save(newEvent);
		return false;
	}
//DEL
	@DeleteMapping("/{id}")
	boolean delEvent(@PathVariable("id") Long id, HttpServletResponse response)
	{
		if((id == null) || id.equals(0L))
		{
			logger.warn("event-del - event to be deleted, should have id:"+id);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return false;
		}

		//return repository.save(newEvent);
		return false;
	}	
}
