package com.revature.models;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

public class Reimbursement {
    private UUID reimb_id;
    private BigDecimal amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private byte[] receipt;
    private UUID payment_id;
    private UUID author_id;
    private UUID resolver_id;
    private ReimbursementStatus status_id;
    private ReimbursementType type_id;

    public Reimbursement(UUID reimb_id, BigDecimal amount, Timestamp submitted, String description, UUID author_id,
            ReimbursementStatus status_id, ReimbursementType type_id) {
        this.reimb_id = reimb_id;
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.author_id = author_id;
        this.status_id = status_id;
        this.type_id = type_id;
    }

    public Reimbursement(UUID reimb_id, BigDecimal amount, Timestamp submitted, Timestamp resolved,
            String description, byte[] receipt, UUID payment_id, UUID author_id, UUID resolver_id,
            ReimbursementStatus status_id, ReimbursementType type_id) {
        this.reimb_id = reimb_id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
        this.author_id = author_id;
        this.resolver_id = resolver_id;
        this.status_id = status_id;
        this.type_id = type_id;
    }

    public UUID getReimb_id() {
        return reimb_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public UUID getPayment_id() {
        return payment_id;
    }

    public UUID getAuthor_id() {
        return author_id;
    }

    public UUID getResolver_id() {
        return resolver_id;
    }

    public ReimbursementStatus getStatus_id() {
        return status_id;
    }

    public ReimbursementType getType_id() {
        return type_id;
    }
}
