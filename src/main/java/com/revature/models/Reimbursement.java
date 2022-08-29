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
}
