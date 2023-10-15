package com.music.dao;

import java.sql.Timestamp;

public class Event {
    private Integer Id;
    private String EventPlannerName;
    private String EventName;
    private Timestamp StartTime;
    private Timestamp EndTime;
    private Integer userId;
    private Integer delete;
    private String lastModifiedName;
    private Venue venue;
    private User user;
    private UserBookingEvent userBookingEvent;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEventPlannerName() {
        return EventPlannerName;
    }

    public void setEventPlannerName(String eventPlannerName) {
        EventPlannerName = eventPlannerName;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public Timestamp getStartTime() {
        return StartTime;
    }

    public void setStartTime(Timestamp startTime) {
        StartTime = startTime;
    }

    public Timestamp getEndTime() {
        return EndTime;
    }

    public void setEndTime(Timestamp endTime) {
        EndTime = endTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public String getLastModifiedName() {
        return lastModifiedName;
    }

    public void setLastModifiedName(String lastModifiedName) {
        this.lastModifiedName = lastModifiedName;
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

    public UserBookingEvent getUserBookingEvent() {
        return userBookingEvent;
    }

    public void setUserBookingEvent(UserBookingEvent userBookingEvent) {
        this.userBookingEvent = userBookingEvent;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Id=" + Id +
                ", EventPlannerName='" + EventPlannerName + '\'' +
                ", EventName='" + EventName + '\'' +
                ", StartTime=" + StartTime +
                ", EndTime=" + EndTime +
                ", userId=" + userId +
                ", delete=" + delete +
                ", lastModifiedName='" + lastModifiedName + '\'' +
                ", venue=" + venue +
                ", user=" + user +
                ", userBookingEvent=" + userBookingEvent +
                '}';
    }
}
