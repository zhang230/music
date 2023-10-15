package com.music.mapper;

import com.music.dao.*;
import com.music.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminMapper {

    private JDBCUtils jdbcUtils;

    public AdminMapper() {
        jdbcUtils = new JDBCUtils();
    }


    public List<User> adminFindAllUsers() {
        //sql inject
        String sql = "SELECT * FROM public.user where public.user.delete=0 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);

            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            List<User> users = new ArrayList<User>();
            while (resultSet.next()) {
                User tuser = new User();
                tuser.setId(resultSet.getInt("id"));
                tuser.setUserName(resultSet.getString("user_name"));
                tuser.setUserPwd(resultSet.getString("user_pwd"));
                tuser.setAge(resultSet.getInt("age"));
                tuser.setEmail(resultSet.getString("email"));
                tuser.setGender(resultSet.getString("gender"));
                tuser.setName(resultSet.getString("name"));
                tuser.setRole(resultSet.getInt("role"));
                tuser.setDelete(resultSet.getInt("delete"));
                users.add(tuser);
            }
            return users;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public List<Event> adminFindAllEvents() {
        //sql inject
        String sql = "SELECT * FROM public.event WHERE public.event.delete=0 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);

            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            List<Event> events = new ArrayList<Event>();
            while (resultSet.next()) {
                Event tevent = new Event();
                tevent.setId(resultSet.getInt("id"));
                tevent.setEventPlannerName(resultSet.getString("event_planner_name"));
                tevent.setEventName(resultSet.getString("event_name"));
                tevent.setStartTime(resultSet.getTimestamp("start_time"));
                tevent.setEndTime(resultSet.getTimestamp("end_time"));
                tevent.setDelete(resultSet.getInt("delete"));
                events.add(tevent);
            }
            return events;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public Integer insertVenue(Venue venue) {
        //sql inject
        String sql = "insert into public.venue(venue_name,venue_address,\"delete\") " +
                "values(?,?,?) returning id";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.setString(1, venue.getVenueName());
            statement.setString(2, venue.getVenueAddress());
            statement.setInt(3, venue.getDelete());
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

    public Boolean isRepeatVenueName(Venue venue) {
        //sql inject
        String sql = "SELECT * FROM public.venue where venue_name='"+venue.getVenueName()+"' and public.venue.delete=0 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if (resultSet.next()) {
                return true;
            } else {
               return false;
            }

        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void insertVenueSection(VenueSection section) {
        //sql inject
        String sql = "insert into public.venue_section(venue_id,section_name,price,capacity,\"delete\") " +
                "values(?,?,?,?,?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);
            statement.setInt(1, section.getVenueId());
            statement.setString(2, section.getSectionName());
            statement.setDouble(3, section.getPrice());
            statement.setInt(4,section.getCapacity());
            statement.setInt(5,section.getDelete());
            statement.executeUpdate();
            connection.commit();
            connection.close();
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public List<Venue> findAllVenue() {
        //sql inject
        String sql = "SELECT * FROM public.venue WHERE public.venue.delete=0 for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);

            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            List<Venue> venues = new ArrayList<Venue>();
            while (resultSet.next()) {
                Venue tvenune = new Venue();
                tvenune.setId(resultSet.getInt("id"));
                tvenune.setVenueName(resultSet.getString("venue_name"));
                tvenune.setVenueAddress(resultSet.getString("venue_address"));
                tvenune.setDelete(resultSet.getInt("delete"));
                venues.add(tvenune);
            }
            return venues;
        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }

    public void addAllVenueToAllVenueSections(List<Venue> venueList) {
        for (int i = 0; i < venueList.size(); i++) {
            //sql inject
            String sql = "SELECT * FROM public.venue_section WHERE public.venue_section.delete=0 and venue_id="+venueList.get(i).getId()+" for update";
            Connection connection = null;
            PreparedStatement statement = null;
            try{
                connection = jdbcUtils.getConnection();
                statement = connection.prepareStatement(sql);

                connection.setAutoCommit(false);

                ResultSet resultSet = statement.executeQuery();
                connection.commit();
            connection.close();
                List<VenueSection> venueSections = new ArrayList<VenueSection>();
                while (resultSet.next()) {
                    VenueSection tvenune = new VenueSection();
                    tvenune.setId(resultSet.getInt("id"));
                    tvenune.setSectionName(resultSet.getString("section_name"));
                    tvenune.setVenueId(resultSet.getInt("venue_id"));
                    tvenune.setPrice(resultSet.getDouble("price"));
                    tvenune.setCapacity(resultSet.getInt("capacity"));
                    tvenune.setDelete(resultSet.getInt("delete"));
                    venueSections.add(tvenune);
                }
                venueList.get(i).setVenueSectionList(venueSections);
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

    public void deleteVenueSectionsByVenueId(Venue venue) {
        //sql inject
        String sql = "update public.venue_section set \"delete\"=1 where venue_id="+venue.getId();
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

    public void updateVenueByVenueId(Venue venue) {
        //sql inject
        String sql = "update public.venue set venue_address='"+venue.getVenueAddress()+"' where id="+venue.getId();
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

    public void deleteVenuesByVenueId(Venue venue) {
        //sql inject
        String sql = "update public.venue set \"delete\"=1 where id="+venue.getId();
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

    public void deleteVenueSectionsByVenueIdAndVenueSectionId(VenueSection section) {
        //sql inject
        String sql = "update public.venue_section set \"delete\"=1 where venue_id="+section.getVenueId()+"and id="+section.getId();
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

    public List<UserBookingEvent> findAllUserBookingEvent() {
        String sql="select * from user_booking_event where user_booking_event.delete=0 and user_booking_event.booking=1 for update";
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
            ;
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

    public User findUserByUserId(UserBookingEvent userBookingEvent) {
        //sql inject
        String sql = "SELECT * FROM public.user where id="+userBookingEvent.getUserId()+" for update";
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = jdbcUtils.getConnection();
            statement = connection.prepareStatement(sql);

            connection.setAutoCommit(false);

            ResultSet resultSet = statement.executeQuery();
            connection.commit();
            connection.close();
            if (resultSet.next()) {
                User tuser = new User();
                tuser.setId(resultSet.getInt("id"));
                tuser.setUserName(resultSet.getString("user_name"));
                tuser.setUserPwd(resultSet.getString("user_pwd"));
                tuser.setAge(resultSet.getInt("age"));
                tuser.setEmail(resultSet.getString("email"));
                tuser.setGender(resultSet.getString("gender"));
                tuser.setName(resultSet.getString("name"));
                tuser.setRole(resultSet.getInt("role"));
                tuser.setDelete(resultSet.getInt("delete"));
                return tuser;
            } else {
                return null;
            }

        }catch (SQLException e) {
            ;
            throw new RuntimeException(e);
        }
    }
}
