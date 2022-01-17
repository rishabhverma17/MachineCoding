package com.rishabh.bookingservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Ticket {
    private String bookingReferenceNumber;
    //private Screen screenDetails;
    private Movie movie;
    private Slot slot;
    private List<Seat> seatList;
    private BookingStates bookingState;
}
