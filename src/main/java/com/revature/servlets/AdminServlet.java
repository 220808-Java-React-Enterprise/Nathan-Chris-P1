package com.revature.servlets;

import com.revature.dtos.requests.admin.ActivateUserRequest;
import com.revature.dtos.requests.admin.DeactivateUserRequest;
import com.revature.dtos.requests.admin.DeleteUserRequest;
import com.revature.dtos.requests.admin.UpdateUserPasswordRequest;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.utils.custom_exceptions.BadRequestException;
import com.revature.utils.custom_exceptions.NetworkException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.revature.utils.ObjectMapperManager.getMapper;

public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = TokenService.extractRequesterDetails(req);
            UserService.verifyUserRole(loginUser, UserRole.ADMIN);
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
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(getMapper().writeValueAsString(users));
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = TokenService.extractRequesterDetails(req);
            UserService.verifyUserRole(loginUser, UserRole.ADMIN);
            if (req.getParameter("action") == null)
                throw new BadRequestException("Missing action for user modification.");
            switch (req.getParameter("action").toLowerCase()) {
                case "password": {
                    UpdateUserPasswordRequest request = getMapper().readValue(req.getInputStream(), UpdateUserPasswordRequest.class);
                    UserService.changePassword(request.getUsername(), request.getPassword());
                    break;
                }
                case "activate": {
                    ActivateUserRequest request = getMapper().readValue(req.getInputStream(), ActivateUserRequest.class);
                    UserService.activateUser(request.getUsername());
                    break;
                }
                case "deactivate": {
                    DeactivateUserRequest request = getMapper().readValue(req.getInputStream(), DeactivateUserRequest.class);
                    UserService.deactivateUser(request.getUsername());
                    break;
                }
                default: throw new BadRequestException("Invalid action for user modification.");
            }
            resp.setStatus(200);
            resp.setContentType("application/json");
        } catch (NetworkException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = TokenService.extractRequesterDetails(req);
            UserService.verifyUserRole(loginUser, UserRole.ADMIN);
            DeleteUserRequest request = getMapper().readValue(req.getInputStream(), DeleteUserRequest.class);
            UserService.deleteUser(request);
            resp.setStatus(200);
            resp.setContentType("application/json");
        } catch (NetworkException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}
