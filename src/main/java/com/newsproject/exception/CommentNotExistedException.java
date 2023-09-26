package com.newsproject.exception;

public class CommentNotExistedException extends Exception{
    public CommentNotExistedException(String message){
        super(message);
    }
}
