package com.music.dao;

public class VenueSection {
    private Integer Id;
    private Integer VenueId;
    private String SectionName;
    private Double Price;
    private Integer Capacity;
    private Integer Delete;

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

    @Override
    public String toString() {
        return "VenueSection{" +
                "Id=" + Id +
                ", VenueId=" + VenueId +
                ", SectionName='" + SectionName + '\'' +
                ", Price=" + Price +
                ", Capacity=" + Capacity +
                ", Delete=" + Delete +
                '}';
    }
}
