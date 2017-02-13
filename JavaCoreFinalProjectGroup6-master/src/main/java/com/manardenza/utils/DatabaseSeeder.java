package com.manardenza.utils;

import com.manardenza.dao.HotelDaoImpl;
import com.manardenza.dao.ReservationDaoImpl;
import com.manardenza.dao.UserDaoImpl;
import com.manardenza.entity.Hotel;
import com.manardenza.entity.Reservation;
import com.manardenza.entity.Room;
import com.manardenza.entity.User;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class DatabaseSeeder {

    private static final UserDaoImpl USERDAOIMPL = Injector.getUserDao();
    private static final HotelDaoImpl HOTELDAOIMPL = Injector.getHotelDao();
    private static final ReservationDaoImpl RESERVATIONDAOIMPL = Injector.getReservationDao();
    private static final List<String> citiesNameList = new ArrayList<>();
    private static final List<String> hotelsNamesList = new ArrayList<>();
    private static final List<Hotel> hotelsList = new ArrayList<>();

    private static List<Room> newRoomsList() {
        List<Room> roomsSeederList = new ArrayList<>();
        roomsSeederList.add(new Room("SINGLE ROOM", 1, 200));
        roomsSeederList.add(new Room("DOUBLE ROOM", 2, 300));
        roomsSeederList.add(new Room("KING BEDROOM", 2, 350));
        roomsSeederList.add(new Room("INTERCONNECTING ROOMS", 3, 400));
        roomsSeederList.add(new Room("INTERCONNECTING ROOMS", 4, 450));
        roomsSeederList.add(new Room("DUPLEX", 4, 700));
        roomsSeederList.add(new Room("SUITE ROOM", 6, 1000));
        return roomsSeederList;
    }

    private static List<Reservation> newReservationsList(int index) {
        List<Reservation> reservationList = new ArrayList<>();
        for (int j = 0; j < 12; j++) {
            reservationList.add(new Reservation(new Date(117, j, 10),
                new Date(117, j, 15),
                USERDAOIMPL.getAll().get(index),
                HOTELDAOIMPL.getAll().get(index).getRooms().get(index),
                HOTELDAOIMPL.getAll().get(index)));
        }
        return reservationList;
    }

    public static void seedDatabase() {
        if (!dbFileAlreadySeeded(Injector.getUsersDatabaseFile())) {
            USERDAOIMPL.save(new User("Vasya", "Petrov"));
            USERDAOIMPL.save(new User("Anton", "Chechov"));
            USERDAOIMPL.save(new User("Natalia", "Novikova"));
            USERDAOIMPL.save(new User("Igor", "Sidorov"));
            USERDAOIMPL.save(new User("Vasya", "Ivanov"));
            USERDAOIMPL.save(new User("Kolya", "Pyatkin"));
            USERDAOIMPL.save(new User("Andrey", "Semenov"));
            USERDAOIMPL.save(new User("Katya", "Zagorodnaja"));
            USERDAOIMPL.save(new User("Marina", "Semionova"));
            USERDAOIMPL.save(new User("Jozef", "Pavlik"));
            USERDAOIMPL.save(new User("Dariusz", "Maciewski"));
            USERDAOIMPL.save(new User("Andrzej", "Donica"));
            USERDAOIMPL.save(new User("Sergey", "Godovany"));
            USERDAOIMPL.save(new User("Scolastyka", "Machura"));
            USERDAOIMPL.save(new User("Ann", "Merryvether"));
        }
        if (!dbFileAlreadySeeded(Injector.getHotelsDatabaseFile())) {
            citiesNameList.add("Kiev");
            citiesNameList.add("Odessa");
            citiesNameList.add("Lvov");
            citiesNameList.add("Donetsk");
            citiesNameList.add("Zaporozhye");
            hotelsNamesList.add("Marriott");
            hotelsNamesList.add("Holiday Inn");
            hotelsNamesList.add("Reikartz");

            for (String hotelName : hotelsNamesList) {
                for (String cityName : citiesNameList) {
                    hotelsList.add(new Hotel(hotelName, cityName, newRoomsList()));
                }
            }

            HOTELDAOIMPL.saveAll(hotelsList);
        }
        if (!dbFileAlreadySeeded(Injector.getReservationDatabaseFile())) {
            RESERVATIONDAOIMPL.saveAll(newReservationsList(0));
            RESERVATIONDAOIMPL.saveAll(newReservationsList(1));
            RESERVATIONDAOIMPL.saveAll(newReservationsList(2));
            RESERVATIONDAOIMPL.saveAll(newReservationsList(3));
            RESERVATIONDAOIMPL.saveAll(newReservationsList(4));
            RESERVATIONDAOIMPL.saveAll(newReservationsList(5));
            RESERVATIONDAOIMPL.saveAll(newReservationsList(6));
        }
    }

    public static boolean dbFileAlreadySeeded(File files) {
        return files.exists();
    }
}