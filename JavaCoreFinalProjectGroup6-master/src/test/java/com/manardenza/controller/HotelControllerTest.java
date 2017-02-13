package com.manardenza.controller;

import com.manardenza.TestUtils;
import com.manardenza.login.CurrentUser;
import com.manardenza.service.HotelService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.manardenza.TestUtils.USER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HotelControllerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private HotelService hotelService;
    @Mock
    private CurrentUser currentUser;
    @InjectMocks
    private HotelController hotelController;

    @Before
    public void setUp() {
        when(currentUser.getUser()).thenReturn(USER);
    }

    @Test
    public void findHotelByNameCallsServiceMethod() throws Exception {
        hotelController.findHotelByName(TestUtils.HOTEL_NAME);
        verify(hotelService, times(1)).findHotelByName(TestUtils.HOTEL_NAME);
    }

    @Test
    public void findHotelByCityCallsServiceMethod() throws Exception {
        hotelController.findHotelByCity(TestUtils.CITY);
        verify(hotelService, times(1)).findHotelByCity(TestUtils.CITY);
    }

    @Test
    public void getAvailableRoomsCallsServiceMethod() throws Exception {
        int persons = 2;
        int price = 300;
        hotelController.getAvailableRooms(TestUtils.CITY, persons, price, TestUtils.RESERVED_FROM, TestUtils
            .RESERVED_TO);
        verify(hotelService, times(1)).getAvailableRooms(TestUtils.CITY, persons, price, TestUtils.RESERVED_FROM,
            TestUtils.RESERVED_TO);
    }
}