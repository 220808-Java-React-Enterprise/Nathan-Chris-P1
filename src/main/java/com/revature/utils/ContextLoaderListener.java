package com.revature.utils;

import com.revature.servlets.LoginServlet;
import com.revature.servlets.SignupServlet;
import com.revature.servlets.admin.UsersServlet;
import com.revature.servlets.finance.ReimbursementServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        servletContext.addServlet("SignupServlet", new SignupServlet()).addMapping("/signup");
        servletContext.addServlet("LoginServlet", new LoginServlet()).addMapping("/login");
        servletContext.addServlet("AdminServlet", new UsersServlet()).addMapping("/admin");
        servletContext.addServlet("ManagerServlet", new ReimbursementServlet()).addMapping("/finance");
        servletContext.addServlet("EmployeeServlet", new com.revature.servlets.employee.ReimbursementServlet()).addMapping("/employee");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Nathan-Chris-P1 web application.");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
