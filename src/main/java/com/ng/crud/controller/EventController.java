package com.ng.crud.controller;

import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.ext.Provider;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

import io.swagger.annotations.*;

@RestController
@RequestMapping(value = "/api/event")
@Produces({"application/json"})
@SwaggerDefinition(
        info = @Info(
                description = "Gets the Events",
                version = "V12.0.12",
                title = "The Events API",
                termsOfService = "http://me.io/terms.html",
                contact = @Contact(
                   name = "me", 
                   email = "me@me.io", 
                   url = "http://me.io"
                ),
                license = @License(
                   name = "Apache 2.0", 
                   url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        consumes = {"application/json"},
        produces = {"application/json"},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        tags = {
                @Tag(name = "Events", description = "Events Tag")
        }, 
        externalDocs = @ExternalDocs(value = "Me", url = "http://me.io/me.html")
)
@Provider
@Path(value = "/api/event")
@Api(value = "/api/event")//, authorizations = { @Authorization(value="sampleoauth", scopes = {})})

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
	@Path("/")
	@ApiOperation(value = "lists events",
	    notes = "lists events, paged & ordered",
	    nickname="events"/*=operationId*/)
	@ApiImplicitParams({ //=query params
		@ApiImplicitParam(name="sortBy", value = "sortBy for the list of events", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name="sortDirection", value = "sortDirection for the list of events", required = false, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name="page", value = "page for the list of events", required = false, dataType = "integer", paramType = "query"),
		@ApiImplicitParam(name="hitsperpage", value = "hitsperpage for the list of events", required = false, dataType = "integer", paramType = "query")
	})
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "internal error when querying the list of events"),
			@ApiResponse(code = 404, message = "nothing found when querying the list of events") })
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
	@Path("/{id}")
	@ApiOperation(value = "gets a single event",
	    notes = "gets a single event - by id",
	    response = Event.class)
	//@ApiParam(value = "id", required = true) //=path param, can be put in function declaration too
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "internal error when reading the event"),
			@ApiResponse(code = 404, message = "nothing found when reading the event") })

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Event> event(@ApiParam(value = "id", required = true) @PathVariable("id") String id, HttpServletResponse response)
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
	@Path("/")
	@ApiOperation(value = "creates a single event",
	    notes = "creates a single event - provide no id!",
	    response = String.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "internal error when creating the new event"),
			@ApiResponse(code = 404, message = "nothing found when creating the new event") })

	@PostMapping()
	ResponseEntity<String> newEvent(@ApiParam(value = "newEvent", required = true) @Valid @RequestBody Event newEvent, HttpServletResponse response, BindingResult bindingResult) throws ValidationException
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
	@Path("/{id}")
	@ApiOperation(value = "updates a single event",
	    notes = "updates a single event - provide an id in the event!",
	    response = Boolean.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "internal error when writing the event"),
			@ApiResponse(code = 404, message = "nothing found when writing the event") })

	@PutMapping(value = "/{id}")
	ResponseEntity<Boolean> editEvent(@ApiParam(value = "event", required = true) @RequestBody Event event, HttpServletResponse response, BindingResult bindingResult) throws ValidationException
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
	@Path("/{id}")
	@ApiOperation(value = "deletes a single event",
	    notes = "deletes a single event - provide an id!",
	    response = Boolean.class,
	    produces = "application/json")
	@ApiResponses(value = { 
			@ApiResponse(code = 400, message = "internal error when deleting the event"),
			@ApiResponse(code = 404, message = "nothing found when deleting the event") })
	@Produces({"application/json"})
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> delEvent(@ApiParam(value = "id", required = true) @PathVariable("id") String id, HttpServletResponse response)
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
