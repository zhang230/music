package com.music.dao;

public class VenueSectionForEventplannerCreate {
    private Integer Id;
    private Integer VenueId;
    private String SectionName;
    private Double Price;
    private Integer Capacity;
    private Integer Delete;
    private Integer EventId;
    private Integer AddCapacity;

    public Integer getAddCapacity() {
        return AddCapacity;
    }

    public void setAddCapacity(Integer addCapacity) {
        AddCapacity = addCapacity;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getVenueId() {
        return VenueId;
    }

    public void setVenueId(Integer venueId) {
        VenueId = venueId;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Integer getCapacity() {
        return Capacity;
    }

    public void setCapacity(Integer capacity) {
        Capacity = capacity;
    }

    public Integer getDelete() {
        return Delete;
    }

    public void setDelete(Integer delete) {
        Delete = delete;
    }

    public Integer getEventId() {
        return EventId;
    }

    public void setEventId(Integer eventId) {
        EventId = eventId;
    }

    @Override
    public String toString() {
        return "VenueSectionForEventplannerCreate{" +
                "Id=" + Id +
                ", VenueId=" + VenueId +
                ", SectionName='" + SectionName + '\'' +
                ", Price=" + Price +
                ", Capacity=" + Capacity +
                ", Delete=" + Delete +
                ", EventId=" + EventId +
                ", AddCapacity=" + AddCapacity +
                '}';
    }
}
