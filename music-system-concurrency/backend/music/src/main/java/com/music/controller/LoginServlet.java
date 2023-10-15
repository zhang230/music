package com.music.controller;
import com.alibaba.fastjson.JSON;
import com.music.dao.User;
import com.music.service.LoginService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet{
    private LoginService loginService;

    public LoginServlet() {
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String user_name = request.getParameter("user_name");
        String user_pwd = request.getParameter("user_pwd");
        User user = new User();
        user.setUserName(user_name);
        user.setUserPwd(user_pwd);
        User tuser = loginService.Login(user);

        if (tuser != null) {
            String s = JSON.toJSONString(tuser);

            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(s);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("Invalid login credentials.");
        }

    }
}
