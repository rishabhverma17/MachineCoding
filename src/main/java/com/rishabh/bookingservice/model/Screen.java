package com.rishabh.bookingservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Screen {
    private String screenId;
    private Movie movie;
    private List<Slot> occupiedSlots;
    private List<Slot> availableSlots;
    private List<Seat> availableSeats;
    private List<Seat> occupiedSeats;
}
