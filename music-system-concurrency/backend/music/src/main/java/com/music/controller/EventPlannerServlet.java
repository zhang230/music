package com.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.music.dao.*;
import com.music.service.AdminService;
import com.music.service.EventPlannerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "EventPlannerServlet", urlPatterns = {"/eventPlannerAddEvent","/eventPlannerFindAllEventsAndVenueInfo",
"/eventPlannerUpdateDateAndPrice","/eventPlannerCancelEvent", "/findEventPlannerCreateBookingEvents",
"/eventPlannerCancelOneBooking", "/eventPlannerSearchByUserName"})
public class EventPlannerServlet extends HttpServlet {
    private EventPlannerService eventPlannerService;

    public EventPlannerServlet() {
        eventPlannerService = new EventPlannerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if ("/eventPlannerFindAllEventsAndVenueInfo".equals(request.getServletPath())) {
            // ready to return
            List<Event> events = eventPlannerService.findAllEvents();
            for(int i = 0; i < events.size(); i++) {
                Venue tv = eventPlannerService.findVenueByEventId(events.get(i));
//                System.out.println(tv.toString());
                List<VenueSection> venueSections = eventPlannerService.findVenueSectionsByVenueId(tv);
                tv.setVenueSectionList(venueSections);
                events.get(i).setVenue(tv);
            }
            String s = JSON.toJSONString(events);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } else if("/findEventPlannerCreateBookingEvents".equals(request.getServletPath())) {
            Integer user_id = Integer.parseInt(request.getParameter("user_id"));

            List<BookEventPlanner> bookEventPlanners = eventPlannerService.findBooksByCreateUserId(user_id);
//            System.out.println(bookEventPlanners.toString());
            String s = JSON.toJSONString(bookEventPlanners);
//                System.out.println(s);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } else if("/eventPlannerSearchByUserName".equals(request.getServletPath())) {
            String inputUserName = request.getParameter("inputUserName");
            List<BookEventPlanner> bookEventPlanners = eventPlannerService.findBooksByCreateUserName(inputUserName);
//            System.out.println(bookEventPlanners.get(0).getEvent().toString());
            String s = JSON.toJSONString(bookEventPlanners);
//                System.out.println(s);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);

        }
        else {
//            ....other uri
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if ("/eventPlannerAddEvent".equals(request.getServletPath())) {
            try {
                String event_planner_name = request.getParameter("event_planner_name");
                String event_name = request.getParameter("event_name");
                String start_time = request.getParameter("start_time");
                String end_time = request.getParameter("end_time");
                Integer user_id = Integer.parseInt(request.getParameter("user_id"));
                Event event = new Event();
                event.setEventPlannerName(event_planner_name);
                event.setEventName(event_name);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(start_time); // parse
                event.setStartTime(new Timestamp(parsedDate.getTime()));
                parsedDate = dateFormat.parse(end_time); // parse
                event.setEndTime(new Timestamp(parsedDate.getTime()));
                event.setUserId(user_id);
                event.setDelete(0);
                //event name can not be repeat
                Boolean isRepeat = eventPlannerService.isRepeatEventName(event);
                if(isRepeat) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    response.getWriter().print("event name repeat.");
                } else {
                    String venueAndSectionList = request.getParameter("venueAndSectionList");
                    JSONObject jsonObject = JSON.parseObject(venueAndSectionList);
                    Integer venueId = jsonObject.getInteger("id");
                    //Find out if there is a corresponding venue with a schedule conflict ！！！！
                    Boolean isConflict = eventPlannerService.isConflictTime(venueId, event);
                    if(isConflict) {
                        response.setStatus(HttpServletResponse.SC_CONFLICT);
                        response.getWriter().print("Create Event Fail,Time Conflict, try again.");
                    } else {
                        //insert event and get id
                        Integer event_id = eventPlannerService.insertEvent(event);
                        //event_id and venue_id insert event_venue table
                        EventVenue eventVenue = new EventVenue();
                        eventVenue.setEventId(event_id);
                        eventVenue.setVenueId(venueId);
                        eventVenue.setDelete(0);
                        eventPlannerService.insertEventVenue(eventVenue);
                        //update all venue_section's price by venue_section id and venue_id
                        JSONArray venueSectionList = jsonObject.getJSONArray("venueSectionList");
                        for (int i = 0; i < venueSectionList.size(); i++) {
                            JSONObject t = venueSectionList.getJSONObject(i);
                            String sectionName = t.getString("sectionName");
                            Double price = Double.parseDouble(t.getString("price"));
                            Integer venueId1 = t.getInteger("venueId");
                            Integer id = t.getInteger("id");
                            Integer delete = t.getInteger("delete");
                            Integer capacity1 = t.getInteger("capacity");
                            // 创建 Section 对象并根据id更新venue_section表的price值
                            VenueSectionForEventplannerCreate section = new VenueSectionForEventplannerCreate();
                            section.setId(id);
                            section.setVenueId(venueId1);
                            section.setSectionName(sectionName);
                            section.setPrice(price);
                            section.setCapacity(capacity1);
                            section.setDelete(delete);
                            section.setEventId(event_id);
                            eventPlannerService.insertVenueSectionForEventplannerCreate(section);
                        }
                        // ready to return
                        List<Event> events = eventPlannerService.findAllEvents();
                        for(int i = 0; i < events.size(); i++) {
                            Venue tv = eventPlannerService.findVenueByEventId(events.get(i));
                            List<VenueSection> venueSections = eventPlannerService.findVenueSectionsByVenueId(tv);
                            tv.setVenueSectionList(venueSections);
                            events.get(i).setVenue(tv);
                        }
                        String s = JSON.toJSONString(events);
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(s);

                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else if("/eventPlannerUpdateDateAndPrice".equals(request.getServletPath())) {
            try{


                Integer eventId = Integer.parseInt(request.getParameter("eventId"));
                String eventName = request.getParameter("eventName");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                String user = request.getParameter("user");
                JSONObject jsonObject1 = JSON.parseObject(user);
//                System.out.println(jsonObject1.toJSONString());
                Event event = new Event();
                event.setId(eventId);
                event.setEventName(eventName);
                event.setLastModifiedName(jsonObject1.getString("userName"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parsedDate = dateFormat.parse(startTime); // parse date
                event.setStartTime(new Timestamp(parsedDate.getTime()));
                parsedDate = dateFormat.parse(endTime); // parse date
                event.setEndTime(new Timestamp(parsedDate.getTime()));
                eventPlannerService.updateEvent(event);//update
                String venueList = request.getParameter("venue");
//                System.out.println(eventId+":"+eventName+":"+startTime+":"+endTime);
//                System.out.println(user);
//                System.out.println(venueList);




//                System.out.println(venueList);
                JSONArray jsonArray = JSON.parseArray(venueList);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String venueSectionList = jsonObject.getString("venueSectionList");
                    JSONArray jsonArray2 = JSON.parseArray(venueSectionList);
                    for(int j = 0; j < jsonArray2.size(); j++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(j);
                        VenueSectionForEventplannerCreate venueSectionForEventplannerCreate = new VenueSectionForEventplannerCreate();

                        venueSectionForEventplannerCreate.setEventId(eventId);
                        venueSectionForEventplannerCreate.setSectionName(jsonObject2.getString("sectionName"));
                        venueSectionForEventplannerCreate.setPrice(jsonObject2.getDouble("price"));
                        venueSectionForEventplannerCreate.setVenueId(jsonObject2.getInteger("venueId"));
                        venueSectionForEventplannerCreate.setDelete(jsonObject2.getInteger("delete"));
                        venueSectionForEventplannerCreate.setCapacity(jsonObject2.getInteger("capacity"));
                        venueSectionForEventplannerCreate.setId(jsonObject2.getInteger("id"));
//                        System.out.println(venueSectionForEventplannerCreate.toString());
                        eventPlannerService.updateVenueSectionFor(venueSectionForEventplannerCreate);
                    }
                }
                // ready to return
                List<Event> events = eventPlannerService.findAllEvents();
                for(int i = 0; i < events.size(); i++) {
                    Venue tv = eventPlannerService.findVenueByEventId(events.get(i));
                    List<VenueSection> venueSections = eventPlannerService.findVenueSectionsByVenueId(tv);
                    tv.setVenueSectionList(venueSections);
                    events.get(i).setVenue(tv);
                }
                String s = JSON.toJSONString(events);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(s);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if("/eventPlannerCancelEvent".equals(request.getServletPath())){
            try{
                Integer eventId = Integer.parseInt(request.getParameter("eventId"));
                Event event = new Event();
                event.setId(eventId);
                eventPlannerService.deleteEventByEventId(event);
                eventPlannerService.deleteEventVenueByEventId(event);
                eventPlannerService.deleteVSFEC(event);

                // ready to return
                List<Event> events = eventPlannerService.findAllEvents();
                for(int i = 0; i < events.size(); i++) {
                    Venue tv = eventPlannerService.findVenueByEventId(events.get(i));
                    List<VenueSection> venueSections = eventPlannerService.findVenueSectionsByVenueId(tv);
                    tv.setVenueSectionList(venueSections);
                    events.get(i).setVenue(tv);
                }
                String s = JSON.toJSONString(events);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(s);


            }catch (Exception e){
                e.printStackTrace();
            }
        } else if("/eventPlannerCancelOneBooking".equals(request.getServletPath())) {
            String userBookingEvent = request.getParameter("userBookingEvent");
            Integer user_id = Integer.parseInt(request.getParameter("user_id"));

//            System.out.println(userBookingEvent);
//            System.out.println(user_id);
            JSONObject jsonObject = JSON.parseObject(userBookingEvent);
            UserBookingEvent userBookingEvent1 = new UserBookingEvent();
            userBookingEvent1.setId(jsonObject.getInteger("id"));
            userBookingEvent1.setUserId(jsonObject.getInteger("userId"));
            userBookingEvent1.setEventId(jsonObject.getInteger("eventId"));
            userBookingEvent1.setBooking(jsonObject.getInteger("booking"));
            userBookingEvent1.setDelete(jsonObject.getInteger("delete"));
            userBookingEvent1.setVenueSectionId(jsonObject.getInteger("venueSectionId"));
            userBookingEvent1.setVenueId(jsonObject.getInteger("venueId"));
            userBookingEvent1.setNumber(jsonObject.getInteger("number"));
            eventPlannerService.addOne(userBookingEvent1);
            //if any user book, can not be cancel
//            Boolean isBooking = eventPlannerService.IfAnyUserBook(userBookingEvent1);
//            if(isBooking) {
//                response.setContentType("application/json;charset=utf-8");
//                PrintWriter writer = response.getWriter();
//                writer.write("Have customer booking, can not cancel!");
//            } else {
                eventPlannerService.cancelBooking(userBookingEvent1);
                List<BookEventPlanner> bookEventPlanners = eventPlannerService.findBooksByCreateUserId(user_id);
//            System.out.println(bookEventPlanners.toString());
                String s = JSON.toJSONString(bookEventPlanners);
//                System.out.println(s);
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(s);
//            }

        }
        else {
//            ....other uri
        }

    }
}
