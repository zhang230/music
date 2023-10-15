package com.music.mapper;

import com.music.dao.Event;
import com.music.dao.UserBookingEvent;
import com.music.dao.Venue;
import com.music.dao.VenueSection;
import com.music.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {
    private JDBCUtils jdbcUtils;

    public CustomerMapper() {
        jdbcUtils = new JDBCUtils();
    }

    public List<Event> findAllEventsByEventNameAndTime(Event event) {
        if(event.getStartTime() == null && event.getEndTime() == null && event.getEventName().equals("")) {
//            System.out.println("1");
            //customer serach
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
                    Event tevent = new Event();
                    tevent.setId(resultSet.getInt("id"));
                    tevent.setEventPlannerName(resultSet.getString("event_planner_name"));
                    tevent.setEventName(resultSet.getString("event_name"));
                    tevent.setStartTime(resultSet.getTimestamp("start_time"));
                    tevent.setEndTime(resultSet.getTimestamp("end_time"));
                    tevent.setDelete(resultSet.getInt("delete"));
                    tevent.setUserId(resultSet.getInt("user_id"));
                    events.add(tevent);
                }
                return events;
            }catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else if(event.getStartTime() == null || event.getEndTime() == null) {
//            System.out.println("2");
            //customer serach
            String sql="select * from event where event.delete=0 and event_name like '"+event.getEventName()+"' for update";
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
                    Event tevent = new Event();
                    tevent.setId(resultSet.getInt("id"));
                    tevent.setEventPlannerName(resultSet.getString("event_planner_name"));
                    tevent.setEventName(resultSet.getString("event_name"));
                    tevent.setStartTime(resultSet.getTimestamp("start_time"));
                    tevent.setEndTime(resultSet.getTimestamp("end_time"));
                    tevent.setDelete(resultSet.getInt("delete"));
                    tevent.setUserId(resultSet.getInt("user_id"));
                    events.add(tevent);
                }
                return events;
            }catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else if(event.getEventName().equals("")){
//            System.out.println("3");
            //customer serach
            String sql="select * from event where event.delete=0 "+
                    "and start_time>='"+event.getStartTime()+"' and end_time<='"+event.getEndTime()+"' for update";
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
                    Event tevent = new Event();
                    tevent.setId(resultSet.getInt("id"));
                    tevent.setEventPlannerName(resultSet.getString("event_planner_name"));
                    tevent.setEventName(resultSet.getString("event_name"));
                    tevent.setStartTime(resultSet.getTimestamp("start_time"));
                    tevent.setEndTime(resultSet.getTimestamp("end_time"));
                    tevent.setDelete(resultSet.getInt("delete"));
                    tevent.setUserId(resultSet.getInt("user_id"));
                    events.add(tevent);
                }
                return events;
            }catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
//            System.out.println("4");
            //customer serach
            String sql="select * from event where event.delete=0 and event_name like '"+event.getEventName()+"' " +
                    "and start_time>='"+event.getStartTime()+"' and end_time<='"+event.getEndTime()+"' for update";
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
                    Event tevent = new Event();
                    tevent.setId(resultSet.getInt("id"));
                    tevent.setEventPlannerName(resultSet.getString("event_planner_name"));
                    tevent.setEventName(resultSet.getString("event_name"));
                    tevent.setStartTime(resultSet.getTimestamp("start_time"));
                    tevent.setEndTime(resultSet.getTimestamp("end_time"));
                    tevent.setDelete(resultSet.getInt("delete"));
                    tevent.setUserId(resultSet.getInt("user_id"));
                    events.add(tevent);
                }
                return events;
            }catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                throw new RuntimeException(e);
            }
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
            ;
            throw new RuntimeException(e);
        }
    }

    public List<VenueSection> findVenueSectionsByVenueId(Venue tv) {
        String sql="select * from venue_section_for_eventplanner_create where venue_section_for_eventplanner_create.delete=0 and " +
                "venue_id="+tv.getId()+" and event_id="+tv.getEventId()+" for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            List<VenueSection> venueSections = new ArrayList<VenueSection>();

            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
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
            ;
            throw new RuntimeException(e);
        }
    }

    public Integer insertUserBookingEvent(UserBookingEvent userBookingEvent) {
        //sql inject
        String sql = "insert into public.user_booking_event(user_id,event_id,venue_section_id,venue_id,booking,\"delete\", number) " +
                "values(?,?,?,?,?,?,?) returning id";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.setInt(1, userBookingEvent.getUserId());
            statement.setInt(2, userBookingEvent.getEventId());
            statement.setInt(3, userBookingEvent.getVenueSectionId());
            statement.setInt(4, userBookingEvent.getVenueId());
            statement.setInt(5, userBookingEvent.getBooking());
            statement.setInt(6, userBookingEvent.getDelete());
            statement.setInt(7, userBookingEvent.getNumber());
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

    public List<Event> findAllEventsByUserId(Integer user_id) {
        String sql="select * from event, user_booking_event where event.delete=0 and user_booking_event.delete=0 " +
                "and event.id=user_booking_event.event_id and user_booking_event.booking=1 and user_booking_event.user_id="+user_id+" for update";
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
                Event tevent = new Event();
                tevent.setId(resultSet.getInt("id"));
                tevent.setEventPlannerName(resultSet.getString("event_planner_name"));
                tevent.setEventName(resultSet.getString("event_name"));
                tevent.setStartTime(resultSet.getTimestamp("start_time"));
                tevent.setEndTime(resultSet.getTimestamp("end_time"));
                tevent.setDelete(resultSet.getInt("delete"));
                tevent.setUserId(resultSet.getInt("user_id"));
                events.add(tevent);
            }
            return events;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public List<UserBookingEvent> findAllUserBookingEventByUserId(Integer user_id) {
        String sql="select * from user_booking_event where user_booking_event.delete=0 and user_booking_event.booking=1 and user_booking_event.user_id="+user_id+" for update";
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
                tuserBooking.setNumber(resultSet.getInt("number"));
                userBookings.add(tuserBooking);
            }
            return userBookings;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public Event findEventByUserId(UserBookingEvent userBookingEvent) {
        String sql="select * from event, user_booking_event where event.delete=0 and user_booking_event.delete=0 " +
                "and event.id=user_booking_event.event_id and user_booking_event.booking=1 and " +
                "user_booking_event.user_id="+userBookingEvent.getUserId()+" and user_booking_event.event_id="+userBookingEvent.getEventId()+" for update";
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
                Event tevent = new Event();
                tevent.setId(resultSet.getInt("id"));
                tevent.setEventPlannerName(resultSet.getString("event_planner_name"));
                tevent.setEventName(resultSet.getString("event_name"));
                tevent.setStartTime(resultSet.getTimestamp("start_time"));
                tevent.setEndTime(resultSet.getTimestamp("end_time"));
                tevent.setDelete(resultSet.getInt("delete"));
                tevent.setUserId(resultSet.getInt("user_id"));
                return tevent;
            }
            return null;
        }catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public Venue findVenueByUserId(UserBookingEvent userBookingEvent) {
        String sql="select * from venue, user_booking_event where venue.delete=0 and user_booking_event.delete=0 " +
                "and venue.id=user_booking_event.venue_id and user_booking_event.booking=1 and user_booking_event.user_id="+userBookingEvent.getUserId()+" for update";
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
                Venue venue = new Venue();
                venue.setId(resultSet.getInt("id"));
                venue.setVenueName(resultSet.getString("venue_name"));
                venue.setVenueAddress(resultSet.getString("venue_address"));
                venue.setDelete(resultSet.getInt("delete"));
                return venue;
            }
            return null;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public VenueSection findVenueSectionsByUserId(UserBookingEvent userBookingEvent) {
        String sql="select * from venue_section_for_eventplanner_create, user_booking_event where venue_section_for_eventplanner_create.delete=0 and user_booking_event.delete=0 " +
                "and venue_section_for_eventplanner_create.id=user_booking_event.venue_section_id and user_booking_event.booking=1 and user_booking_event.user_id="+userBookingEvent.getUserId()+" " +
                "and user_booking_event.venue_section_id="+userBookingEvent.getVenueSectionId()+" for update";
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
                VenueSection venueSection = new VenueSection();
                venueSection.setId(resultSet.getInt("id"));
                venueSection.setVenueId(resultSet.getInt("venue_id"));
                venueSection.setSectionName(resultSet.getString("section_name"));
                venueSection.setPrice(resultSet.getDouble("price"));
                venueSection.setCapacity(resultSet.getInt("capacity"));
                venueSection.setDelete(resultSet.getInt("delete"));
                return venueSection;
            }
            return null;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public Integer cancelUserBookingEvent(UserBookingEvent userBookingEvent) {
        //sql inject
        String sql = "update public.user_booking_event set booking=0 where user_id="+userBookingEvent.getUserId()+" " +
                "and event_id="+userBookingEvent.getEventId()+" and venue_section_id="+userBookingEvent.getVenueSectionId()+" " +
                "and venue_id="+userBookingEvent.getVenueId() +" and id="+userBookingEvent.getId();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            int i = statement.executeUpdate();
            connection.commit();
            connection.close();
            return i;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public Integer isOutOFNumberBooking(UserBookingEvent u) {
        String sql="select * from  venue_section_for_eventplanner_create as vsfec" +
                " where vsfec.delete=0 and vsfec.venue_id="+u.getVenueId()+" and vsfec.event_id="+u.getEventId()+" " +
                "and vsfec.id="+u.getVenueSectionId()+" and vsfec.capacity-"+u.getNumber()+">=0 for update";
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
                return 0;//can book
            }
            return 1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void subOne(UserBookingEvent u) {
        String sql="update venue_section_for_eventplanner_create as vsfec set capacity=capacity-"+u.getNumber() +
                ",add_capacity=add_capacity+"+u.getNumber()+" where vsfec.delete=0 and vsfec.venue_id="+u.getVenueId()+" and vsfec.event_id="+u.getEventId()+" " +
                "and vsfec.capacity-"+u.getNumber()+">=0 and vsfec.id="+u.getVenueSectionId();
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

    public void addOne(UserBookingEvent u) {
        String sql="update venue_section_for_eventplanner_create as vsfec set capacity=capacity+"+u.getNumber() +
                ",add_capacity=add_capacity-"+u.getNumber()+" where vsfec.delete=0 " +
                "and vsfec.venue_id="+u.getVenueId()+" " +
                "and vsfec.event_id="+u.getEventId()+" and vsfec.id="+u.getVenueSectionId();
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
