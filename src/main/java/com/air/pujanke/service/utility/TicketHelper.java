package com.air.pujanke.service.utility;

import com.air.pujanke.model.custom.Seat;

import java.util.BitSet;
import java.util.List;

public class TicketHelper {
    public TicketHelper() {}

    public static int seatToIndex(Seat seat, int cols) {
        return (seat.getSeatRow() - 1) * cols + seat.columnCharToIndex();
    }

    public static Seat indexToSeat(int idx, int cols) {
        int row = (idx / cols) + 1;
        int colIndex = idx % cols;
        char col = Seat.indexToColumnChar(colIndex);
        return new Seat(row, col);
    }

    public static BitSet constructSeatMap(int rows, int cols, List<Seat> takenSeats) {
        var seatMap = new BitSet(rows * cols);
        takenSeats.stream().mapToInt((seat) -> seatToIndex(seat, cols)).forEach(seatMap::set);
        return seatMap;
    }

    public static int pickKthClearBit(BitSet taken, int totalSeats, int k) {
        int index = taken.nextClearBit(0);
        while (k > 0 && index >= 0 && index < totalSeats) {
            index = taken.nextClearBit(index + 1);
            k--;
        }
        return index;
    }
}
