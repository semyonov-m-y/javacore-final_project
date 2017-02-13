package com.manardenza.service;

import com.manardenza.TestUtils;
import com.manardenza.dao.HotelDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceTest {

    @Mock
    private HotelDaoImpl hotelDao;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private HotelService hotelService;

    @Test
    public void findHotelByNameCallsHotelDaoMethod() throws Exception {
        hotelService.findHotelByName(TestUtils.HOTEL_NAME);
        Mockito.verify(hotelDao, Mockito.times(1)).getHotelsByName(TestUtils.HOTEL_NAME);
    }

    @Test
    public void findHotelCityCallsHotelDaoMethod() throws Exception {
        hotelService.findHotelByCity(TestUtils.CITY);
        Mockito.verify(hotelDao, Mockito.times(1)).getHotelsByCity(TestUtils.CITY);
    }

    @Test
    public void getAvailableRoomsCallReservationServiceMethod() throws Exception {
        hotelService.getAvailableRooms(TestUtils.CITY, 2, 300, TestUtils.RESERVED_FROM, TestUtils.RESERVED_TO);
        Mockito.verify(reservationService, Mockito.times(1))
                .checkRoomReservation(TestUtils.RESERVED_FROM, TestUtils.RESERVED_TO,
                hotelService.findPreliminaryRoom(TestUtils.CITY, 2, 300));

    }

    @Test
    public void findPreliminaryRoomCallsHotelDaoMethod() throws Exception {
        hotelService.findPreliminaryRoom(TestUtils.CITY, 2, 300);
        Mockito.verify(hotelDao, Mockito.times(1)).getHotelsByCity(TestUtils.CITY);
    }
}