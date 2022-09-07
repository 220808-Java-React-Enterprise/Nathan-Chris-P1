package com.revature.dtos.requests;

public class DeleteReimbursementRequest {
    private String reimb_id;

    public DeleteReimbursementRequest() {
    }

    //TODO Possibly remove this
    public DeleteReimbursementRequest(String reimb_id) {
        this.reimb_id = reimb_id;
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
