package com.air.pujanke.model.custom;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Seat implements Serializable {

    @Column(name = "seat_row", nullable = false)
    private Integer seatRow;

    @Column(name = "seat_column", nullable = false)
    private Character seatColumn;


    @Override
    public String toString() {
        return String.format("%d%c", seatRow, seatColumn);
    }

    public static Seat fromString(String seat) {
        return new Seat(Integer.parseInt(seat.substring(0, seat.length() - 1)), Character.toUpperCase(seat.charAt(seat.length() - 1)));
    }

    public int columnCharToIndex() {
        return seatColumn - 'A';
    }

    public static char indexToColumnChar(int index) {
        return (char)(index + 'A');
    }
}
