package com.revature.models;

public enum UserRole {
    NULL,
    EMPLOYEE{
        @Override
        public String toString(){
            return "Employee";
        }
    },
    FINANCE_MANAGER{
        @Override
        public String toString(){
            return "Finance Manager";
        }
    },
    ADMIN{
        @Override
        public String toString(){
            return "Admin";
        }
    },
    COUNT
}
