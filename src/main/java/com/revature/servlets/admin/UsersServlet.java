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

import static com.revature.utils.ObjectMapperManager.getMapper;

public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = TokenService.extractRequesterDetails(req);
            System.out.println(user);
            if((user.getRole() != UserRole.ADMIN) || !user.isActive()){
                throw new ForbiddenException("Unauthorized User. Admins only.");
            }
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(getMapper().writeValueAsString(UserService.getAllUsers()));
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}
