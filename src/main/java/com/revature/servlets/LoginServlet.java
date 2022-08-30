package com.revature.servlets;
import com.revature.dtos.requests.LoginRequest;
import com.revature.models.User;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.utils.custom_exceptions.NetworkException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.revature.utils.ObjectMapperManager.getMapper;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User user = UserService.validateLogin(getMapper().readValue(req.getInputStream(), LoginRequest.class));
            resp.setStatus(200);
            resp.setHeader("Authorization", TokenService.generateToken(user.getUsername()));
            resp.setContentType("application/json");
            resp.getWriter().write(getMapper().writeValueAsString(user.getUsername()));
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}