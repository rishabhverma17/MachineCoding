package com.rishabh.bookingservice.model;

import lombok.Data;

@Data
public class Movie {
    private String movieTitle;

    public Movie(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
