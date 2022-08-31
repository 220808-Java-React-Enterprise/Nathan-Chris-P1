package com.revature.servlets.admin;

import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.utils.custom_exceptions.BadRequestException;
import com.revature.utils.custom_exceptions.ForbiddenException;
import com.revature.utils.custom_exceptions.NetworkException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.revature.utils.ObjectMapperManager.getMapper;

public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = TokenService.extractRequesterDetails(req);
            if((loginUser.getRole() != UserRole.ADMIN) || !loginUser.isActive())
                throw new ForbiddenException("Unauthorized User. Active Admins only.");
            resp.setStatus(200);
            resp.setContentType("application/json");
            List<User> users = new ArrayList<>();
            if(req.getParameter("user") != null) {
                User user = UserService.getUserByUsername(req.getParameter("user"));
                if (user != null) {
                    if (req.getParameter("active") != null) {
                        if (Boolean.parseBoolean(req.getParameter("active")) == user.isActive()) {
                            users.add(user);
                        }
                    } else {
                        users.add(user);
                    }
                }
            } else if (req.getParameter("active") != null) {
                if(Boolean.parseBoolean(req.getParameter("active"))) {
                    users.addAll(UserService.getAllActiveUsers());
                }else{
                    users.addAll(UserService.getAllInactiveUsers());
                }
            } else {
                users.addAll(UserService.getAllUsers());
            }
            resp.getWriter().write(getMapper().writeValueAsString(users));
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO possibly change to json in body rather than parameters
        try {
            User loginUser = TokenService.extractRequesterDetails(req);
            if((loginUser.getRole() != UserRole.ADMIN) || !loginUser.isActive())
                throw new ForbiddenException("Unauthorized User. Active Admins only.");
            if((req.getParameter("action") == null)
                    || (!(req.getParameter("action").equalsIgnoreCase("password"))
                        && !(req.getParameter("action").equalsIgnoreCase("activate"))
                        && !(req.getParameter("action").equalsIgnoreCase("deactivate"))))
                throw new BadRequestException("Invalid or missing action for user modification.");
            if(req.getParameter("user") == null || req.getParameter("user").equals(""))
                throw new BadRequestException("User param is required for user modification.");
            User user = UserService.getUserByUsername(req.getParameter("user"));
            if(user == null)
                throw new BadRequestException("No user with username " + req.getParameter("user") + " found. Cannot modify user.");
            switch(req.getParameter("action").toLowerCase()){
                case "password":
                    if(req.getParameter("password") == null || req.getParameter("password").equals(""))
                        throw new BadRequestException("Password param is required for password reset.");
                    UserService.changePassword(user, req.getParameter("password"));
                    break;
                case "activate":
                    UserService.activateUser(user);
                    break;
                case"deactivate":
                    UserService.deactivateUser(user);
                    break;
            }
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO possibly change to json in body rather than parameters
        try {
            User loginUser = TokenService.extractRequesterDetails(req);
            if((loginUser.getRole() != UserRole.ADMIN) || !loginUser.isActive())
                throw new ForbiddenException("Unauthorized User. Active Admins only.");
            if(req.getParameter("user") == null || req.getParameter("user").equals(""))
                throw new BadRequestException("User param is required for user deletion.");
            User user = UserService.getUserByUsername(req.getParameter("user"));
            if (user == null)
                throw new BadRequestException("No user with username " + req.getParameter("user") + " found. Cannot delete user.");
            UserService.deleteUser(user);
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}
