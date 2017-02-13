package com.manardenza.utils;

import com.manardenza.console.ConsoleMain;
import com.manardenza.console.ContentsUserMenu;
import com.manardenza.console.VisualUserMenu;
import com.manardenza.controller.HotelController;
import com.manardenza.controller.ReservationController;
import com.manardenza.controller.UserController;
import com.manardenza.dao.HotelDaoImpl;
import com.manardenza.dao.ReservationDaoImpl;
import com.manardenza.dao.UserDaoImpl;
import com.manardenza.login.CurrentUser;
import com.manardenza.service.HotelService;
import com.manardenza.service.ReservationService;
import com.manardenza.service.UserService;
import lombok.Getter;

import java.io.File;

public class Injector {

    private static CurrentUser currentUser = CurrentUser.getInstance();
    @Getter
    private static File hotelsDatabaseFile = new File("src/main/resources/databaseFiles/databaseHotelsFile.bin");
    @Getter
    private static File usersDatabaseFile = new File("src/main/resources/databaseFiles/databaseUsersFile.bin");
    @Getter
    private static File reservationDatabaseFile = new File("src/main/resources/databaseFiles/databaseReservationsFile.bin");
    @Getter
    private static HotelDaoImpl hotelDao = new HotelDaoImpl(hotelsDatabaseFile);
    @Getter
    private static UserDaoImpl userDao = new UserDaoImpl(usersDatabaseFile);
    @Getter
    private static ReservationDaoImpl reservationDao = new ReservationDaoImpl(reservationDatabaseFile);
    @Getter
    private static UserService userService = new UserService(userDao, currentUser);
    private static ReservationService reservationService = new ReservationService(reservationDao, currentUser);
    private static HotelService hotelService = new HotelService(hotelDao, reservationService);
    @Getter
    private static HotelController hotelController = new HotelController(hotelService, currentUser);
    @Getter
    private static UserController userController = new UserController(userService, currentUser);
    @Getter
    private static ReservationController reservationController = new ReservationController(reservationService,
        currentUser);
    @Getter
    private static VisualUserMenu visualUserMenu = new VisualUserMenu();
    @Getter
    private static ContentsUserMenu contentsUserMenu = new ContentsUserMenu(hotelController, reservationController,
        userController, visualUserMenu);
    @Getter
    private static ConsoleMain consoleMain = new ConsoleMain(contentsUserMenu);

    private Injector() {
    }

    static {
        hotelsDatabaseFile.getParentFile().mkdirs();
        usersDatabaseFile.getParentFile().mkdirs();
        reservationDatabaseFile.getParentFile().mkdirs();
    }
}
