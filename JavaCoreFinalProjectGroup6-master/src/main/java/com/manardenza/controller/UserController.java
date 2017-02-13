package com.manardenza.controller;

import com.manardenza.login.CurrentUser;
import com.manardenza.service.UserService;
import org.slf4j.LoggerFactory;


public class UserController {

    private UserService userService;
    private CurrentUser currentUser;
    private org.slf4j.Logger log = LoggerFactory.getLogger(HotelController.class);

    public UserController(UserService userService, CurrentUser currentUser) {
        this.userService = userService;
        this.currentUser = currentUser;
    }

    public void loginUser(String firstName, String lastName) {
        log.info(String.format("Requested login for user %s %s", firstName, lastName));
        userService.loginUser(firstName, lastName);
        log.info(String.format("User %s successfully logged in", currentUser.getUser().getFullName()));
    }

    public void logoutUser() {
        String user = currentUser.getUser().getFullName();
        log.info(String.format("Requested logout for user %s", user));
        userService.logoutUser();
        log.info(String.format("User %s successfully logged out", user));
    }
}
