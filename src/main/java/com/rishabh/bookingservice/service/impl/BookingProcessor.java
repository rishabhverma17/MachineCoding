package com.rishabh.bookingservice.service.impl;

import com.rishabh.bookingservice.model.Movie;
import com.rishabh.bookingservice.service.IBookingService;
import lombok.SneakyThrows;

public class BookingProcessor implements Runnable{

    private IBookingService bookingService;
    private Movie movie;
    private int slotId;
    private int numberOfSeats;

    public BookingProcessor(IBookingService bookingService, Movie movie, int slotId, int numberOfSeats) {
        this.bookingService = bookingService;
        this.movie = movie;
        this.slotId = slotId;
        this.numberOfSeats = numberOfSeats;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (bookingService){
            bookingService.doBooking(movie,slotId, numberOfSeats);
        }
    }
}
