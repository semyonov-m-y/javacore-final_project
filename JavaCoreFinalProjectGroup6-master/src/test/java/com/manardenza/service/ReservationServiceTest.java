package com.manardenza.service;

import com.manardenza.TestUtils;
import com.manardenza.dao.ReservationDaoImpl;
import com.manardenza.entity.Reservation;
import com.manardenza.entity.User;
import com.manardenza.login.CurrentUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.manardenza.TestUtils.HOTEL;
import static com.manardenza.TestUtils.RESERVED_FROM;
import static com.manardenza.TestUtils.RESERVED_TO;
import static com.manardenza.TestUtils.ROOM;
import static com.manardenza.TestUtils.USER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @Captor
    ArgumentCaptor<Reservation> reservationCaptor;

    @InjectMocks
    private ReservationService reservationService;
    private final List<Reservation> testReservationList = new ArrayList<>();
    @Mock
    private CurrentUser currentUser;
    @Mock
    private ReservationDaoImpl reservationDao;

    @Before
    public void setUp() {
        Reservation testReservationToDelete = new Reservation(RESERVED_FROM, RESERVED_TO, USER, ROOM, HOTEL);
        Reservation testNotCorrectReservation1 = new Reservation(RESERVED_FROM, RESERVED_TO,
                new User("Some1", "User1"), ROOM, HOTEL);
        Reservation testNotCorrectReservation2 = new Reservation(RESERVED_FROM, RESERVED_TO,
                new User("Some2", "User2"), ROOM, HOTEL);

        testReservationList.add(testReservationToDelete);
        testReservationList.add(testNotCorrectReservation1);
        testReservationList.add(testNotCorrectReservation2);
    }

    @Test
    public void bookRoomCreatedReservation() throws Exception {
        Reservation testReservation = new Reservation(RESERVED_FROM, RESERVED_TO, USER, ROOM, HOTEL);
        reservationService.bookRoom(RESERVED_FROM, RESERVED_TO, ROOM, HOTEL);

        Mockito.verify(reservationDao, Mockito.times(1)).save(reservationCaptor.capture());
        Reservation savedReservation = reservationCaptor.getValue();
        assertEquals(savedReservation.getReservedFrom(), testReservation.getReservedFrom());
        assertEquals(savedReservation.getReservedTo(), testReservation.getReservedTo());
        assertEquals(savedReservation.getReservedRoom(), testReservation.getReservedRoom());
        assertEquals(savedReservation.getReservedHotel(), testReservation.getReservedHotel());
    }

    @Test
    public void cancelReservationDeleteReservation() throws Exception {
        Reservation testReservationToDelete = testReservationList.get(0);
        when(reservationDao.getAll()).thenReturn(testReservationList);

        reservationService.cancelReservation(testReservationToDelete.getId());
        Mockito.verify(reservationDao, Mockito.times(1)).delete(testReservationToDelete);
    }

    @Test
    public void abilityVerifyRoomsReservationBasedOnAlreadyRegisteredAndDates() throws Exception {
        when(reservationDao.getAll()).thenReturn(TestUtils.getTestReservationList());
        assertEquals(TestUtils.getSortedTestRoomsToReturnMap(),
                reservationService.checkRoomReservation(RESERVED_FROM, RESERVED_TO, TestUtils.getSortedTestRoomsMap()));
    }

    @Test
    public void getAllUserReservationsFromReservationDao() throws Exception {
        List<Reservation> testUserReservationList = new ArrayList<>();
        testUserReservationList.add(testReservationList.get(0));
        when(currentUser.getUser()).thenReturn(testReservationList.get(0).getReservedUser());
        when(reservationDao.getAll()).thenReturn(testReservationList);
        assertEquals(testUserReservationList, reservationService.getAllUserReservations());
    }
}