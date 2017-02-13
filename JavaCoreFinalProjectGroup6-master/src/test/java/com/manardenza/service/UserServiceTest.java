package com.manardenza.service;

import com.manardenza.dao.UserDaoImpl;
import com.manardenza.entity.User;
import com.manardenza.login.CurrentUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "User";

    @Mock
    private CurrentUser currentUser;
    @Mock
    private UserDaoImpl userDao;
    @InjectMocks
    private UserService userService;
    private User user = new User(FIRST_NAME, LAST_NAME);

    @Before
    public void setUp() {
        when(userDao.save(user)).thenReturn(user);
    }

    @Test
    public void ifUserNotRegisteredNewUserIsCreatedAndLoggedIn() throws Exception {
        userService.loginUser(FIRST_NAME, LAST_NAME);
        Mockito.verify(userDao, Mockito.times(1)).save(user);
        Mockito.verify(currentUser, Mockito.times(1)).setUser(user);
    }

    @Test
    public void ifUserRegisteredUserIsLoggedIn() throws Exception {
        when(userDao.getUserByName(FIRST_NAME,LAST_NAME)).thenReturn(user);
        userService.loginUser(FIRST_NAME, LAST_NAME);
        Mockito.verify(userDao, Mockito.times(0)).save(user);
        Mockito.verify(currentUser, Mockito.times(1)).setUser(user);
    }

    @Test
    public void logoutUserFromSystemCurrentUserSetNull() throws Exception {
        userService.logoutUser();
        Mockito.verify(currentUser, Mockito.times(1)).setUser(null);
    }
}