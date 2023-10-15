package com.music.mapper;

import com.music.dao.*;
import com.music.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventPlannerMapper {

    private JDBCUtils jdbcUtils;

    public EventPlannerMapper() {
        jdbcUtils = new JDBCUtils();
    }

    public Boolean isConflictTime(Integer venueId, Event event) {
        String sql="select * from event_venue, event where event_venue.venue_id="+venueId+" " +
                "and event_venue.event_id=event.id and ('"+event.getStartTime()+"' <= event.end_time and '"+event.getEndTime()+"' >= event.start_time) for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);

            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if(resultSet.next()) {
                return true;//冲突了
            } else {
                return false;
            }

        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public Integer insertEvent(Event event) {
        //sql inject
        String sql = "insert into public.event(event_planner_name,event_name,start_time,end_time,user_id,\"delete\") " +
                "values(?,?,?,?,?,?) returning id";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.setString(1, event.getEventPlannerName());
            statement.setString(2,event.getEventName());
            statement.setTimestamp(3, event.getStartTime());
            statement.setTimestamp(4, event.getEndTime());
            statement.setInt(5, event.getUserId());
            statement.setInt(6, event.getDelete());
            ResultSet resultSet = statement.executeQuery();
            ResultSet rs = statement.getGeneratedKeys();
            connection.commit();
            connection.close();
            if (resultSet.next()) {
                int insertedId = resultSet.getInt("id");
                return insertedId;
            }else {
                return null;
            }
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void insertEventVenue(EventVenue eventVenue) {
        //sql inject
        String sql = "insert into public.event_venue(event_id,venue_id,\"delete\") " +
                "values(?,?,?) returning id";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.setInt(1, eventVenue.getEventId());
            statement.setInt(2,eventVenue.getVenueId());
            statement.setInt(3, eventVenue.getDelete());
            ResultSet resultSet = statement.executeQuery();
            ResultSet rs = statement.getGeneratedKeys();
            connection.commit();
            connection.close();
            if (resultSet.next()) {
                int insertedId = resultSet.getInt("id");
            }else {

            }
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void updateVenueSectionPriceByVenueIdAndId(VenueSectionForEventplannerCreate section) {
        //sql inject
        String sql = "update public.venue_section_for_eventplanner_create set price="+section.getPrice()+" where venue_id="+section.getVenueId()+" and id="+section.getId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public List<Event> findAllEvets() {
        String sql="select * from event where event.delete=0 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            List<Event> events = new ArrayList<Event>();
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            while(resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setEventPlannerName(resultSet.getString("event_planner_name"));
                event.setEventName(resultSet.getString("event_name"));
                event.setStartTime(resultSet.getTimestamp("start_time"));
                event.setEndTime(resultSet.getTimestamp("end_time"));
                event.setDelete(resultSet.getInt("delete"));
                event.setUserId(resultSet.getInt("user_id"));
                event.setLastModifiedName(resultSet.getString("last_modified_name"));
                events.add(event);
            }
            return events;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public Venue findVenueByEventId(Event event) {
        String sql="select * from venue, event_venue where event_venue.delete=0 and venue.delete=0 and event_venue.event_id="+event.getId()+
                " and event_venue.venue_id=venue.id for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            Venue venue = new Venue();
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if(resultSet.next()) {

                venue.setId(resultSet.getInt("id"));
                venue.setEventId(resultSet.getInt("event_id"));
                venue.setVenueName(resultSet.getString("venue_name"));
                venue.setVenueAddress(resultSet.getString("venue_address"));
                venue.setDelete(resultSet.getInt("delete"));

            }
            return venue;
        }catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public List<VenueSection> findVenueSectionsByVenueId(Venue tv) {
        String sql="select * from venue_section_for_eventplanner_create where venue_section_for_eventplanner_create.delete=0 and " +
                "venue_id="+tv.getId()+" and event_id="+tv.getEventId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            List<VenueSection> venueSections = new ArrayList<VenueSection>();

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                VenueSection venueSection = new VenueSection();
                venueSection.setId(resultSet.getInt("id"));
                venueSection.setVenueId(resultSet.getInt("venue_id"));
                venueSection.setSectionName(resultSet.getString("section_name"));
                venueSection.setPrice(resultSet.getDouble("price"));
                venueSection.setCapacity(resultSet.getInt("capacity"));
                venueSection.setDelete(resultSet.getInt("delete"));
                venueSections.add(venueSection);
            }
            return venueSections;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean isRepeatEventName(Event event) {
        String sql="select * from event where event.delete=0 and event_name='"+event.getEventName()+"'";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void insertVenueSectionForEventplannerCreate(VenueSectionForEventplannerCreate section) {
        //sql inject
        String sql = "insert into public.venue_section_for_eventplanner_create(venue_id,section_name,price,capacity,\"delete\",event_id) " +
                "values(?,?,?,?,?,?) returning id";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.setInt(1, section.getVenueId());
            statement.setString(2,section.getSectionName());
            statement.setDouble(3, section.getPrice());
            statement.setInt(4, section.getCapacity());
            statement.setInt(5, section.getDelete());
            statement.setInt(6, section.getEventId());

            ResultSet resultSet = statement.executeQuery();
            ResultSet rs = statement.getGeneratedKeys();
            connection.commit();
            connection.close();
            if (resultSet.next()) {
                int insertedId = resultSet.getInt("id");
            }else {

            }
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }

    }

    public void updateEvent(Event event) {
        //sql inject
        String sql = "update public.event set event_name='"+event.getEventName()+"', " +
                "start_time='"+event.getStartTime()+"', end_time='"+event.getEndTime()+"', " +
                "last_modified_name='"+event.getLastModifiedName()+"' " +
                "where id="+event.getId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateVenueSectionFor(VenueSectionForEventplannerCreate v) {
        //sql inject
        String sql = "update public.venue_section_for_eventplanner_create set price="+v.getPrice()+" where event_id="+v.getEventId()+" " +
                "and venue_id="+v.getVenueId()+" and id="+v.getId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void deleteEventByEventId(Event event) {
        //sql inject
        String sql = "update public.event set \"delete\"=1 where id="+event.getId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void deleteEventVenueByEventId(Event event) {
        //sql inject
        String sql = "update public.event_venue set \"delete\"=1 where event_id="+event.getId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void deleteVSFEC(Event event) {
        //sql inject
        String sql = "update public.venue_section_for_eventplanner_create set \"delete\"=1 where event_id="+event.getId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public List<Event> findAllEventsByUserId(Integer user_id) {
        String sql="select * from event where event.delete=0 and user_id="+user_id+" for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            List<Event> events = new ArrayList<Event>();
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            while(resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getInt("id"));
                event.setEventPlannerName(resultSet.getString("event_planner_name"));
                event.setEventName(resultSet.getString("event_name"));
                event.setStartTime(resultSet.getTimestamp("start_time"));
                event.setEndTime(resultSet.getTimestamp("end_time"));
                event.setDelete(resultSet.getInt("delete"));
                event.setUserId(resultSet.getInt("user_id"));
                events.add(event);
            }
            return events;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }


    public List<UserBookingEvent> findAllUserBookingEventByEventId(Event event) {
        String sql="select * from user_booking_event where user_booking_event.delete=0 " +
                "and user_booking_event.booking=1 and user_booking_event.event_id="+event.getId()+" for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            List<UserBookingEvent> userBookings = new ArrayList<UserBookingEvent>();
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            while(resultSet.next()) {
                UserBookingEvent tuserBooking = new UserBookingEvent();
                tuserBooking.setId(resultSet.getInt("id"));
                tuserBooking.setUserId(resultSet.getInt("user_id"));
                tuserBooking.setEventId(resultSet.getInt("event_id"));
                tuserBooking.setBooking(resultSet.getInt("booking"));
                tuserBooking.setDelete(resultSet.getInt("delete"));
                tuserBooking.setVenueSectionId(resultSet.getInt("venue_section_id"));
                tuserBooking.setVenueId(resultSet.getInt("venue_id"));
                userBookings.add(tuserBooking);
            }
            return userBookings;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public List<Venue> findVenueByUserBookingEventEventId(Event event) {
        String sql="select * from venue, user_booking_event where user_booking_event.delete=0 and venue.delete=0 " +
                "and venue.id=user_booking_event.venue_id and user_booking_event.event_id="+event.getId()+" for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            List<Venue> venues = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            while(resultSet.next()) {
                Venue venue = new Venue();
                venue.setId(resultSet.getInt("id"));
                venue.setEventId(resultSet.getInt("event_id"));
                venue.setVenueName(resultSet.getString("venue_name"));
                venue.setVenueAddress(resultSet.getString("venue_address"));
                venue.setDelete(resultSet.getInt("delete"));
                venues.add(venue);
            }
            return venues;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }


    public List<BookEventPlanner> findBooksByCreateUserId(Integer user_id) {
        String sql="select * from event as e, user_booking_event as ube, \"user\" as u, venue as v, venue_section_for_eventplanner_create as vsfec \n" +
                "where e.user_id="+user_id+" and e.id=ube.event_id and ube.user_id=u.id and ube.venue_id=v.id and ube.venue_section_id=vsfec.id and ube.booking=1 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            List<BookEventPlanner> bookEventPlanners = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            while(resultSet.next()) {
                BookEventPlanner bookEventPlanner = new BookEventPlanner();

                Event event = new Event();
                event.setId(resultSet.getInt(1));
                event.setEventPlannerName(resultSet.getString(2));
                event.setEventName(resultSet.getString(3));
                event.setStartTime(resultSet.getTimestamp(4));
                event.setEndTime(resultSet.getTimestamp(5));
                event.setDelete(resultSet.getInt(6));
                event.setUserId(resultSet.getInt(7));
                event.setLastModifiedName(resultSet.getString(8));
                bookEventPlanner.setEvent(event);

                UserBookingEvent userBookingEvent = new UserBookingEvent();
                userBookingEvent.setId(resultSet.getInt(9));
                userBookingEvent.setUserId(resultSet.getInt(10));
                userBookingEvent.setEventId(resultSet.getInt(11));
                userBookingEvent.setBooking(resultSet.getInt(12));
                userBookingEvent.setDelete(resultSet.getInt(13));
                userBookingEvent.setVenueSectionId(resultSet.getInt(14));
                userBookingEvent.setVenueId(resultSet.getInt(15));
                userBookingEvent.setNumber(resultSet.getInt(16));
                bookEventPlanner.setUserBookingEvent(userBookingEvent);

                User user = new User();
                user.setId(resultSet.getInt(17));
                user.setUserName(resultSet.getString(18));
                user.setUserPwd(resultSet.getString(19));
                user.setName(resultSet.getString(20));
                user.setAge(resultSet.getInt(21));
                user.setGender(resultSet.getString(22));
                user.setEmail(resultSet.getString(23));
                user.setRole(resultSet.getInt(24));
                user.setDelete(resultSet.getInt(25));
                bookEventPlanner.setUser(user);

                Venue venue = new Venue();
                venue.setId(resultSet.getInt(26));
                venue.setVenueName(resultSet.getString(27));
                venue.setVenueAddress(resultSet.getString(28));
                venue.setDelete(resultSet.getInt(29));
                bookEventPlanner.setVenue(venue);

                VenueSectionForEventplannerCreate venueSectionForEventplannerCreate = new VenueSectionForEventplannerCreate();
                venueSectionForEventplannerCreate.setId(resultSet.getInt(30));
                venueSectionForEventplannerCreate.setVenueId(resultSet.getInt(31));
                venueSectionForEventplannerCreate.setSectionName(resultSet.getString(32));
                venueSectionForEventplannerCreate.setPrice(resultSet.getDouble(33));
                venueSectionForEventplannerCreate.setCapacity(resultSet.getInt(34));
                venueSectionForEventplannerCreate.setDelete(resultSet.getInt(35));
                venueSectionForEventplannerCreate.setEventId(resultSet.getInt(36));
                venueSectionForEventplannerCreate.setAddCapacity(resultSet.getInt(37));
                bookEventPlanner.setVenueSectionForEventplannerCreate(venueSectionForEventplannerCreate);

                bookEventPlanners.add(bookEventPlanner);
            }
            return bookEventPlanners;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public Boolean IfAnyUserBook(UserBookingEvent u) {
        String sql="select * from  user_booking_event as ube where ube.id="+u.getId()+" and ube.user_id="+u.getUserId()+" " +
                "and ube.event_id="+u.getEventId()+" and ube.venue_section_id="+u.getVenueSectionId()+" and ube.venue_id="+u.getVenueId()+" and ube.booking=1 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if(resultSet.next()) {
               return true;
            }
            return false;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void cancelBooking(UserBookingEvent u) {
//        String sql="select * from event as e, user_booking_event as ube, \"user\" as u, venue as v, venue_section_for_eventplanner_create as vsfec \n" +
//                "where e.user_id="+user_id+" and e.id=ube.event_id and ube.user_id=u.id and ube.venue_id=v.id and ube.venue_section_id=vsfec.id ";
        //sql inject
        String sql = "update user_booking_event set booking=0 where id="+u.getId()+" " +
                "and user_id="+u.getUserId()+" and event_id="+u.getEventId()+" " +
                "and venue_section_id="+u.getVenueSectionId()+" and venue_id="+u.getVenueId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public List<BookEventPlanner> findBooksByCreateUserName(String inputUserName) {
        String sql="select * from event as e, user_booking_event as ube, \"user\" as u, venue as v, venue_section_for_eventplanner_create as vsfec \n" +
                "where u.user_name='"+inputUserName+"' and e.id=ube.event_id and ube.user_id=u.id and ube.venue_id=v.id and ube.venue_section_id=vsfec.id and ube.booking=1 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            List<BookEventPlanner> bookEventPlanners = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            while(resultSet.next()) {
                BookEventPlanner bookEventPlanner = new BookEventPlanner();

                Event event = new Event();
                event.setId(resultSet.getInt(1));
                event.setEventPlannerName(resultSet.getString(2));
                event.setEventName(resultSet.getString(3));
                event.setStartTime(resultSet.getTimestamp(4));
                event.setEndTime(resultSet.getTimestamp(5));
                event.setDelete(resultSet.getInt(6));
                event.setUserId(resultSet.getInt(7));
                event.setLastModifiedName(resultSet.getString(8));
                bookEventPlanner.setEvent(event);

                UserBookingEvent userBookingEvent = new UserBookingEvent();
                userBookingEvent.setId(resultSet.getInt(9));
                userBookingEvent.setUserId(resultSet.getInt(10));
                userBookingEvent.setEventId(resultSet.getInt(11));
                userBookingEvent.setBooking(resultSet.getInt(12));
                userBookingEvent.setDelete(resultSet.getInt(13));
                userBookingEvent.setVenueSectionId(resultSet.getInt(14));
                userBookingEvent.setVenueId(resultSet.getInt(15));
                userBookingEvent.setNumber(resultSet.getInt(16));
                bookEventPlanner.setUserBookingEvent(userBookingEvent);

                User user = new User();
                user.setId(resultSet.getInt(17));
                user.setUserName(resultSet.getString(18));
                user.setUserPwd(resultSet.getString(19));
                user.setName(resultSet.getString(20));
                user.setAge(resultSet.getInt(21));
                user.setGender(resultSet.getString(22));
                user.setEmail(resultSet.getString(23));
                user.setRole(resultSet.getInt(24));
                user.setDelete(resultSet.getInt(25));
                bookEventPlanner.setUser(user);

                Venue venue = new Venue();
                venue.setId(resultSet.getInt(26));
                venue.setVenueName(resultSet.getString(27));
                venue.setVenueAddress(resultSet.getString(28));
                venue.setDelete(resultSet.getInt(29));
                bookEventPlanner.setVenue(venue);

                VenueSectionForEventplannerCreate venueSectionForEventplannerCreate = new VenueSectionForEventplannerCreate();
                venueSectionForEventplannerCreate.setId(resultSet.getInt(30));
                venueSectionForEventplannerCreate.setVenueId(resultSet.getInt(31));
                venueSectionForEventplannerCreate.setSectionName(resultSet.getString(32));
                venueSectionForEventplannerCreate.setPrice(resultSet.getDouble(33));
                venueSectionForEventplannerCreate.setCapacity(resultSet.getInt(34));
                venueSectionForEventplannerCreate.setDelete(resultSet.getInt(35));
                venueSectionForEventplannerCreate.setEventId(resultSet.getInt(36));
                venueSectionForEventplannerCreate.setAddCapacity(resultSet.getInt(37));
                bookEventPlanner.setVenueSectionForEventplannerCreate(venueSectionForEventplannerCreate);

                bookEventPlanners.add(bookEventPlanner);
            }
            return bookEventPlanners;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void addOne(UserBookingEvent u) {
        String sql="update venue_section_for_eventplanner_create as vsfec set capacity=capacity+"+u.getNumber() +
                ",add_capacity=add_capacity-"+u.getNumber()+" where vsfec.delete=0 and vsfec.venue_id="+u.getVenueId()+" and vsfec.event_id="+u.getEventId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            int i = statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }
}
