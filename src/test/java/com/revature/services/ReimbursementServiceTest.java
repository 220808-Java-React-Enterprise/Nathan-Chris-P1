package com.revature.services;

import com.revature.daos.ReimbursementDAO;
import com.revature.dtos.requests.employee.NewReimbursementRequest;
import com.revature.dtos.requests.employee.UpdateReimbursementRequest;
import com.revature.dtos.requests.finance.UpdateReimbursementStatusRequest;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.models.User;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.internal.matchers.NotNull;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class ReimbursementServiceTest {

    UUID uuidForAdmin = UUID.randomUUID();
    UUID uuidForFinance = UUID.randomUUID();
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
    public void getReimbursementsByTypeAndStatus() {
        ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
        new ReimbursementService(mockReimbDAO);

        ReimbursementService.getReimbursementsByTypeAndStatus("FOOD", "PENDING");

        verify(mockReimbDAO, times(1)).getByTypeAndStatus(any(), any());
    }

    @Test()
    public void updateReimbursementStatus() {
        try (MockedStatic<ReimbursementService> mockedReimbursementService = mockStatic(ReimbursementService.class)) {
            Reimbursement reimb = mock(Reimbursement.class);
            mockedReimbursementService.when(() -> ReimbursementService.getReimbursementById(any())).thenReturn(reimb);
            ReimbursementDAO mockReimbDAO = mock(ReimbursementDAO.class);
            User user = mock(User.class);
            new ReimbursementService(mockReimbDAO);
            UpdateReimbursementStatusRequest request = mock(UpdateReimbursementStatusRequest.class);
            when(request.getReimb_id()).thenReturn(UUID.randomUUID().toString());

            ReimbursementService.updateReimbursement(user, request);

            verify(mockReimbDAO, times(1)).setStatus(any());
        }
    }

    @Test
    public void verifyCanModify() {
    }

    @Test
    public void deleteReimbursement() {
    }
}