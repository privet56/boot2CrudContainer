package com.ng.crud.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

		/*MvcResult result = */mvc.perform(get("/api/event")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      //.andExpect(jsonPath("$[\"content\"]", hasSize(1)))	//when in-memory (=clean) db
			      .andExpect(jsonPath("$[\"content\"]", hasSize(greaterThan(0))))
			      .andExpect(jsonPath("$[\"content\"][0].title", is(e.getTitle())));
			      //.andReturn();
		
		//{"content":[{"id":"b75d5ed5-44dc-49f6-bfbc-2d09eff05f6e","title":"title","startDate":3,"endDate":null,"type":null,"location":"location","description":"description","createdBy":null,"lastUpdatedBy":null,"createdAt":1550599585830,"lastUpdatedAt":1550599585830}],"pageable":{"sort":{"sorted":true,"unsorted":false,"empty":false},"offset":0,"pageSize":10,"pageNumber":0,"paged":true,"unpaged":false},"totalPages":1,"totalElements":1,"last":true,"size":10,"number":0,"sort":{"sorted":true,"unsorted":false,"empty":false},"first":true,"numberOfElements":1,"empty":false}
		//String content = result.getResponse().getContentAsString();
		//System.out.println(content);
		
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
	        return jsonContent;
	    }
	    catch (Exception e)
	    {
	        throw new RuntimeException(e);
	    }
	}
}
