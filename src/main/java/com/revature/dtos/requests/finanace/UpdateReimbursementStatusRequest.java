package com.revature.dtos.requests.finanace;

public class UpdateReimbursementStatusRequest {
    String reimb_id;
    String status;

    public UpdateReimbursementStatusRequest() {
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UpdateReimbursementStatusRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
