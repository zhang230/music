package com.music.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.music.dao.*;
import com.music.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//@WebServlet(name = "AdminServlet", urlPatterns = {"/login", "/logout", "/profile"})
@WebServlet(name = "AdminServlet", urlPatterns = {"/adminFindAllUsers", "/adminFindAllEvents",
        "/adminVenueSectionCreate", "/adminFindAllVenueAndVenueSection", "/adminVenueSectionEdit",
"/adminDeleteVenueAndVenueSections", "/getVenueAndVenueSection","/findAllCustomerBookingEvents"})
public class AdminServlet extends HttpServlet {
    private AdminService adminService;

    public AdminServlet() {
        adminService = new AdminService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        if ("/adminFindAllUsers".equals(request.getServletPath())) {
            List<User> users = new ArrayList<User>();
            users = adminService.adminFindAllUsers();
            if (users != null) {
                String s = JSON.toJSONString(users);

                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(s);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print("Invalid login credentials.");
            }
        } else if("/adminFindAllEvents".equals(request.getServletPath())) {
            List<Event> events = new ArrayList<Event>();
            events = adminService.adminFindAllEvents();
            if (events != null) {
                String s = JSON.toJSONString(events);

                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(s);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print("Invalid login credentials.");
            }
        } else if("/adminFindAllVenueAndVenueSection".equals(request.getServletPath())) {
            List<Venue> venueList = adminService.findAllVenue();
            adminService.addAllVenueToAllVenueSections(venueList);

            String s = JSON.toJSONString(venueList);

            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } else if("/getVenueAndVenueSection".equals(request.getServletPath())) {
            List<Venue> venueList = adminService.findAllVenue();
            adminService.addAllVenueToAllVenueSections(venueList);

            String s = JSON.toJSONString(venueList);

            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } else if("/findAllCustomerBookingEvents".equals(request.getServletPath())) {
//            Integer user_id = Integer.parseInt(request.getParameter("user_id"));
            List<UserBookingEvent> userBookingEvents=adminService.findAllUserBookingEvent();

            List<Event> events = new ArrayList<Event>();
            for(int i = 0; i < userBookingEvents.size(); i++) {
                //find user
                User ttuser = adminService.findUserByUserId(userBookingEvents.get(i));
//                System.out.println(userBookingEvents.get(i).toString());
                //find event infomation
                Event event = new Event();
                event = adminService.findEventByUserId(userBookingEvents.get(i));
//                System.out.println(event.toString());
                Venue venue = new Venue();
                venue = adminService.findVenueByUserId(userBookingEvents.get(i));
                VenueSection venueSection = new VenueSection();
                venueSection = adminService.findVenueSectionsByUserId(userBookingEvents.get(i));
                venue.setVenueSectionList(new ArrayList<VenueSection>());
                venue.getVenueSectionList().add(venueSection);
                event.setVenue(venue);
                event.setUser(ttuser);
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
//            ....other uri
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
       if ("/adminVenueSectionCreate".equals(request.getServletPath())) {
           String venueName = request.getParameter("VenueName");
           String venueAddress = request.getParameter("VenueAddress");
           Venue venue = new Venue();
           venue.setVenueName(venueName);
           venue.setVenueAddress(venueAddress);
           venue.setDelete(0);
           Boolean isRepeat = adminService.isRepeatVenueName(venue);
           if(isRepeat == false) {
               Integer venueId = adminService.insertVenue(venue);

               String sectionList = request.getParameter("SectionList");
               JSONArray jsonArray = JSON.parseArray(sectionList);

               for (int i = 0; i < jsonArray.size(); i++) {
                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                   String sectionname = jsonObject.getString("sectionname");
                   Integer capacity = Integer.parseInt(jsonObject.getString("capacity"));

                   // 创建 Section 对象并添加到列表
                   VenueSection section = new VenueSection();
                   section.setSectionName(sectionname);
                   section.setCapacity(capacity);
                   section.setVenueId(venueId);
                   section.setPrice(0.0);
                   section.setDelete(0);
                   adminService.insertVenueSection(section);
               }
               List<Venue> venueList = adminService.findAllVenue();
               adminService.addAllVenueToAllVenueSections(venueList);

               String s = JSON.toJSONString(venueList);

               response.setContentType("application/json;charset=utf-8");
               PrintWriter writer = response.getWriter();
               writer.write(s);

           } else {
               response.setStatus(HttpServletResponse.SC_CONFLICT);
               response.getWriter().print("Venue Name repeat, try again.");
           }

       }
        else if ("/adminVenueSectionEdit".equals(request.getServletPath())){
           Integer venueId = Integer.parseInt(request.getParameter(("VenueId")));
            String venueName = request.getParameter("VenueName");
           String venueAddress = request.getParameter("VenueAddress");
           Venue venue = new Venue();
           venue.setId(venueId);
           venue.setVenueName(venueName);
           venue.setVenueAddress(venueAddress);
           venue.setDelete(0);
           adminService.updateVenueByVenueId(venue);
           adminService.deleteVenueSectionsByVenueId(venue);

               String sectionList = request.getParameter("SectionList");
               JSONArray jsonArray = JSON.parseArray(sectionList);
               for (int i = 0; i < jsonArray.size(); i++) {
                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                   String sectionname = jsonObject.getString("sectionname");
                   Integer capacity = Integer.parseInt(jsonObject.getString("capacity"));

                   // Create a section object and add it to the list
                   VenueSection section = new VenueSection();
                   section.setSectionName(sectionname);
                   section.setCapacity(capacity);
                   section.setVenueId(venue.getId());
                   section.setPrice(0.0);
                   section.setDelete(0);
                   adminService.insertVenueSection(section);
               }
               List<Venue> venueList = adminService.findAllVenue();
               adminService.addAllVenueToAllVenueSections(venueList);

               String s = JSON.toJSONString(venueList);

               response.setContentType("application/json;charset=utf-8");
               PrintWriter writer = response.getWriter();
               writer.write(s);

       }
        else if ("/adminDeleteVenueAndVenueSections".equals(request.getServletPath())) {
           Integer venueId = Integer.parseInt(request.getParameter(("VenueId")));
           String venueName = request.getParameter("VenueName");
           String venueAddress = request.getParameter("VenueAddress");
           Venue venue = new Venue();
           venue.setId(venueId);
           venue.setVenueName(venueName);
           venue.setVenueAddress(venueAddress);
           venue.setDelete(0);
           adminService.deleteVenuesByVenueId(venue);

           String sectionList = request.getParameter("SectionList");
           JSONArray jsonArray = JSON.parseArray(sectionList);
           for (int i = 0; i < jsonArray.size(); i++) {
               JSONObject jsonObject = jsonArray.getJSONObject(i);
               String sectionname = jsonObject.getString("sectionname");
               Integer capacity = Integer.parseInt(jsonObject.getString("capacity"));
               Integer id =jsonObject.getInteger("id");
               // Create a Section object and add it to the list
               VenueSection section = new VenueSection();
               section.setSectionName(sectionname);
               section.setCapacity(capacity);
               section.setVenueId(venue.getId());
               section.setPrice(0.0);
               section.setDelete(0);
               section.setId(id);
               adminService.deleteVenueSectionsByVenueIdAndVenueSectionId(section);
           }
           List<Venue> venueList = adminService.findAllVenue();
           adminService.addAllVenueToAllVenueSections(venueList);

           String s = JSON.toJSONString(venueList);

           response.setContentType("application/json;charset=utf-8");
           PrintWriter writer = response.getWriter();
           writer.write(s);
       }
        else {
//            ....other uri
        }

    }
}
