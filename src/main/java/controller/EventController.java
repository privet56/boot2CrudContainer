package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ng.crud.model.Event;

@RestController
@RequestMapping(value = "/api/event")
public class EventController
{
	@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Event> events()
	{
		List<Event> l = new ArrayList<Event>();
        return l;
    }
	@RequestMapping(value = "/id", method = RequestMethod.GET)
    public @ResponseBody Event event(@RequestParam("id") String id)
	{
        return new Event();
    }
}
