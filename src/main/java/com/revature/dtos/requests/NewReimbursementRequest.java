package com.revature.dtos.requests;

import java.math.BigDecimal;

public class NewReimbursementRequest {
    private BigDecimal amount;
    private String description;

    //TODO put receipts back in
    //private String receipt;
    private String payment_id;
    private String author_username;
    private String type;

    public NewReimbursementRequest() {
    }

    public NewReimbursementRequest(BigDecimal amount, String description, String payment_id, String author_username, String type) {
        this.amount = amount;
        this.description = description;
        //this.receipt = receipt;
        this.payment_id = payment_id;
        this.author_username = author_username;
        this.type = type;
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

    public String getAuthor_username() {
        return author_username;
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
                ", author_username='" + author_username + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
