package com.revature.servlets.employee;
import com.revature.dtos.requests.NewReimbursementRequest;
import com.revature.dtos.requests.UpdateReimbursementRequest;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.ReimbursementService;
import com.revature.services.TokenService;
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

public class ReimbursementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User loginUser = TokenService.extractRequesterDetails(req);
            if((loginUser.getRole() != UserRole.EMPLOYEE) || !loginUser.isActive())
                throw new ForbiddenException("Unauthorized User. Active Employees only.");
            ReimbursementService.addReimbursement(getMapper().readValue(req.getInputStream(), NewReimbursementRequest.class),
                    loginUser.getUserID());
            resp.setStatus(200);
            resp.setContentType("application/json");
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User loginUser = TokenService.extractRequesterDetails(req);
            if((loginUser.getRole() != UserRole.EMPLOYEE) || !loginUser.isActive())
                throw new ForbiddenException("Unauthorized User. Active Employees only.");
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(getMapper().writeValueAsString(ReimbursementService.getReimbursementsByAuthor(loginUser)));
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User loginUser = TokenService.extractRequesterDetails(req);
            if((loginUser.getRole() != UserRole.EMPLOYEE) || !loginUser.isActive())
                throw new ForbiddenException("Unauthorized User. Active Employees only.");
            //TODO check if author matches reimbursement
            ReimbursementService.updateReimbursement(getMapper().readValue(req.getInputStream(), UpdateReimbursementRequest.class),
                    loginUser.getUserID());
            resp.setStatus(200);
            resp.setContentType("application/json");
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    //TODO delete pending request
}