package dk.logb.spring.boot.swagger.demo.event.controller;

import dk.logb.spring.boot.swagger.demo.event.model.Event;
import dk.logb.spring.boot.swagger.demo.event.service.EventService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Events API",
                version = "1.0.0",
                description = "Events API for managing events. It supports basic CRUD operations for events.",
                contact = @Contact(
                        name = "Jakob Bendsen",
                        email = "...@....dk"
                ),
                license = @License(
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html",
                        name = "Apache 2.0"
                )
        )
)
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    //get all events
    @RequestMapping(method= RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    //get event by id
    @Operation(method = "findEventById", summary = "Get an Event by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the event with the given id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid event id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content) })
    @RequestMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public Event getEvent(@PathVariable @Parameter(description = "id of event to be looked up") Integer id){
        return eventService.getEvent(id);
    }

    //add event
    @Operation(method = "createEvent", summary = "Create event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created event successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid event info supplied, e.g. bad dates",
                    content = @Content)})
    @RequestMapping(method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public void addEvent(@RequestBody Event event){
        eventService.addEvent(event);
    }

    @RequestMapping(method = RequestMethod.GET, value="/filter", produces=MediaType.APPLICATION_JSON_VALUE)
    public Page<Event> filterEvents(@ParameterObject Pageable pageable) {
        return Page.empty();
    }

}
