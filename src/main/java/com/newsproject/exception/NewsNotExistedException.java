package com.newsproject.exception;

public class NewsNotExistedException extends Exception{
    public NewsNotExistedException(String message){
        super(message);
    }
}
