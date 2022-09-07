package com.revature.dtos.requests.employee;

public class DeleteReimbursementRequest {
    private String reimb_id;

    public DeleteReimbursementRequest() {
    }

    public String getReimb_id() {
        return reimb_id;
    }

    @Override
    public String toString() {
        return "DeleteReimbursementRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                '}';
    }
}
