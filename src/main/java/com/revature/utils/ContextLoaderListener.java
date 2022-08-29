package com.revature.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.servlets.AuthenticationServlet;
import com.revature.servlets.TestServlet;
import com.revature.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        //TODO Remove TestServlet when done with application
        servletContext.addServlet("TestServlet", new TestServlet()).addMapping("/test");
        servletContext.addServlet("UserServlet", new UserServlet()).addMapping("/signup");
        servletContext.addServlet("AuthenticationServlet", new AuthenticationServlet()).addMapping("/login");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Nathan-Chris-P1 web application.");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
