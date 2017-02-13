package com.manardenza.entity;

import lombok.Data;

import java.util.List;

@Data
public class Hotel extends AbstractObject {

    private String name;
    private String city;
    private List<Room> rooms;

    public Hotel(String name, String city, List<Room> rooms) {
        this.name = name;
        this.city = city;
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hotel");
        sb.append("name = '").append(name).append('\'' + "\t")
                .append("city = '").append(city).append('\'' + "\t\n")
                .append("Rooms: \n'").append(rooms).append("\t\n");
        return sb.toString();
    }
}
