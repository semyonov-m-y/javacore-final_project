package com.manardenza.controller;

import com.manardenza.entity.Hotel;
import com.manardenza.entity.Room;
import com.manardenza.login.CurrentUser;
import com.manardenza.service.HotelService;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class HotelController {

    private HotelService hotelService;
    private CurrentUser currentUser;
    private org.slf4j.Logger log = LoggerFactory.getLogger(HotelController.class);

    public HotelController(HotelService hotelService, CurrentUser currentUser) {
        this.hotelService = hotelService;
        this.currentUser = currentUser;
    }

    public List<Hotel> findHotelByName(String hotelName) {
        log.info(String.format("User %s has searched for hotels by name %s", currentUser.getUser().getFullName(),
            hotelName));
        return hotelService.findHotelByName(hotelName);
    }

    public List<Hotel> findHotelByCity(String cityName) {
        log.info(String.format("User %s has searched for hotels by city %s", currentUser.getUser().getFullName(),
            cityName));
        return hotelService.findHotelByCity(cityName);
    }

    public Map<String, List<Room>> getAvailableRooms(String city, int persons, int price,
                                                     Date reservedFrom, Date reservedTo) {
        log.info(String.format("User %s has searched for %d person rooms available from %tD till %tD in %s priced up "
            + "to $d", currentUser.getUser().getFullName(), persons, reservedFrom, reservedTo, city, price));
        return hotelService.getAvailableRooms(city, persons, price, reservedFrom, reservedTo);
    }
}
