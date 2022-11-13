package com.project.first.userbankcrud.Response;

public class Success implements Response {

    String message;
    Object payload;

    public Success(String message, Object payload) {
        this.message = message;
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayLoad() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
