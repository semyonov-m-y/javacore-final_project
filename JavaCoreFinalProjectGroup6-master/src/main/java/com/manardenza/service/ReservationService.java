package com.manardenza.service;

import com.manardenza.dao.ReservationDaoImpl;
import com.manardenza.entity.Hotel;
import com.manardenza.entity.Reservation;
import com.manardenza.entity.Room;
import com.manardenza.login.CurrentUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationService {

    private ReservationDaoImpl reservationDao;
    private CurrentUser currentUser;

    public ReservationService(ReservationDaoImpl reservationDao, CurrentUser currentUser) {
        this.reservationDao = reservationDao;
        this.currentUser = currentUser;
    }

    public Long bookRoom(Date reservedFrom, Date reservedTo, Room room, Hotel hotel) {
        Reservation newReservation = new Reservation(reservedFrom, reservedTo, currentUser.getUser(), room, hotel);
        reservationDao.save(newReservation);

        return newReservation.getId();
    }

    public boolean cancelReservation(long idReservation) {
        return reservationDao.delete(reservationDao.getAll().stream()
                .filter(p -> p.getId() == idReservation)
                .findFirst()
                .orElse(null));
    }

    public Map<String, List<Room>> checkRoomReservation(Date reservedFrom, Date reservedTo,
                                                        Map<String, List<Room>> rooms) {
        for (Map.Entry<String, List<Room>> entry : rooms.entrySet()) {
            entry.setValue(selectAvailableRooms(reservedFrom, reservedTo, entry.getValue()));
        }
        return rooms;
    }

    private List<Room> selectAvailableRooms(Date reservedFrom, Date reservedTo, List<Room> rooms) {
        List<Room> availableRooms = new ArrayList<>(rooms);
        reservationDao.getAll()
                .forEach(reservation -> availableRooms.removeIf(room -> reservation.getReservedRoom().equals(room)
                        && reservation.overlaps(reservedFrom, reservedTo)));
        return availableRooms;
    }

    public List<Reservation> getAllUserReservations() {
        return reservationDao.getAll().stream()
                .filter(reservation -> reservation.getReservedUser().equals(currentUser.getUser()))
                .collect(Collectors.toList());
    }
}
