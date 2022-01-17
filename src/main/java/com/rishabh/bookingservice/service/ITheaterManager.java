package com.rishabh.bookingservice.service;

import com.rishabh.bookingservice.model.Movie;
import com.rishabh.bookingservice.model.Screen;
import com.rishabh.bookingservice.model.Theater;

import java.util.List;
import java.util.Map;

public interface ITheaterManager {
    Map<String, Screen> getMovieTheaterMap();

    List<Screen> getScreenList();

    Theater getTheaterInstance();

    void assignMovieToScreen(Movie movie, String screenId, Theater theater);
}
