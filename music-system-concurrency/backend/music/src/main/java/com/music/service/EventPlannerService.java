package com.music.service;

import com.music.dao.*;
import com.music.mapper.AdminMapper;
import com.music.mapper.EventPlannerMapper;

import java.util.List;

public class EventPlannerService {
    private EventPlannerMapper eventPlannerMapper;
    public EventPlannerService(){
        eventPlannerMapper = new EventPlannerMapper();
    }



    public Boolean isConflictTime(Integer venueId, Event event) {
        return eventPlannerMapper.isConflictTime( venueId,  event);
    }

    public Integer insertEvent(Event event) {
         return eventPlannerMapper.insertEvent(event);
    }

    public void insertEventVenue(EventVenue eventVenue) {
        eventPlannerMapper.insertEventVenue(eventVenue);
    }

    public void updateVenueSectionPriceByVenueIdAndId(VenueSectionForEventplannerCreate section) {
        eventPlannerMapper.updateVenueSectionPriceByVenueIdAndId( section);
    }

    public List<Event> findAllEvents() {
        return eventPlannerMapper.findAllEvets();
    }

    public Venue findVenueByEventId(Event event) {
        return eventPlannerMapper.findVenueByEventId( event);
    }

    public List<VenueSection> findVenueSectionsByVenueId(Venue tv) {
        return eventPlannerMapper.findVenueSectionsByVenueId( tv);
    }

    public Boolean isRepeatEventName(Event event) {
        return eventPlannerMapper.isRepeatEventName(event);
    }

    public void insertVenueSectionForEventplannerCreate(VenueSectionForEventplannerCreate section) {
        eventPlannerMapper.insertVenueSectionForEventplannerCreate( section);
    }

    public void updateEvent(Event event) {
        eventPlannerMapper.updateEvent(event);
    }

    public void updateVenueSectionFor(VenueSectionForEventplannerCreate venueSectionForEventplannerCreate) {
        eventPlannerMapper.updateVenueSectionFor( venueSectionForEventplannerCreate);
    }

    public void deleteEventByEventId(Event event) {
        eventPlannerMapper.deleteEventByEventId( event);
    }

    public void deleteEventVenueByEventId(Event event) {
        eventPlannerMapper.deleteEventVenueByEventId( event);
    }

    public void deleteVSFEC(Event event) {
        eventPlannerMapper.deleteVSFEC(event);
    }

    public List<Event> findAllEventsByUserId(Integer user_id) {
        return eventPlannerMapper.findAllEventsByUserId( user_id);
    }


    public List<UserBookingEvent> findAllUserBookingEventByEventId(Event event) {
        return eventPlannerMapper.findAllUserBookingEventByEventId(event);
    }

    public List<Venue> findVenueByUserBookingEventEventId(Event event) {
        return eventPlannerMapper.findVenueByUserBookingEventEventId( event);
    }


    public List<BookEventPlanner> findBooksByCreateUserId(Integer user_id) {
        return eventPlannerMapper.findBooksByCreateUserId(user_id);
    }

    public Boolean IfAnyUserBook(UserBookingEvent userBookingEvent1) {
        return eventPlannerMapper.IfAnyUserBook(userBookingEvent1);
    }

    public void cancelBooking(UserBookingEvent userBookingEvent) {
        eventPlannerMapper.cancelBooking(userBookingEvent);
    }

    public List<BookEventPlanner> findBooksByCreateUserName(String inputUserName) {
        return eventPlannerMapper.findBooksByCreateUserName(inputUserName);
    }

    public void addOne(UserBookingEvent userBookingEvent1) {
        eventPlannerMapper.addOne(userBookingEvent1);
    }
}
