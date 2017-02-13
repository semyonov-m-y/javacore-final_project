package com.manardenza.controller;

import com.manardenza.entity.Hotel;
import com.manardenza.entity.Reservation;
import com.manardenza.entity.Room;
import com.manardenza.login.CurrentUser;
import com.manardenza.service.ReservationService;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class ReservationController {

    private ReservationService reservationService;
    private CurrentUser currentUser;
    private org.slf4j.Logger log = LoggerFactory.getLogger(HotelController.class);

    public ReservationController(ReservationService reservationService, CurrentUser currentUser) {
        this.reservationService = reservationService;
        this.currentUser = currentUser;
    }

    public long bookRoom(Date reservedFrom, Date reservedTo, Room room, Hotel hotel) {
        log.info(String.format("%s has requested reservation of room %d in hotel %s from %tD till %tD", currentUser
            .getUser().getFullName(), room.getId(), hotel.getName(), reservedFrom, reservedTo));
        long reservationId = reservationService.bookRoom(reservedFrom, reservedTo, room, hotel);
        log.info(String.format("%s has successfully reserved room %d in hotel %s from %tD till %tD", currentUser
            .getUser().getFullName(), room.getId(), hotel.getName(), reservedFrom, reservedTo));
        return reservationId;
    }

    public boolean cancelReservation(long idReservation) {
        log.info(String.format("%s has requested cancellation of reservation %d", currentUser
            .getUser().getFullName(), idReservation));
        boolean wasCancelled = reservationService.cancelReservation(idReservation);
        if (wasCancelled) {
            log.info(String.format("%s has successfully cancelled reservation %d", currentUser
                .getUser().getFullName(), idReservation));
        } else {
            log.info(String.format("Reservation %d cancellation failure", idReservation));
        }
        return wasCancelled;
    }

    //TODO: consider deleting this method if it won't be used in console
    public Map<String, List<Room>> checkRoomReservation(Date reservedFrom, Date reservedTo,
                                                        Map<String, List<Room>> rooms) {
        return reservationService.checkRoomReservation(reservedFrom, reservedTo, rooms);
    }

    public List<Reservation> getAllUserReservations() {
        return reservationService.getAllUserReservations();
    }
}
