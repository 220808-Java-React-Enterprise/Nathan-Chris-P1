package com.revature.servlets.admin;

import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.TokenService;
import com.revature.services.UserService;
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
            User user = TokenService.extractRequesterDetails(req);
            if((user.getRole() != UserRole.ADMIN) || !user.isActive()){
                throw new ForbiddenException("Unauthorized User. Active Admins only.");
            }
            resp.setStatus(200);
            resp.setContentType("application/json");
            List<User> queryUsers = new ArrayList<>();
            if(req.getParameter("user") != null) {
                User queryUser = UserService.getUserByUsername(req.getParameter("user"));
                if (queryUser != null) {
                    if (req.getParameter("active") != null) {
                        if (Boolean.parseBoolean(req.getParameter("active")) == queryUser.isActive()) {
                            queryUsers.add(queryUser);
                        }
                    } else {
                        queryUsers.add(queryUser);
                    }
                }
            } else if (req.getParameter("active") != null) {
                if(Boolean.parseBoolean(req.getParameter("active"))) {
                    queryUsers.addAll(UserService.getAllActiveUsers());
                }else{
                    queryUsers.addAll(UserService.getAllInactiveUsers());
                }
            } else {
                queryUsers.addAll(UserService.getAllUsers());
            }
            resp.getWriter().write(getMapper().writeValueAsString(queryUsers));
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}
