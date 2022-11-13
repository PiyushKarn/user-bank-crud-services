package com.project.first.userbankcrud.Response;

public class Failure implements Response {

    String message;

    public Failure(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Object getPayLoad() {
        return null;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
