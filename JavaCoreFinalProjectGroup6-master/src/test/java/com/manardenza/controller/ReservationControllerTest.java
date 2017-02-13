package com.manardenza.controller;

import com.manardenza.login.CurrentUser;
import com.manardenza.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.manardenza.TestUtils.HOTEL;
import static com.manardenza.TestUtils.RESERVED_FROM;
import static com.manardenza.TestUtils.RESERVED_TO;
import static com.manardenza.TestUtils.ROOM;
import static com.manardenza.TestUtils.ROOMS_MAP;
import static com.manardenza.TestUtils.USER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;
    @Mock
    private CurrentUser currentUser;
    @InjectMocks
    private ReservationController reservationController;

    @Before
    public void setUp() {
        when(currentUser.getUser()).thenReturn(USER);
    }

    @Test
    public void bookRoomCallsServiceMethod() throws Exception {
        reservationController.bookRoom(RESERVED_FROM, RESERVED_TO, ROOM, HOTEL);
        verify(reservationService, times(1)).bookRoom(RESERVED_FROM, RESERVED_TO, ROOM, HOTEL);
    }

    @Test
    public void cancelReservationCallsServiceMethod() throws Exception {
        long id = 0;
        reservationController.cancelReservation(id);
        verify(reservationService, times(1)).cancelReservation(id);
    }

    @Test
    public void checkRoomReservationCallsServiceMethod() throws Exception {
        reservationController.checkRoomReservation(RESERVED_FROM, RESERVED_TO, ROOMS_MAP);
        verify(reservationService, times(1)).checkRoomReservation(RESERVED_FROM, RESERVED_TO, ROOMS_MAP);
    }

    @Test
    public void GetAllUserReservationsCallsServiceMethod() throws Exception {
        reservationController.getAllUserReservations();
        verify(reservationService, times(1)).getAllUserReservations();
    }
}