package com.example.ebookrepository.model;

public class Status {

    private boolean success;
    private String message;

    public Status(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getStatus() {
        return success;
    }

    public void setStatus(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
