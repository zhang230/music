package com.music.service;

import com.music.dao.*;
import com.music.mapper.AdminMapper;

import java.util.List;

public class AdminService {
    private AdminMapper adminMapper;
    public AdminService(){
        adminMapper = new AdminMapper();
    }


    public List<User> adminFindAllUsers() {
        return adminMapper.adminFindAllUsers();
    }

    public List<Event> adminFindAllEvents() {
        return adminMapper.adminFindAllEvents();
    }

    public Integer insertVenue(Venue venue) {
        return adminMapper.insertVenue(venue);
    }

    public Boolean isRepeatVenueName(Venue venue) {
        return adminMapper.isRepeatVenueName(venue);
    }

    public void insertVenueSection(VenueSection section) {
         adminMapper.insertVenueSection(section);
    }

    public List<Venue> findAllVenue() {
        return adminMapper.findAllVenue();
    }

    public void addAllVenueToAllVenueSections(List<Venue> venueList) {
         adminMapper.addAllVenueToAllVenueSections(venueList);
    }

    public void deleteVenueSectionsByVenueId(Venue venue) {
        adminMapper.deleteVenueSectionsByVenueId(venue);
    }

    public void updateVenueByVenueId(Venue venue) {
        adminMapper.updateVenueByVenueId(venue);
    }

    public void deleteVenuesByVenueId(Venue venue) {
        adminMapper.deleteVenuesByVenueId(venue);
    }

    public void deleteVenueSectionsByVenueIdAndVenueSectionId(VenueSection section) {
        adminMapper.deleteVenueSectionsByVenueIdAndVenueSectionId(section);
    }

    public List<UserBookingEvent> findAllUserBookingEvent() {
        return adminMapper.findAllUserBookingEvent();
    }

    public Event findEventByUserId(UserBookingEvent userBookingEvent) {
        return adminMapper.findEventByUserId( userBookingEvent);
    }

    public Venue findVenueByUserId(UserBookingEvent userBookingEvent) {
        return adminMapper.findVenueByUserId( userBookingEvent);
    }

    public VenueSection findVenueSectionsByUserId(UserBookingEvent userBookingEvent) {
        return adminMapper.findVenueSectionsByUserId(userBookingEvent);
    }

    public User findUserByUserId(UserBookingEvent userBookingEvent) {
        return adminMapper.findUserByUserId( userBookingEvent);
    }
}
