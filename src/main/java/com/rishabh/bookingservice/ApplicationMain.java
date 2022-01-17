package com.rishabh.bookingservice;

import com.rishabh.bookingservice.exceptions.InvalidSlotException;
import com.rishabh.bookingservice.exceptions.NoSeatAvailableException;
import com.rishabh.bookingservice.model.*;
import com.rishabh.bookingservice.service.IBookingService;
import com.rishabh.bookingservice.service.IPaymentService;
import com.rishabh.bookingservice.service.ITheaterManager;
import com.rishabh.bookingservice.service.impl.BookingProcessor;
import com.rishabh.bookingservice.service.impl.BookingServiceImpl;
import com.rishabh.bookingservice.service.impl.PaymentServiceImpl;
import com.rishabh.bookingservice.service.impl.TheaterManager;
import jdk.nashorn.internal.runtime.ScriptRuntime;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationMain {
    public static void main(String[] args) throws NoSeatAvailableException, InvalidSlotException, InterruptedException {
        // Setup
        ITheaterManager theaterManager = new TheaterManager(2, 5);
        Movie movie1 = new Movie("Movie-1");
        Movie movie2 = new Movie("Movie-2");
        List<Screen> screenList = theaterManager.getScreenList();
        theaterManager.assignMovieToScreen(movie1, screenList.get(0).getScreenId(), theaterManager.getTheaterInstance());
        theaterManager.assignMovieToScreen(movie2, screenList.get(1).getScreenId(), theaterManager.getTheaterInstance());

        // Booking
        Map<String, Ticket> bookingHistory = new ConcurrentHashMap<>();
        IPaymentService paymentService = new PaymentServiceImpl();
        int retryCount = 2;
        IBookingService bookingService = new BookingServiceImpl(bookingHistory, theaterManager, paymentService, retryCount);
        //Ticket ticket = bookingService.doBooking(movie1, 1, 2);

        BookingProcessor processor = new BookingProcessor(bookingService, movie1, 1, 1);
        Thread t1 = new Thread(processor);
        t1.start();

        for(Seat s : screenList.get(0).getAvailableSeats()){
            if(s.isBooked()){
                System.out.println(s.getBookingReferenceNumber() + " seat ID :: "+ s.getSeatId());
            }
        }
        t1.join();
        BookingProcessor processor2 = new BookingProcessor(bookingService, movie1, 2, 1);
        Thread t2 = new Thread(processor2);
        t2.start();

        System.out.println("END");

    }
}
