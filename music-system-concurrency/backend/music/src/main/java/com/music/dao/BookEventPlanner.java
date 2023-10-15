package com.music.dao;

public class BookEventPlanner {
    private Event event;
    private UserBookingEvent userBookingEvent;
    private Venue venue;
    private User user;
    private VenueSectionForEventplannerCreate venueSectionForEventplannerCreate;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public UserBookingEvent getUserBookingEvent() {
        return userBookingEvent;
    }

    public void setUserBookingEvent(UserBookingEvent userBookingEvent) {
        this.userBookingEvent = userBookingEvent;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VenueSectionForEventplannerCreate getVenueSectionForEventplannerCreate() {
        return venueSectionForEventplannerCreate;
    }

    public void setVenueSectionForEventplannerCreate(VenueSectionForEventplannerCreate venueSectionForEventplannerCreate) {
        this.venueSectionForEventplannerCreate = venueSectionForEventplannerCreate;
    }
}
