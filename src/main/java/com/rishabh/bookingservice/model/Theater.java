package com.rishabh.bookingservice.model;

import lombok.Data;

import java.util.List;

@Data
public class Theater {
    private String theaterId;
    private List<Screen> listOfScreens;
}
