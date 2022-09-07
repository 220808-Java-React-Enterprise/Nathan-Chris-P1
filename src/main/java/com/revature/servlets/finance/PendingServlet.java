package com.revature.servlets.finance;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.ReimbursementService;
import com.revature.services.TokenService;
import com.revature.utils.custom_exceptions.ForbiddenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.revature.utils.ObjectMapperManager.getMapper;

public class PendingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loginUser = TokenService.extractRequesterDetails(req);
        if((loginUser.getRole() != UserRole.FINANCE_MANAGER) || !loginUser.isActive()){
            throw new ForbiddenException("Unauthorized User. Active Finance Managers only.");
        }
        resp.setStatus(200);
        resp.setContentType("application/json");
        List<Reimbursement> reimbursements = new ArrayList<>();
        String type = req.getParameter("type");
            if (type != null) {
                reimbursements.addAll(ReimbursementService.getReimbursementsByTypeAndStatus(type, ReimbursementStatus.PENDING.name()));
            }
            else reimbursements.addAll(ReimbursementService.getReimbursementsByStatus(ReimbursementStatus.PENDING.name()));
        resp.getWriter().write(getMapper().writeValueAsString(reimbursements));
    }
}
