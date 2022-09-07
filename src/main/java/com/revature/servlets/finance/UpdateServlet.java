package com.revature.servlets.finance;

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

public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loginUser = TokenService.extractRequesterDetails(req);
        if((loginUser.getRole() != UserRole.FINANCE_MANAGER) || !loginUser.isActive()){
            throw new ForbiddenException("Unauthorized User. Active Finance Managers only.");
        }
        resp.setStatus(200);
        resp.setContentType("application/json");
        try {
            String id = req.getParameter("id");
            String status = req.getParameter("status");
            ReimbursementService.updateReimbursement(id, loginUser, status);
        } catch (NetworkException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}
