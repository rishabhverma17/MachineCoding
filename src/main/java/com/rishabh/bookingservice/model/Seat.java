package com.rishabh.bookingservice.model;

import lombok.Data;

@Data
public class Seat {
    private int seatId;
    private boolean isBooked;
    private String bookingReferenceNumber;
}
