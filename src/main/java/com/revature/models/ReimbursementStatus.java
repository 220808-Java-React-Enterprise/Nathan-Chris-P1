package com.revature.models;

public enum ReimbursementStatus {
    NONE,
    PENDING {
        @Override
        public String toString() {
            return "Pending";
        }
    },
    DENIED {
        @Override
        public String toString() {
            return "Denied";
        }
    },
    APPROVED {
        @Override
        public String toString() {
            return "Approved";
        }
    },
    STATUS
}
