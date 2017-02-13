package com.manardenza.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Reservation extends AbstractObject {

    private Date reservedFrom;
    private Date reservedTo;
    private User reservedUser;
    private Room reservedRoom;
    private Hotel reservedHotel;

    public boolean overlaps(Date newReservedFrom, Date newReservedTo) {
        return reservedFrom.getTime() <= newReservedTo.getTime() && newReservedFrom.getTime() <= reservedTo.getTime();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(reservedUser).append("\n")
                .append(reservedHotel.getName()).append("\t\n")
                .append("Room = '").append(reservedRoom)
                .append("Reserved from = ").append(reservedFrom).append("\n")
                .append("Reserved to = ").append(reservedTo).append("\n");
        return sb.toString();
    }
}
