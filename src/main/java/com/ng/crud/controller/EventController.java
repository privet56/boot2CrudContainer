package com.ng.crud.controller;

import java.util.ArrayList;
import java.util.List;

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
    public @ResponseBody Event event(@PathVariable("id") Long id)
	{
        return new Event();
    }
//CREATE
	@PostMapping()
	Long newEvent(@RequestBody Event newEvent) {
		//return repository.save(newEvent);
		return newEvent.getId();
	}
//EDIT
	@PutMapping(value = "/{id}")
	boolean editEvent(@RequestBody Event event) {
		//return repository.save(newEvent);
		return false;
	}
//DEL
	@DeleteMapping("/{id}")
	boolean delEvent(@PathVariable("id") Long id) {
		//return repository.save(newEvent);
		return false;
	}	
}
