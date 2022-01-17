package com.rishabh.bookingservice.model;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
public class Slot {
    private String screenId;
    @Setter
    private boolean isAvailable;
    private int slotId;

    public Slot(@NotNull final String screenId, @NotNull final int slotId){
        this.screenId = screenId;
        this.isAvailable = true;
        this.slotId = slotId;
    }
}
