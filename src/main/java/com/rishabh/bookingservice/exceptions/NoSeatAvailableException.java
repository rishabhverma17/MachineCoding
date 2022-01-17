package com.rishabh.bookingservice.exceptions;

public class NoSeatAvailableException extends Exception{
    public NoSeatAvailableException(String message) {
        super(message);
    }
}
