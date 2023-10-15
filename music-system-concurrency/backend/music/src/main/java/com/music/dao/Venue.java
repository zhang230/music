package com.music.dao;

import java.util.List;

public class Venue {
    private Integer Id;
    private String VenueName;
    private String VenueAddress;
    private Integer Delete;
    private Integer eventId;
    private List<VenueSection> venueSectionList;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public String getVenueAddress() {
        return VenueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        VenueAddress = venueAddress;
    }

    public Integer getDelete() {
        return Delete;
    }

    public void setDelete(Integer delete) {
        Delete = delete;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public List<VenueSection> getVenueSectionList() {
        return venueSectionList;
    }

    public void setVenueSectionList(List<VenueSection> venueSectionList) {
        this.venueSectionList = venueSectionList;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "Id=" + Id +
                ", VenueName='" + VenueName + '\'' +
                ", VenueAddress='" + VenueAddress + '\'' +
                ", Delete=" + Delete +
                ", eventId=" + eventId +
                ", venueSectionList=" + venueSectionList +
                '}';
    }
}
