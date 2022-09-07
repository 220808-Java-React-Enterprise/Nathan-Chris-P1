package com.revature.dtos.requests.employee;

import java.math.BigDecimal;

public class UpdateReimbursementRequest {
    private String reimb_id;
    private BigDecimal amount;
    private String description;

    //TODO put receipts back in
    //private String receipt;
    private String payment_id;
    private String type;

    public UpdateReimbursementRequest() {
    }

    public UpdateReimbursementRequest(String reim_id, BigDecimal amount, String description, String payment_id, String type) {
        this.reimb_id = reim_id;
        this.amount = amount;
        this.description = description;
        //this.receipt = receipt;
        this.payment_id = payment_id;
        this.type = type;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    /**public String getReceipt() {
        return receipt;
    }**/

    public String getPayment_id() {
        return payment_id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "UpdateReimbursementRequest{" +
                "reimb_id=" + reimb_id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
