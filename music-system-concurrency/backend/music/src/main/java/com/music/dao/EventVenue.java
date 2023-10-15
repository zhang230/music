package com.music.dao;

public class EventVenue {
    private Integer Id;
    private Integer EventId;
    private Integer VenueId;
    private Integer Delete;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getEventId() {
        return EventId;
    }

    public void setEventId(Integer eventId) {
        EventId = eventId;
    }

    public Integer getVenueId() {
        return VenueId;
    }

    public void setVenueId(Integer venueId) {
        VenueId = venueId;
    }

    public Integer getDelete() {
        return Delete;
    }

    public void setDelete(Integer delete) {
        Delete = delete;
    }

    @Override
    public String toString() {
        return "EventVenue{" +
                "Id=" + Id +
                ", EventId=" + EventId +
                ", VenueId=" + VenueId +
                ", Delete=" + Delete +
                '}';
    }
}

