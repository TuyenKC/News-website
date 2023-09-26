package com.newsproject.exception;

public class UsersNotExistedException extends Exception{
    public UsersNotExistedException(String message){
        super(message);
    }
}
