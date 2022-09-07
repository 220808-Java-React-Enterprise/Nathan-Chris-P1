package com.revature.utils;

import com.revature.servlets.LoginServlet;
import com.revature.servlets.SignupServlet;
import com.revature.servlets.TestServlet;
import com.revature.servlets.admin.UsersServlet;
import com.revature.servlets.employee.ReimbursementServlet;
import com.revature.servlets.finance.PendingServlet;
import com.revature.servlets.finance.ResolvedServlet;
import com.revature.servlets.finance.UpdateServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        //TODO Remove TestServlet when done with application
        servletContext.addServlet("TestServlet", new TestServlet()).addMapping("/test");
        servletContext.addServlet("SignupServlet", new SignupServlet()).addMapping("/signup");
        servletContext.addServlet("LoginServlet", new LoginServlet()).addMapping("/login");
        servletContext.addServlet("UsersServlet", new UsersServlet()).addMapping("/admin");
        servletContext.addServlet("ManagerPendingServlet", new PendingServlet()).addMapping("/finance/viewpending");
        servletContext.addServlet("ManagerResolvedServlet", new ResolvedServlet()).addMapping("/finance/viewresolved");
        servletContext.addServlet("ManagerUpdateServlet", new UpdateServlet()).addMapping("/finance/update");
        servletContext.addServlet("EmployeeReimbursementServlet", new ReimbursementServlet()).addMapping("/employee");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Shutting down Nathan-Chris-P1 web application.");
        ServletContextListener.super.contextDestroyed(sce);
    }
}
