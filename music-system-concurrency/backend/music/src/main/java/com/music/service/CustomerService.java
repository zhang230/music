package com.music.service;

import com.music.dao.Event;
import com.music.dao.UserBookingEvent;
import com.music.dao.Venue;
import com.music.dao.VenueSection;
import com.music.mapper.AdminMapper;
import com.music.mapper.CustomerMapper;

import java.util.List;

public class CustomerService {
    private CustomerMapper customerMapper;

    public CustomerService(){
        customerMapper = new CustomerMapper();
    }


    public List<Event> findAllEventsByEventNameAndTime(Event event) {
        return customerMapper.findAllEventsByEventNameAndTime( event);
    }

    public Venue findVenueByEventId(Event event) {
        return customerMapper.findVenueByEventId( event);
    }

    public List<VenueSection> findVenueSectionsByVenueId(Venue tv) {
        return customerMapper.findVenueSectionsByVenueId( tv);
    }

    public Integer insertUserBookingEvent(UserBookingEvent userBookingEvent) {
        return customerMapper.insertUserBookingEvent( userBookingEvent);
    }

    public List<Event> findAllEventsByUserId(Integer user_id) {
        return customerMapper.findAllEventsByUserId( user_id);
    }

    public List<UserBookingEvent> findAllUserBookingEventByUserId(Integer user_id) {
        return customerMapper.findAllUserBookingEventByUserId( user_id);
    }

    public Event findEventByUserId(UserBookingEvent userBookingEvent) {
        return customerMapper.findEventByUserId(userBookingEvent);
    }

    public Venue findVenueByUserId(UserBookingEvent userBookingEvent) {
        return customerMapper.findVenueByUserId( userBookingEvent);
    }

    public VenueSection findVenueSectionsByUserId(UserBookingEvent userBookingEvent) {
        return customerMapper.findVenueSectionsByUserId(userBookingEvent);
    }

    public Integer cancelUserBookingEvent(UserBookingEvent userBookingEvent) {
        return customerMapper.cancelUserBookingEvent( userBookingEvent);
    }

    public Integer isOutOFNumberBooking(UserBookingEvent userBookingEvent) {
        return customerMapper.isOutOFNumberBooking( userBookingEvent);
    }

    public void subOne(UserBookingEvent userBookingEvent) {
        customerMapper.subOne(userBookingEvent);
    }

    public void addOne(UserBookingEvent userBookingEvent) {
        customerMapper.addOne(userBookingEvent);
    }
}
