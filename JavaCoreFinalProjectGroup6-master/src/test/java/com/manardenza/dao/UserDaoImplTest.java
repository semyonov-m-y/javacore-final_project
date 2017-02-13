package com.manardenza.dao;

import com.manardenza.TestUtils;
import com.manardenza.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserDaoImpl.class)
public class UserDaoImplTest {

    @Mock
    private File databaseFile;
    @InjectMocks
    private UserDaoImpl userDao;
    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "User";
    private ObjectInputStream oisMock = PowerMockito.mock(ObjectInputStream.class);
    private ObjectOutputStream oosMock = PowerMockito.mock(ObjectOutputStream.class);
    private FileInputStream inputStreamMock = PowerMockito.mock(FileInputStream.class);
    private FileOutputStream outputStreamMock = PowerMockito.mock(FileOutputStream.class);
    List<User> testUsersList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        whenNew(ObjectOutputStream.class).withArguments(Matchers.any()).thenReturn(oosMock);
        whenNew(ObjectInputStream.class).withArguments(Matchers.any()).thenReturn(oisMock);
        whenNew(FileInputStream.class).withParameterTypes(String.class).withArguments(Matchers.any()).thenReturn(inputStreamMock);
        whenNew(FileOutputStream.class).withParameterTypes(String.class).withArguments(Matchers.any()).thenReturn(outputStreamMock);

        testUsersList.add(new User(FIRST_NAME, LAST_NAME));
        testUsersList.add(new User(FIRST_NAME, "Not Correct Last Name"));
        testUsersList.add(new User("Not Correct First Name", LAST_NAME));
        testUsersList.add(new User("Not Correct First Name", "Not Correct Last Name"));

        userDao.database = testUsersList;
    }

    @Test
    public void getUserByNameCorrectUserReturn() throws Exception {
        PowerMockito.when(oisMock.readObject()).thenReturn(testUsersList);
        User findUser = userDao.getUserByName(FIRST_NAME, LAST_NAME);
        User expectedUser = testUsersList.get(0);
        assertEquals(expectedUser, findUser);
    }

    @Test
    public void newUserSavingToDatabaseAvailability() throws Exception {
        userDao.save(TestUtils.USER);
        assertEquals(TestUtils.USER, userDao.save(TestUtils.USER));
        Mockito.verify(oosMock, Mockito.times(2)).writeObject(userDao.database);
    }

    @Test
    public void availabilityToDeleteRegisteredObjectFromDatabase() throws Exception {
        User testUserToDelete = new User("Test", "User");
        assertTrue(userDao.delete(testUserToDelete));
        Mockito.verify(oosMock, Mockito.times(1)).writeObject(userDao.database);
    }

    @Test
    public void getAllUsersListFromDatabaseAvailability() throws Exception {
        when(oisMock.readObject()).thenReturn(testUsersList);
        assertEquals(testUsersList, userDao.getAll());
    }

    @Test
    public void databaseAvailabilitySavingUsersLists() throws Exception {
        userDao.saveAll(testUsersList);
        assertTrue(userDao.saveAll(testUsersList));
        Mockito.verify(oosMock, Mockito.times(2)).writeObject(userDao.database);
    }
}