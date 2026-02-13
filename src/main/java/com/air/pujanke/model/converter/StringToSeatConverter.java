package com.air.pujanke.model.converter;

import com.air.pujanke.model.custom.Seat;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSeatConverter implements Converter<String, Seat> {

    @Override
    public Seat convert(String seatId) {
        return Seat.fromString(seatId);
    }
}
