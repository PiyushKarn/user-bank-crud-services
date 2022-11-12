package com.project.first.userbankcrud.Exception;

public class UserAlreadyExistsException extends RuntimeException{

    private String msg;

    public UserAlreadyExistsException() {
    }
    public UserAlreadyExistsException(String msg) {

        super(msg);
        this.msg = msg;

    }

}
