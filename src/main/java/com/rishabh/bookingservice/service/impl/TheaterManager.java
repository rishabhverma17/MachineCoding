package com.rishabh.bookingservice.service.impl;

import com.rishabh.bookingservice.model.*;
import com.rishabh.bookingservice.service.ITheaterManager;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TheaterManager implements ITheaterManager {

    private int numberOfScreens;
    private Theater theater;
    private int numberOfSeats;
    private Map<String, Screen> movieTheaterMap;
    private List<Screen> screensList;

    public TheaterManager(@NotNull final int numberOfScreens, @NotNull final int numberOfSeats){
        this.numberOfScreens = numberOfScreens;
        this.numberOfSeats = numberOfSeats;
        this.movieTheaterMap = new ConcurrentHashMap<>();
        this.theater = init();
        this.screensList = createScreens();
    }

    private Theater init(){
        theater = new Theater();
        theater.setTheaterId(UUID.randomUUID().toString());
        return theater;
    }

    @Override
    public Map<String, Screen> getMovieTheaterMap() {
        return this.movieTheaterMap;
    }

    @Override
    public List<Screen> getScreenList() {
        return this.screensList;
    }

    @Override
    public Theater getTheaterInstance() {
        return this.theater;
    }

    @Override
    public void assignMovieToScreen(@NotNull final Movie movie,@NotNull final String screenId, Theater theater) {
        List<Screen> screens = theater.getListOfScreens().stream()
                .filter(s -> screenId.equalsIgnoreCase(screenId)).collect(Collectors.toList());
        screens.get(0).setMovie(movie);
        movieTheaterMap.put(movie.getMovieTitle(), screens.get(0));
        System.out.println("Assigned Movie :: "+movie.getMovieTitle()+ " to Screen "+screens.get(0).getScreenId());
    }

    private List<Screen> createScreens(){
        List<Screen> screens = new ArrayList<>();
        for (int i = 0; i < numberOfScreens; i++) {
            Screen screen = new Screen();
            screen.setScreenId(UUID.randomUUID().toString());
            screen.setAvailableSeats(createSeats());
            screen.setOccupiedSeats(new ArrayList<>());
            screen.setAvailableSlots(createSlots(screen.getScreenId()));
            screen.setOccupiedSlots(new ArrayList<>(24));
            screens.add(screen);
        }
        System.out.println("Created "+numberOfScreens+" of screens");
        theater.setListOfScreens(screens);
        return screens;
    }

    private List<Seat> createSeats(){
        List<Seat> seatLists = new ArrayList<>();
        for(int i=0; i<numberOfSeats; i++){
            Seat seat = new Seat();
            seat.setSeatId(i);
            seatLists.add(seat);
        }
        return seatLists;
    }

    private List<Slot> createSlots(String screenId){
        List<Slot> slotLists = new ArrayList<>();
        for(int i=0; i<24; i++){
            Slot slot = new Slot(screenId, i);
            slotLists.add(slot);
        }
        return slotLists;
    }
}
