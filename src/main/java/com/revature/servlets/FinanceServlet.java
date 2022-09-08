package com.revature.servlets;

import com.revature.dtos.requests.finance.UpdateReimbursementStatusRequest;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.services.ReimbursementService;
import com.revature.services.TokenService;
import com.revature.services.UserService;
import com.revature.utils.custom_exceptions.BadRequestException;
import com.revature.utils.custom_exceptions.NetworkException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import static com.revature.utils.ObjectMapperManager.getMapper;

public class FinanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = TokenService.extractRequesterDetails(req);
            UserService.verifyUserRole(loginUser, UserRole.FINANCE_MANAGER);
            List<Reimbursement> reimbursements;
            String view = req.getParameter("view");
            view = view != null ? view.toLowerCase() : "";
            String type = req.getParameter("type");
            type = type != null ? type.toUpperCase() : null;
            if ("pending".equals(view))
                reimbursements = ReimbursementService.getReimbursementsByTypeAndStatus(type, ReimbursementStatus.PENDING.name());
            else if ("resolved".equals(view)) reimbursements = ReimbursementService.getReimbursementsByManagerAndType(loginUser, type);
            else throw new BadRequestException("No view specified.");
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(getMapper().writeValueAsString(reimbursements));
        } catch (NetworkException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            User loginUser = TokenService.extractRequesterDetails(req);
            UserService.verifyUserRole(loginUser, UserRole.FINANCE_MANAGER);
            UpdateReimbursementStatusRequest request = getMapper().readValue(req.getInputStream(), UpdateReimbursementStatusRequest.class);
            ReimbursementService.updateReimbursement(loginUser, request);
            resp.setStatus(200);
            resp.setContentType("application/json");
        }catch (NetworkException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(e.getStatusCode());
        }
    }
}
