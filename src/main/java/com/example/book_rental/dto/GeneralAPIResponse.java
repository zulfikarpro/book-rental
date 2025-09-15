package com.example.book_rental.dto;

import com.example.book_rental.entity.Borrower;

import java.util.List;

public class GeneralAPIResponse<T> {
    private String message;
    private T data;

    public GeneralAPIResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public GeneralAPIResponse(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
