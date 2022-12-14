package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.dtos.requests.employee.DeleteReimbursementRequest;
import com.revature.dtos.requests.employee.NewReimbursementRequest;
import com.revature.dtos.requests.employee.UpdateReimbursementRequest;
import com.revature.dtos.requests.finance.UpdateReimbursementStatusRequest;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import com.revature.utils.custom_exceptions.BadRequestException;
import com.revature.utils.custom_exceptions.ForbiddenException;
import com.revature.utils.custom_exceptions.NotFoundException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class ReimbursementServiceTest {

    UUID uuidForEmployee = UUID.randomUUID();
    @Test
    public void addReimbursement() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);
        NewReimbursementRequest request = mock(NewReimbursementRequest.class);
        when(request.getPayment_id()).thenReturn(UUID.randomUUID().toString());
        when(request.getType()).thenReturn(ReimbursementType.FOOD.name());

        ReimbursementService.addReimbursement(request, uuidForEmployee);

        verify(mockReimbDAO, times(1)).save(any());
    }

    @Test
    public void updateReimbursement() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        Reimbursement reimb = mock(Reimbursement.class);
        new ReimbursementService(mockReimbDAO);
        UpdateReimbursementRequest request = mock(UpdateReimbursementRequest.class);
        when(mockReimbDAO.getByKey(any())).thenReturn(reimb);
        when(request.getReimb_id()).thenReturn(UUID.randomUUID().toString());
        when(request.getPayment_id()).thenReturn(UUID.randomUUID().toString());
        when(request.getType()).thenReturn(ReimbursementType.FOOD.name());

        ReimbursementService.updateReimbursement(request, uuidForEmployee);

        verify(mockReimbDAO, times(1)).update(any());
        verify(mockReimbDAO, times(1)).getByKey(any());
    }

    @Test(expected = BadRequestException.class)
    public void updateReimbursementNoId() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        Reimbursement reimb = mock(Reimbursement.class);
        new ReimbursementService(mockReimbDAO);
        UpdateReimbursementRequest request = mock(UpdateReimbursementRequest.class);
        when(request.getReimb_id()).thenReturn(UUID.randomUUID().toString());
        when(request.getPayment_id()).thenReturn(UUID.randomUUID().toString());
        when(request.getType()).thenReturn(ReimbursementType.FOOD.name());

        ReimbursementService.updateReimbursement(request, uuidForEmployee);
    }

    @Test
    public void getReimbursementById() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        Reimbursement reimb = mock(Reimbursement.class);
        new ReimbursementService(mockReimbDAO);
        when(mockReimbDAO.getByKey(any())).thenReturn(reimb);

        assertNotNull(ReimbursementService.getReimbursementById(UUID.randomUUID().toString()));

        verify(mockReimbDAO, times(1)).getByKey(any());
    }

    @Test
    public void getReimbursementsByAuthor() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        User user = mock(User.class);
        new ReimbursementService(mockReimbDAO);
        
        ReimbursementService.getReimbursementsByAuthor(user);

        verify(mockReimbDAO, times(1)).getByUser(any());
    }

    @Test
    public void getReimbursementsByManagerAndType() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        User user = mock(User.class);
        new ReimbursementService(mockReimbDAO);

        ReimbursementService.getReimbursementsByManagerAndType(user, "FOOD");

        verify(mockReimbDAO, times(1)).getByManagerAndType(any(), any());
    }

    @Test
    public void getReimbursementsByManagerAndBadType() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        User user = mock(User.class);
        new ReimbursementService(mockReimbDAO);

        ReimbursementService.getReimbursementsByManagerAndType(user, "GARBAGE");

        verify(mockReimbDAO, times(0)).getByManagerAndType(any(), any());
    }

    @Test
    public void getReimbursementsByTypeAndStatus() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);

        ReimbursementService.getReimbursementsByTypeAndStatus("FOOD", "PENDING");

        verify(mockReimbDAO, times(1)).getByTypeAndStatus(any(), any());
    }

    @Test
    public void updateReimbursementStatus() {
            ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
            User user = mock(User.class);
            Reimbursement reimb = mock(Reimbursement.class);
            new ReimbursementService(mockReimbDAO);
            when(mockReimbDAO.getByKey(any())).thenReturn(reimb);
            UpdateReimbursementStatusRequest request = mock(UpdateReimbursementStatusRequest.class);
            when(request.getReimb_id()).thenReturn(UUID.randomUUID().toString());
            when(request.getStatus()).thenReturn(ReimbursementStatus.APPROVED.name());
            when(reimb.getStatus_id()).thenReturn(ReimbursementStatus.PENDING);

            ReimbursementService.updateReimbursement(user, request);

            verify(mockReimbDAO, times(1)).setStatus(any());
    }

    @Test(expected = BadRequestException.class)
    public void updateReimbursementStatusNoId() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        User user = mock(User.class);
        new ReimbursementService(mockReimbDAO);
        UpdateReimbursementStatusRequest request = mock(UpdateReimbursementStatusRequest.class);

        ReimbursementService.updateReimbursement(user, request);

        verify(mockReimbDAO, times(1)).setStatus(any());
    }

    @Test(expected = NotFoundException.class)
    public void updateReimbursementStatusNotFound() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        User user = mock(User.class);
        Reimbursement reimb = mock(Reimbursement.class);
        new ReimbursementService(mockReimbDAO);
        UpdateReimbursementStatusRequest request = mock(UpdateReimbursementStatusRequest.class);
        when(request.getReimb_id()).thenReturn(UUID.randomUUID().toString());

        ReimbursementService.updateReimbursement(user, request);

        verify(mockReimbDAO, times(1)).setStatus(any());
    }

    @Test(expected = ForbiddenException.class)
    public void updateReimbursementStatusNotPending() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        User user = mock(User.class);
        Reimbursement reimb = mock(Reimbursement.class);
        new ReimbursementService(mockReimbDAO);
        when(mockReimbDAO.getByKey(any())).thenReturn(reimb);
        UpdateReimbursementStatusRequest request = mock(UpdateReimbursementStatusRequest.class);
        when(request.getReimb_id()).thenReturn(UUID.randomUUID().toString());
        when(request.getStatus()).thenReturn(ReimbursementStatus.APPROVED.name());
        when(reimb.getStatus_id()).thenReturn(ReimbursementStatus.APPROVED);

        ReimbursementService.updateReimbursement(user, request);

        verify(mockReimbDAO, times(1)).setStatus(any());
    }

    @Test
    public void verifyCanModify() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);
        Reimbursement reimb = mock(Reimbursement.class);
        when(mockReimbDAO.getByKey(any())).thenReturn(reimb);
        when(reimb.getAuthor_id()).thenReturn(uuidForEmployee);
        when(reimb.getStatus_id()).thenReturn(ReimbursementStatus.PENDING);
        
        ReimbursementService.verifyCanModify(UUID.randomUUID().toString(), uuidForEmployee);
    }

    @Test(expected = NotFoundException.class)
    public void verifyCanModifyNotFound() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);
        Reimbursement reimb = mock(Reimbursement.class);

        ReimbursementService.verifyCanModify(UUID.randomUUID().toString(), uuidForEmployee);
    }

    @Test(expected = ForbiddenException.class)
    public void verifyCanModifyWrongUser() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);
        Reimbursement reimb = mock(Reimbursement.class);
        when(mockReimbDAO.getByKey(any())).thenReturn(reimb);
        when(reimb.getAuthor_id()).thenReturn(UUID.randomUUID());
        when(reimb.getStatus_id()).thenReturn(ReimbursementStatus.PENDING);

        ReimbursementService.verifyCanModify(UUID.randomUUID().toString(), uuidForEmployee);
    }

    @Test(expected = ForbiddenException.class)
    public void verifyCanModifyNotPending() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);
        Reimbursement reimb = mock(Reimbursement.class);
        when(mockReimbDAO.getByKey(any())).thenReturn(reimb);
        when(reimb.getAuthor_id()).thenReturn(uuidForEmployee);
        when(reimb.getStatus_id()).thenReturn(ReimbursementStatus.APPROVED);

        ReimbursementService.verifyCanModify(UUID.randomUUID().toString(), uuidForEmployee);
    }

    @Test
    public void deleteReimbursement() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);
        DeleteReimbursementRequest request = mock(DeleteReimbursementRequest.class);
        when(request.getReimb_id()).thenReturn(UUID.randomUUID().toString());
        ReimbursementService.deleteReimbursement(request);
        
        verify(mockReimbDAO, times(1)).delete(any());
    }
}