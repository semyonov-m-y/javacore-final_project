package com.manardenza.service;

import com.manardenza.dao.UserDaoImpl;
import com.manardenza.entity.User;
import com.manardenza.login.CurrentUser;

public class UserService {

    private UserDaoImpl userDao;
    private CurrentUser currentUser;

    public UserService(UserDaoImpl userDao, CurrentUser currentUser) {
        this.userDao = userDao;
        this.currentUser = currentUser;
    }

    public void loginUser(String firstName, String lastName) {
        User user = userDao.getUserByName(firstName, lastName);
        if (user == null) {
            user = registerNewUser(firstName, lastName);
        }
        currentUser.setUser(user);
    }

    private User registerNewUser(String firstName, String lastName) {
        return userDao.save(new User(firstName, lastName));
    }

    public void logoutUser() {
        currentUser.setUser(null);
    }
}
