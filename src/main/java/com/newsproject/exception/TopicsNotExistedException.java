package com.newsproject.exception;

import lombok.Data;

@Data
public class TopicsNotExistedException extends Exception {
    public TopicsNotExistedException(String message) {
        super(message);
    }
}
