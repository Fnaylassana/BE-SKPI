package com.skpijtk.springboot_boilerplate.constant;

public enum ResponseMessage {
    
    SIGNUP_SUCCESS("T-SUCC-001", "Signup successful", 200),
    LOGIN_SUCCESS("T-SUCC-002", "Login successful", 200),
    SEARCH_SUCCESS("T-SUCC-004", "Student data successfully found.", 200),
    FILTER_SUCCESS("T-SUCC-005", "Data successfully displayed.", 200),
    DELETE_SUCCESS("T-SUCC-006", "Student {student name} successfully deleted.", 200),
    
    CHECK_LATE_WARNING("T-WAR-001", "Your {Checkin / Checkout} is Late", 200),
    
    CHECK_SUCCESS("T-SUCC-007", "{Checkin / Checkout} successful", 201),
    SAVE_SUCCESS("T-SUCC-008", "Data successfully saved", 201),

    FIELD_TOO_LONG("T-ERR-003", "{column} must be at most {max_length} characters", 400),
    FIELD_TOO_SHORT("T-ERR-004", "{column} must be at least {min_length} characters", 400),
    FILTER_FAIL("T-ERR-006", "Data failed to display.", 400),
    DELETE_FAIL("T-ERR-007", "Student {student name} deletion failed.", 400),
    CHECK_FAIL_NOTE_EMPTY("T-ERR-009", "{Checkin / Checkout} Failed because the Note is empty", 400),
    SAVE_FAIL("T-ERR-010", "Data Failed to saved", 400),
    CHECKIN_FAIL_ALREADY_EXIST("T-ERR-011", "Already {Checkin / Checkout}", 400),
    BAD_REQUEST("T-ERR-012", "Cant save data, bad request", 400),
    
    INVALID_CREDENTIALS("T-ERR-001", "Invalid username or password", 401),

    SEARCH_FAIL("T-ERR-005", "Student data not found.", 404),
    
    DUPLICATE_USERNAME_EMAIL("T-ERR-008", "Username or Email has been used", 409),

    INTERNAL_ERROR("T-ERR-002", "Internal Server Error. Please try again", 500);

    private final String code;
    private final String message;
    private final int statusCode;

    ResponseMessage(String code, String message, int statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

