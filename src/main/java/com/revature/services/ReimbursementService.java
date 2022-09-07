package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.dtos.requests.employee.DeleteReimbursementRequest;
import com.revature.dtos.requests.employee.NewReimbursementRequest;
import com.revature.dtos.requests.employee.UpdateReimbursementRequest;
import com.revature.dtos.requests.finanace.UpdateReimbursementStatusRequest;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.custom_exceptions.BadRequestException;
import com.revature.utils.custom_exceptions.ForbiddenException;
import com.revature.utils.custom_exceptions.NetworkException;
import com.revature.utils.custom_exceptions.NotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
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
                Timestamp.from(Instant.now()),
                null,
                request.getDescription(),
                null,
                UUID.fromString(request.getPayment_id()),
                userID,
                null,
                ReimbursementStatus.PENDING,
                ReimbursementType.valueOf(request.getType().toUpperCase())
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

    public static Reimbursement getReimbursementById(String id) {
        return reimbDAO.getByKey(id);
    }

    public static List<Reimbursement> getReimbursementsByAuthor(User user) {
        return reimbDAO.getByUser(user);
    }

    public static List<Reimbursement> getReimbursementsByManagerAndType(User manager, String type) {
        try {
            ReimbursementType rType = (type == null ? ReimbursementType.NULL : ReimbursementType.valueOf(type));
            return reimbDAO.getByManagerAndType(manager, rType);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Reimbursement> getReimbursementsByTypeAndStatus(String type, String status) {
        try {
            ReimbursementType rType = (type == null ? ReimbursementType.NULL : ReimbursementType.valueOf(type));
            ReimbursementStatus rStatus = (status == null ? ReimbursementStatus.NULL : ReimbursementStatus.valueOf(status));
            return reimbDAO.getByTypeAndStatus(rType, rStatus);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void updateReimbursement(User manager, UpdateReimbursementStatusRequest request) throws NetworkException {
        try {
            if (request.getReimb_id() == null || request.getReimb_id().isEmpty()) throw new BadRequestException("No id given.");
            Reimbursement reimb = ReimbursementService.getReimbursementById(request.getReimb_id());
            if (reimb == null) throw new NotFoundException("No reimbursement was found with that id.");
            if (reimb.getStatus_id() != ReimbursementStatus.PENDING)
                throw new ForbiddenException("Finance managers are only allowed to change Pending requests.");
            reimb.setResolved(Timestamp.from(Instant.now()));
            reimb.setResolver(manager.getUserID());
            reimb.setStatus(ReimbursementStatus.valueOf(request.getStatus().toUpperCase()));
            reimbDAO.setStatus(reimb);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status");
        }
    }

    public static void verifyCanModify(String reimb_id, UUID userID){
        Reimbursement reimbursement = getReimbursementById(reimb_id);
        if(reimbursement == null)
            throw new BadRequestException("Can not delete reimbursement that does not exit.");
        if(!reimbursement.getAuthor_id().equals(userID))
            throw new ForbiddenException("Unauthorized Reimbursement Modification. Employees can only modify their own reimbursements.");
        if(!reimbursement.getStatus_id().equals(ReimbursementStatus.PENDING))
            throw new ForbiddenException("Unauthorized Reimbursement Modification. Can only modify pending reimbursements.");
    }

    public static void deleteReimbursement(DeleteReimbursementRequest request) {
        reimbDAO.delete(UUID.fromString(request.getReimb_id()));
    }
}