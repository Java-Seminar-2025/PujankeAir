package com.air.pujanke.model.dto.flight;

import com.air.pujanke.model.custom.Seat;

import java.util.List;

public record FlightSeatConfigurationDto(Integer seatRowCount,
                                         Integer seatColumnCount,
                                         List<Seat> takenSeats) {
}
