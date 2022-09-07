package com.revature.servlets.finance;

import com.revature.models.Reimbursement;
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

public class ResolvedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loginUser = TokenService.extractRequesterDetails(req);
        if((loginUser.getRole() != UserRole.FINANCE_MANAGER) || !loginUser.isActive()){
            throw new ForbiddenException("Unauthorized User. Active Finance Managers only.");
        }
        resp.setStatus(200);
        resp.setContentType("application/json");
        String type = req.getParameter("type");
        List<Reimbursement> reimbursements = ReimbursementService.getReimbursementsByManagerAndType(loginUser, type);
        resp.getWriter().write(getMapper().writeValueAsString(reimbursements));
    }
}
