package com.ng.crud.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ng.crud.model.Event;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EventControllerTest
{
	@Autowired
    private MockMvc mvc;
	
	public EventControllerTest() {	}

	@Test
	public void testCreateGetAll() throws Exception
	{
		Event e = getDummyEvent();
		
		mvc.perform(post("/api/event").contentType(MediaType.APPLICATION_JSON_UTF8)
	            .content(asJsonString(e))
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())                   
	            .andExpect(content().contentType
	                 (MediaType.APPLICATION_JSON_UTF8_VALUE));
		
		mvc.perform(get("/api/event")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", hasSize(1)))
			      .andExpect(jsonPath("$[0].title", is(e.getTitle())));
		
		System.out.println("event with title '"+e.getTitle()+"' saved.");
	}

	private Event getDummyEvent()
	{
		Event e = new Event();
		e.setTitle("title");
		e.setLocation("location");
		e.setDescription("description");
		e.setStartDate(3L);
		return e;
	}

	public static String asJsonString(final Object obj)
	{
	    try
	    {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        System.out.println(jsonContent);
	        return jsonContent;
	    }
	    catch (Exception e)
	    {
	        throw new RuntimeException(e);
	    }
	}
}
