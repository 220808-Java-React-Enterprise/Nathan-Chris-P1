package com.revature.servlets;

import com.revature.dtos.requests.employee.DeleteReimbursementRequest;
import com.revature.dtos.requests.employee.NewReimbursementRequest;
import com.revature.dtos.requests.employee.UpdateReimbursementRequest;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.ReimbursementService;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.utils.custom_exceptions.NetworkException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import static com.revature.utils.ObjectMapperManager.getMapper;

public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User loginUser = TokenService.extractRequesterDetails(req);
            UserService.verifyUserRole(loginUser, UserRole.EMPLOYEE);
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
            UserService.verifyUserRole(loginUser, UserRole.EMPLOYEE);
            resp.setStatus(200);
            resp.setContentType("application/json");
            List<Reimbursement> reimbursements = ReimbursementService.getReimbursementsByAuthor(loginUser);
            if(req.getParameter("sort") != null) {
                switch (req.getParameter("sort")){
                    case "submitted":
                        reimbursements.sort(Comparator.comparing(Reimbursement::getSubmitted));
                        break;
                    case "resolved":
                        reimbursements.sort(Comparator.comparing(Reimbursement::getResolved));
                        break;
                    case "amount":
                        reimbursements.sort(Comparator.comparing(Reimbursement::getAmount));
                        break;
                }
            }
            resp.getWriter().write(getMapper().writeValueAsString(reimbursements));
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
            UserService.verifyUserRole(loginUser, UserRole.EMPLOYEE);
            UpdateReimbursementRequest request = getMapper().readValue(req.getInputStream(), UpdateReimbursementRequest.class);
            ReimbursementService.verifyCanModify(request.getReimb_id(), loginUser.getUserID());
            ReimbursementService.updateReimbursement(request, loginUser.getUserID());
            resp.setStatus(200);
            resp.setContentType("application/json");
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User loginUser = TokenService.extractRequesterDetails(req);
            UserService.verifyUserRole(loginUser, UserRole.EMPLOYEE);
            DeleteReimbursementRequest request = getMapper().readValue(req.getInputStream(), DeleteReimbursementRequest.class);
            ReimbursementService.verifyCanModify(request.getReimb_id(), loginUser.getUserID());
            ReimbursementService.deleteReimbursement(request);
            resp.setStatus(200);
            resp.setContentType("application/json");
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}