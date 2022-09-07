package com.revature.dtos.requests.employee;

import java.math.BigDecimal;

public class NewReimbursementRequest {
    private BigDecimal amount;
    private String description;

    //TODO put receipts back in
    //private String receipt;
    private String payment_id;
    private String type;

    public NewReimbursementRequest() {
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
        return "NewReimbursementRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
