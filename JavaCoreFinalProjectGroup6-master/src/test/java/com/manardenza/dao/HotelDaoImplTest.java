package com.manardenza.dao;

import com.manardenza.TestUtils;
import com.manardenza.entity.Hotel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HotelDaoImpl.class)
public class HotelDaoImplTest {

    @Mock
    private File databaseFile;
    @InjectMocks
    private HotelDaoImpl hotelDao;
    private ObjectInputStream oisMock = PowerMockito.mock(ObjectInputStream.class);
    private ObjectOutputStream oosMock = PowerMockito.mock(ObjectOutputStream.class);
    private FileInputStream inputStreamMock = PowerMockito.mock(FileInputStream.class);
    private FileOutputStream outputStreamMock = PowerMockito.mock(FileOutputStream.class);
    private List<Hotel> testHotelsList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        whenNew(ObjectOutputStream.class).withArguments(Matchers.any()).thenReturn(oosMock);
        whenNew(ObjectInputStream.class).withArguments(Matchers.any()).thenReturn(oisMock);
        whenNew(FileInputStream.class).withParameterTypes(String.class).withArguments(Matchers.any()).thenReturn(inputStreamMock);
        whenNew(FileOutputStream.class).withParameterTypes(String.class).withArguments(Matchers.any()).thenReturn(outputStreamMock);

        testHotelsList.add(new Hotel(TestUtils.HOTEL_NAME, "Not Correct City", TestUtils.SORTED_TEST_ROOMS_LIST));
        testHotelsList.add(new Hotel(TestUtils.HOTEL_NAME, "Not Correct City", TestUtils.SORTED_TEST_ROOMS_LIST));
        testHotelsList.add(new Hotel("Not Correct Name", TestUtils.CITY, TestUtils.SORTED_TEST_ROOMS_LIST));
        testHotelsList.add(new Hotel("Not Correct Name", TestUtils.CITY, TestUtils.SORTED_TEST_ROOMS_LIST));
    }

    @Test
    public void getHotelsByNameCorrectHotelsListReturn() throws Exception {
        List<Hotel> expectedHotels = new ArrayList<>();
        expectedHotels.add(testHotelsList.get(0));
        expectedHotels.add(testHotelsList.get(1));
        PowerMockito.when(oisMock.readObject()).thenReturn(testHotelsList);
        List<Hotel> foundHotels = hotelDao.getHotelsByName(TestUtils.HOTEL_NAME);
        assertEquals(expectedHotels, foundHotels);
    }

    @Test
    public void getHotelsByCityCorrectHotelsListReturn() throws Exception {
        List<Hotel> expectedHotels = new ArrayList<>();
        expectedHotels.add(testHotelsList.get(2));
        expectedHotels.add(testHotelsList.get(3));
        PowerMockito.when(oisMock.readObject()).thenReturn(testHotelsList);
        List<Hotel> foundHotels = hotelDao.getHotelsByCity(TestUtils.CITY);
        assertEquals(expectedHotels, foundHotels);
    }
}