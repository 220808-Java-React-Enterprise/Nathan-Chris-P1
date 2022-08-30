package com.revature.servlets;

import com.revature.dtos.requests.NewUserRequest;
import com.revature.services.UserService;
import com.revature.utils.custom_exceptions.NetworkException;

import static com.revature.utils.ObjectMapperManager.getMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            UserService.registerUser(getMapper().readValue(req.getInputStream(), NewUserRequest.class));
            resp.setStatus(200);
            resp.setContentType("application/json");
        }catch (NetworkException e){
            System.out.println(e);
            resp.setStatus(e.getStatusCode());
        }
    }
}
