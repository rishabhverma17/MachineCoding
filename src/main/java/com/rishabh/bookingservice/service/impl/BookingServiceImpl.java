package com.rishabh.bookingservice.service.impl;

import com.rishabh.bookingservice.exceptions.InvalidSlotException;
import com.rishabh.bookingservice.exceptions.NoSeatAvailableException;
import com.rishabh.bookingservice.model.*;
import com.rishabh.bookingservice.service.IBookingService;
import com.rishabh.bookingservice.service.IPaymentService;
import com.rishabh.bookingservice.service.ITheaterManager;
import com.sun.istack.internal.NotNull;

import java.util.*;

public class BookingServiceImpl implements IBookingService{

    private Map<String, Ticket> bookingHistory;
    private ITheaterManager theaterManager;
    private IPaymentService paymentService;
    private int retryCount;

    public BookingServiceImpl(@NotNull  Map<String,Ticket> bookingHistory, @NotNull ITheaterManager theaterManager,
                              @NotNull IPaymentService paymentService, @NotNull int retryCount){
        this.bookingHistory = new HashMap<>();
        this.theaterManager = theaterManager;
        this.paymentService = paymentService;
        this.retryCount = retryCount;
    }

    @Override
    public Ticket doBooking(Movie movie, int slotTime, int numberOfSeats) throws InvalidSlotException, NoSeatAvailableException {
        Map<String, Screen> movieTheaterMap = theaterManager.getMovieTheaterMap();
        Screen screen = movieTheaterMap.get(movie.getMovieTitle());
        List<Slot> availableSlots = screen.getAvailableSlots();
        List<Seat> availableSeats = screen.getAvailableSeats();
        if(availableSlots.size() == 0 || availableSlots.get(slotTime).getSlotId()!= slotTime+1){
            throw new InvalidSlotException("Slot not available");
        }

        if(availableSeats.size() == 0 || availableSeats.size() < numberOfSeats){
            throw new NoSeatAvailableException("Seat(s) not Available");
        }

        // Can book now
        Ticket ticket = new Ticket();
        ticket.setBookingReferenceNumber(UUID.randomUUID().toString());


        // Slot Occupy
        Slot slot = availableSlots.get(slotTime);
        screen.getAvailableSlots().remove(slot);
        slot.setAvailable(false);
        screen.getOccupiedSlots().add(slot);

        // Seat occupy
        List<Seat> seatList = new ArrayList<>();
        for (int i = 0; i < numberOfSeats; i++) {
            Seat seat = screen.getAvailableSeats().get(0);
            seat.setBookingReferenceNumber(ticket.getBookingReferenceNumber());
            seat.setBooked(true);
            screen.getOccupiedSeats().add(seat);
            screen.getAvailableSeats().remove(seat);
            seatList.add(seat);
        }

        // Payment Section
        ticket.setBookingState(BookingStates.INI);
        boolean paymentStatus = paymentService.doPayment();
        if(paymentStatus){
            paymentStatus = paymentService.retryPayment(retryCount);
        }

        if(paymentStatus == false){
            ticket.setBookingState(BookingStates.FLD);
            bookingHistory.put(ticket.getBookingReferenceNumber(), ticket);
            System.out.println("Payment failed for booking ref Id "+ ticket.getBookingReferenceNumber());

            // Revert provisional booking
            screen.getOccupiedSlots().remove(slot);
            slot.setAvailable(true);
            screen.getAvailableSlots().add(slot);

            for(Seat s : seatList){
                screen.getOccupiedSeats().remove(s);
                s.setBookingReferenceNumber(null);
                s.setBooked(false);
                screen.getAvailableSeats().add(s);
            }
            return ticket;
        }

        ticket.setBookingState(BookingStates.CRD);
        ticket.setMovie(movie);
        ticket.setSlot(slot);
        ticket.setSeatList(seatList);

        // save to booking history
        this.bookingHistory.put(ticket.getBookingReferenceNumber(), ticket);
        System.out.println("Booking Confirmed, Booking refId " +ticket.getBookingReferenceNumber());
        return ticket;
    }

    @Override
    public Map<String, Ticket> getBookingHistory() {
        return bookingHistory;
    }

    @Override
    public void cancelBooking() {
    }
}
