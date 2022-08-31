package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.dtos.requests.NewReimbursementRequest;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;

import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {
    private static ReimbursementDAO reimbDAO = new ReimbursementDAO();
    
    public ReimbursementService(ReimbursementDAO reimbDAO) { ReimbursementService.reimbDAO = reimbDAO; }
    
    public static void addReimbursement(NewReimbursementRequest request) {
        //TODO
        //reimbDAO.save(new Reimbursement());
    }
    
    public static List<Reimbursement> getReimbursementsByManager(User manager) {
        return reimbDAO.getByManager(manager);
    }

    public static List<Reimbursement> getReimbursementsByType(String type) {
        try {
            ReimbursementType rType = ReimbursementType.valueOf(type);
            return reimbDAO.getByType(rType);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Reimbursement> getReimbursementsByStatus(String status) {
        try {
            ReimbursementStatus rStatus = ReimbursementStatus.valueOf(status);
            return reimbDAO.getByStatus(rStatus);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Reimbursement> getReimbursementsByTypeAndStatus(String type, String status) {
        try {
            ReimbursementType rType = ReimbursementType.valueOf(type);
            ReimbursementStatus rStatus = ReimbursementStatus.valueOf(status);
            return reimbDAO.getByTypeAndStatus(rType, rStatus);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
