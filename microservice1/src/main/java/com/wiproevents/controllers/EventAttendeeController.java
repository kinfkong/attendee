/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.controllers;


import com.wiproevents.entities.EventAttendee;
import com.wiproevents.entities.criteria.EventAttendeeSearchCriteria;
import com.wiproevents.entities.statuses.EventAttendeeStatus;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.exceptions.ConfigurationException;
import com.wiproevents.exceptions.EntityNotFoundException;
import com.wiproevents.services.EventAttendeeService;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.Paging;
import com.wiproevents.utils.springdata.extensions.SearchResult;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;


/**
 * The event attendee REST controller. Is effectively thread safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RestController
@RequestMapping("/eventAttendees")
@NoArgsConstructor
public class EventAttendeeController {

    /**
     * The service used to perform operations. Should be non-null after injection.
     */
    @Autowired
    private EventAttendeeService eventAttendeeService;

    /**
     * Check if all required fields are initialized properly.
     *
     * @throws ConfigurationException if any required field is not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {
        Helper.checkConfigNotNull(eventAttendeeService, "eventAttendeeService");
    }

    /**
     * This method is used to retrieve an entity.
     *
     * @param id the id of the entity to retrieve
     * @return the match entity
     * @throws IllegalArgumentException if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public EventAttendee get(@PathVariable String id) throws AttendeeException {
        return eventAttendeeService.get(id);
    }

    /**
     * This method is used to create an entity.
     *
     * @param entity the entity to create
     * @return the created entity
     * @throws IllegalArgumentException if entity is null or not valid
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public EventAttendee create(@RequestBody EventAttendee entity) throws AttendeeException  {
        return eventAttendeeService.create(entity);
    }

    /**
     * This method is used to update an entity.
     *
     * @param id the id of the entity to update
     * @param entity the entity to update
     * @return the updated entity
     * @throws IllegalArgumentException if id is null or empty or entity is null or id of entity is null or empty.
     * or id of entity not match id or entity is invalid
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @Transactional
    public EventAttendee update(@PathVariable String id, @RequestBody EventAttendee entity)
            throws AttendeeException  {
        return eventAttendeeService.update(id, entity);
    }

    /**
     * This method is used to delete an entity.
     *
     * @param id the id of the entity to delete
     * @throws IllegalArgumentException if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @Transactional
    public void delete(@PathVariable String id) throws AttendeeException  {
        eventAttendeeService.delete(id);
    }

    /**
     * This method is used to search for entities by criteria and paging params.
     *
     * @param criteria the search criteria
     * @param paging the paging data
     * @return the search result
     * @throws IllegalArgumentException if pageSize is not positive or pageNumber is negative
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(method = RequestMethod.GET)
    public SearchResult<EventAttendee> search(@ModelAttribute EventAttendeeSearchCriteria criteria,
                                          @ModelAttribute Paging paging) throws AttendeeException  {
        return eventAttendeeService.search(criteria, paging);
    }

    /**
     * This method is used to make the entity approved.
     *
     * @param id the id of the entity to update
     * @return the updated entity
     * @throws IllegalArgumentException if id is null or empty or entity is null or id of entity is null or empty.
     * or id of entity not match id or entity is invalid
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{id}/approve", method = RequestMethod.PUT)
    @Transactional
    public EventAttendee approve(@PathVariable String id)
            throws AttendeeException  {
        Helper.checkNullOrEmpty(id, "id");
        EventAttendee entity = eventAttendeeService.get(id);
        entity.setStatus(EventAttendeeStatus.Approved);
        return eventAttendeeService.update(id, entity);
    }

    /**
     * This method is used to make the entity rejected.
     *
     * @param id the id of the entity to update
     * @return the updated entity
     * @throws IllegalArgumentException if id is null or empty or entity is null or id of entity is null or empty.
     * or id of entity not match id or entity is invalid
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{id}/reject", method = RequestMethod.PUT)
    @Transactional
    public EventAttendee reject(@PathVariable String id)
            throws AttendeeException  {
        Helper.checkNullOrEmpty(id, "id");
        EventAttendee entity = eventAttendeeService.get(id);
        entity.setStatus(EventAttendeeStatus.Rejected);
        return eventAttendeeService.update(id, entity);
    }

    /**
     * This method is used to make the entity deleted.
     *
     * @param id the id of the entity to update
     * @return the updated entity
     * @throws IllegalArgumentException if id is null or empty or entity is null or id of entity is null or empty.
     * or id of entity not match id or entity is invalid
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{id}/delete", method = RequestMethod.PUT)
    @Transactional
    public EventAttendee markDelete(@PathVariable String id)
            throws AttendeeException  {
        Helper.checkNullOrEmpty(id, "id");
        EventAttendee entity = eventAttendeeService.get(id);
        entity.setStatus(EventAttendeeStatus.Deleted);
        return eventAttendeeService.update(id, entity);
    }

    /**
     * This method is used to make the entity checkedin.
     *
     * @param id the id of the entity to update
     * @return the updated entity
     * @throws IllegalArgumentException if id is null or empty or entity is null or id of entity is null or empty.
     * or id of entity not match id or entity is invalid
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{id}/checkIn", method = RequestMethod.PUT)
    @Transactional
    public EventAttendee checkIn(@PathVariable String id)
            throws AttendeeException  {
        Helper.checkNullOrEmpty(id, "id");
        EventAttendee entity = eventAttendeeService.get(id);
        entity.setStatus(EventAttendeeStatus.CheckedIn);
        return eventAttendeeService.update(id, entity);
    }
}

