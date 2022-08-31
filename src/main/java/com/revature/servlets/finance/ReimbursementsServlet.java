package com.revature.servlets.finance;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.ReimbursementService;
import com.revature.services.TokenService;
import com.revature.utils.custom_exceptions.BadRequestException;
import com.revature.utils.custom_exceptions.ForbiddenException;
import com.revature.utils.custom_exceptions.NetworkException;
import com.revature.utils.custom_exceptions.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.revature.utils.ObjectMapperManager.getMapper;

public class ReimbursementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loginUser = TokenService.extractRequesterDetails(req);
        if((loginUser.getRole() != UserRole.FINANCE_MANAGER) || !loginUser.isActive()){
            throw new ForbiddenException("Unauthorized User. Active Finance Managers only.");
        }
        resp.setStatus(200);
        resp.setContentType("application/json");
        List<Reimbursement> reimbursements = new ArrayList<>();
        String status = req.getParameter("status");
        String type = req.getParameter("type");
        if (status != null) {
            if (type != null) {
                reimbursements.addAll(ReimbursementService.getReimbursementsByTypeAndStatus(type, status));
            }
            else reimbursements.addAll(ReimbursementService.getReimbursementsByStatus(status));
        }
        else if (type != null) {
            reimbursements.addAll(ReimbursementService.getReimbursementsByType(type));
        }
        else reimbursements.addAll(ReimbursementService.getAllReimbursements());
        resp.getWriter().write(getMapper().writeValueAsString(reimbursements));
    }

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
