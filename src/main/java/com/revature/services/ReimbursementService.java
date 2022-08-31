package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.dtos.requests.NewReimbursementRequest;
import com.revature.dtos.requests.UpdateReimbursementRequest;
import com.revature.models.*;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimbursementService {
    private static ReimbursementDAO reimbDAO = new ReimbursementDAO();

    public ReimbursementService(ReimbursementDAO reimbDAO) { ReimbursementService.reimbDAO = reimbDAO; }

    public static void addReimbursement(NewReimbursementRequest request, UUID userID) {
        reimbDAO.save(new Reimbursement(
                UUID.randomUUID(),
                request.getAmount(),
                Timestamp.from(ZonedDateTime.now().toInstant()),
                null,
                request.getDescription(),
                null,
                UUID.fromString(request.getPayment_id()),
                userID,
                null,
                ReimbursementStatus.PENDING,
                ReimbursementType.valueOf(request.getType())
        ));
    }

    public static void updateReimbursement(UpdateReimbursementRequest request, UUID userID){
        reimbDAO.update(new Reimbursement(
                UUID.fromString(request.getReimb_id()),
                request.getAmount(),
                reimbDAO.getByKey(request.getReimb_id()).getSubmitted(),
                null,
                request.getDescription(),
                null,
                UUID.fromString(request.getPayment_id()),
                userID,
                null,
                ReimbursementStatus.PENDING,
                ReimbursementType.valueOf(request.getType())
        ));
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

    public static List<Reimbursement> getReimbursementsByAuthor(User user) {
        return reimbDAO.getByUser(user);
    }
}