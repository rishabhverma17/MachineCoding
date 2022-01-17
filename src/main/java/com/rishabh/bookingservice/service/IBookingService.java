package com.rishabh.bookingservice.service;

import com.rishabh.bookingservice.exceptions.InvalidSlotException;
import com.rishabh.bookingservice.exceptions.NoSeatAvailableException;
import com.rishabh.bookingservice.model.Movie;
import com.rishabh.bookingservice.model.Ticket;

import java.util.Map;

public interface IBookingService {
    Ticket doBooking(Movie movie, int slotTime, int numberOfSeats) throws InvalidSlotException, NoSeatAvailableException;

    Map<String, Ticket> getBookingHistory();

    void cancelBooking();
}
