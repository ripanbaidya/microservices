package com.example.loans.constants;

// Constants used across the Accounts microservice
public final class LoansConstants {

    // Prevent instantiation of this utility class
    private LoansConstants() {
    }

    public static final String  HOME_LOAN = "Home Loan";
    public static final int  NEW_LOAN_LIMIT = 1_00_000;
    public static final String  STATUS_201 = "201";
    public static final String  MESSAGE_201 = "Loan created successfully";
    public static final String  STATUS_200 = "200";
    public static final String  MESSAGE_200 = "Request processed successfully";
    public static final String  STATUS_417 = "417";
    public static final String  MESSAGE_417_UPDATE= "Update operation failed. Please try again or contact Dev team";
    public static final String  MESSAGE_417_DELETE= "Delete operation failed. Please try again or contact Dev team";

    // We will handle the 500 Internal Server Error in a Global Exception Handler
    // public static final String  STATUS_500 = "500";
    // public static final String  MESSAGE_500 = "An error occurred. Please try again or contact the Dev team";
}