package com.revature.utils;

import com.revature.servlets.LoginServlet;
import com.revature.servlets.SignupServlet;
import com.revature.servlets.AdminServlet;
import com.revature.servlets.EmployeeServlet;
import com.revature.servlets.FinanceServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        servletContext.addServlet("SignupServlet", new SignupServlet()).addMapping("/signup");
        servletContext.addServlet("LoginServlet", new LoginServlet()).addMapping("/login");
        servletContext.addServlet("AdminServlet", new AdminServlet()).addMapping("/admin");
        servletContext.addServlet("ManagerServlet", new FinanceServlet()).addMapping("/finance");
        servletContext.addServlet("EmployeeServlet", new EmployeeServlet()).addMapping("/employee");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Nathan-Chris-P1 web application.");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
