package com.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.music.dao.*;
import com.music.service.AdminService;
import com.music.service.CustomerService;
import com.music.service.EventPlannerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/customerSearch","/customerBookEvent","/findCustomerBookingEvents",
"/customerCancelBookEvent"})
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;

    public CustomerServlet() {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if("/customerSearch".equals(request.getServletPath())) {
            try{
                String inputEventName = request.getParameter("inputEventName");
                String inputStartTime = request.getParameter("inputStartTime");
                String inputEndTime = request.getParameter("inputEndTime");
//                System.out.println(inputEventName+":"+inputStartTime+":"+inputEndTime);
                Event event = new Event();
                if(inputStartTime.equals("")) {
                    event.setStartTime(null);
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date parsedDate = dateFormat.parse(inputStartTime); // 解析日期时间字符串
                    event.setStartTime(new Timestamp(parsedDate.getTime()));
                }
                if(inputEndTime.equals("")) {
                    event.setEndTime(null);
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date parsedDate = dateFormat.parse(inputEndTime); // 解析日期时间字符串
                    event.setEndTime(new Timestamp(parsedDate.getTime()));
                }
//                if(inputEventName.equals("")) {
//                    event.setEventName(null);
//                } else {
                    event.setEventName(inputEventName);
//                }
//                System.out.println(event.toString());
                // ready to return
                List<Event> events = customerService.findAllEventsByEventNameAndTime(event);
                System.out.println(events.toString());
                for(int i = 0; i < events.size(); i++) {
                    Venue tv = customerService.findVenueByEventId(events.get(i));
                    List<VenueSection> venueSections = customerService.findVenueSectionsByVenueId(tv);
                    tv.setVenueSectionList(venueSections);
                    events.get(i).setVenue(tv);
                }
                String s = JSON.toJSONString(events);
//                System.out.println(s);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(s);

            }catch (Exception e){
                e.printStackTrace();
            }

        } else if ("/findCustomerBookingEvents".equals(request.getServletPath())) {
            Integer user_id = Integer.parseInt(request.getParameter("user_id"));

            List<UserBookingEvent> userBookingEvents=customerService.findAllUserBookingEventByUserId(user_id);

            List<Event> events = new ArrayList<Event>();
            for(int i = 0; i < userBookingEvents.size(); i++) {
//                System.out.println(userBookingEvents.get(i).toString());
                //find event infomation
                Event event = new Event();
                event = customerService.findEventByUserId(userBookingEvents.get(i));
//                System.out.println(event.toString());
                Venue venue = new Venue();
                venue = customerService.findVenueByUserId(userBookingEvents.get(i));
                VenueSection venueSection = new VenueSection();
                venueSection = customerService.findVenueSectionsByUserId(userBookingEvents.get(i));
                venue.setVenueSectionList(new ArrayList<VenueSection>());
                venue.getVenueSectionList().add(venueSection);
                event.setVenue(venue);
                event.setUserBookingEvent(userBookingEvents.get(i));
                events.add(event);
            }

            String s = JSON.toJSONString(events);
//                System.out.println(s);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);
        }
        else {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if("/customerBookEvent".equals(request.getServletPath())) {
            Integer user_id = Integer.parseInt(request.getParameter("user_id"));
            Integer event_id = Integer.parseInt(request.getParameter("event_id"));
            Integer venue_id = Integer.parseInt(request.getParameter("venue_id"));
            Integer venue_section_id = Integer.parseInt(request.getParameter("venue_section_id"));
            Integer number = Integer.parseInt(request.getParameter("number"));
            UserBookingEvent userBookingEvent = new UserBookingEvent();
            userBookingEvent.setUserId(user_id);
            userBookingEvent.setEventId(event_id);
            userBookingEvent.setVenueId(venue_id);
            userBookingEvent.setVenueSectionId(venue_section_id);
            userBookingEvent.setDelete(0);
            userBookingEvent.setBooking(1);
            userBookingEvent.setNumber(number);
            // if oversold
            Integer flag =  customerService.isOutOFNumberBooking(userBookingEvent);
           if(flag==0) {
               //if not oversold
               Integer id = customerService.insertUserBookingEvent(userBookingEvent);
               customerService.subOne(userBookingEvent);

               String s = JSON.toJSONString(userBookingEvent);
               response.setContentType("application/json;charset=utf-8");
               PrintWriter writer = response.getWriter();
               writer.write(s);
           } else {

           }


        } else if("/customerCancelBookEvent".equals(request.getServletPath())) {
            Integer user_id = Integer.parseInt(request.getParameter("user_id"));
            Integer event_id = Integer.parseInt(request.getParameter("event_id"));
            Integer venue_id = Integer.parseInt(request.getParameter("venue_id"));
            Integer venue_section_id = Integer.parseInt(request.getParameter("venue_section_id"));
            Integer user_booking_event_id = Integer.parseInt(request.getParameter("user_booking_event_id"));
            Integer capacity = Integer.parseInt(request.getParameter("capacity"));
//            System.out.println(capacity);

            UserBookingEvent userBookingEvent = new UserBookingEvent();
            userBookingEvent.setId(user_booking_event_id);
            userBookingEvent.setUserId(user_id);
            userBookingEvent.setEventId(event_id);
            userBookingEvent.setVenueId(venue_id);
            userBookingEvent.setVenueSectionId(venue_section_id);
            userBookingEvent.setDelete(0);
            userBookingEvent.setBooking(1);
            userBookingEvent.setNumber(capacity);
            Integer id = customerService.cancelUserBookingEvent(userBookingEvent);
            customerService.addOne(userBookingEvent);
            List<UserBookingEvent> userBookingEvents=customerService.findAllUserBookingEventByUserId(user_id);

            List<Event> events = new ArrayList<Event>();
            for(int i = 0; i < userBookingEvents.size(); i++) {
//                System.out.println(userBookingEvents.get(i).toString());
                //find event infomation
                Event event = new Event();
                event = customerService.findEventByUserId(userBookingEvents.get(i));
//                System.out.println(event.toString());
                Venue venue = new Venue();
                venue = customerService.findVenueByUserId(userBookingEvents.get(i));
                VenueSection venueSection = new VenueSection();
                venueSection = customerService.findVenueSectionsByUserId(userBookingEvents.get(i));
                venue.setVenueSectionList(new ArrayList<VenueSection>());
                venue.getVenueSectionList().add(venueSection);
                event.setVenue(venue);

                event.setUserBookingEvent(userBookingEvents.get(i));

                events.add(event);
            }

            String s = JSON.toJSONString(events);
//                System.out.println(s);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);


        }
        else {

        }

    }
}
