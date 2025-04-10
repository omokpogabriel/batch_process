package com.gabby.spring.batch.services;

public class BatchProcessException extends RuntimeException {

    public BatchProcessException(String msg){
        super(msg);
    }
}
