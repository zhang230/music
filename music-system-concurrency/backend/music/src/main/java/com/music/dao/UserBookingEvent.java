package com.music.dao;

public class UserBookingEvent {
    private Integer Id;
    private Integer UserId;
    private Integer EventId;
    private Integer Booking;
    private Integer Delete;
    private Integer VenueSectionId;
    private Integer VenueId;
    private Integer Number;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getEventId() {
        return EventId;
    }

    public void setEventId(Integer eventId) {
        EventId = eventId;
    }

    public Integer getBooking() {
        return Booking;
    }

    public void setBooking(Integer booking) {
        Booking = booking;
    }

    public Integer getDelete() {
        return Delete;
    }

    public void setDelete(Integer delete) {
        Delete = delete;
    }

    public Integer getVenueSectionId() {
        return VenueSectionId;
    }

    public void setVenueSectionId(Integer venueSectionId) {
        VenueSectionId = venueSectionId;
    }

    public Integer getVenueId() {
        return VenueId;
    }

    public void setVenueId(Integer venueId) {
        VenueId = venueId;
    }

    public Integer getNumber() {
        return Number;
    }

    public void setNumber(Integer number) {
        Number = number;
    }

    @Override
    public String toString() {
        return "UserBookingEvent{" +
                "Id=" + Id +
                ", UserId=" + UserId +
                ", EventId=" + EventId +
                ", Booking=" + Booking +
                ", Delete=" + Delete +
                ", VenueSectionId=" + VenueSectionId +
                ", VenueId=" + VenueId +
                ", Number=" + Number +
                '}';
    }
}
