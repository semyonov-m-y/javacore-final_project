package com.manardenza.console;

import com.manardenza.controller.HotelController;
import com.manardenza.controller.ReservationController;
import com.manardenza.controller.UserController;
import com.manardenza.entity.Hotel;
import com.manardenza.entity.Reservation;
import com.manardenza.entity.Room;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ContentsUserMenu {

    public static final String ERROR_INCORRECT_MENU_ITEM_SELECTED = "ERROR: Incorrect menu item selected \n";
    public static final String ERROR_INVALID_NAME_FORMAT = "ERROR: Invalid name format!\n";
    private static org.slf4j.Logger log = LoggerFactory.getLogger(ContentsUserMenu.class);


    private HotelController hotelController;
    private ReservationController reservationController;
    private UserController userController;
    private VisualUserMenu visualUserMenu;

    public ContentsUserMenu(HotelController hotelController,
                            ReservationController reservationController, UserController userController,
                            VisualUserMenu visualUserMenu) {
        this.hotelController = hotelController;
        this.reservationController = reservationController;
        this.userController = userController;
        this.visualUserMenu = visualUserMenu;
    }

    public void mainMenu() {
        VisualUserMenu.printListInConsole(ListMenu.getSiteHeader(), null);
        VisualUserMenu.printListInConsole(ListMenu.getAuthorizationHeader(), ListMenu.getAuthorizationMenu());
        Integer action;
        do {
            action = Integer.parseInt(visualUserMenu.getValidInputFromUser("Choose action: ", InputType.INTEGER));
            switch (action) {
                case (1):
                    registrationUserMenu();
                    break;
                case (2):
                    System.out.println("\tBye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println(ERROR_INCORRECT_MENU_ITEM_SELECTED);
                    break;
            }
        } while (!visualUserMenu.validateIntegerSize(ListMenu.getAuthorizationMenu().size(), action));
    }

    private void registrationUserMenu() {
        String firstName = "";
        String lastName = "";
        boolean repeat = true;
        do {
            firstName = visualUserMenu.getValidInputFromUser("Enter your first name: ", InputType.STRING);
            if (firstName.contains(" ") || firstName.isEmpty()) {
                System.out.println(ERROR_INVALID_NAME_FORMAT);
                continue;
            }
            lastName = visualUserMenu.getValidInputFromUser("Enter your last name: ", InputType.STRING);
            if (lastName.contains(" ") || lastName.isEmpty()) {
                System.out.println(ERROR_INVALID_NAME_FORMAT);
                continue;
            }
            repeat = false;
        } while (repeat);
        userController.loginUser(firstName, lastName);
        System.out.println(firstName + ", You have successfully logged in!\n\n\t Welcome to our site!\n");
    }

    public void serviceMenu() {
        while (true) {
            VisualUserMenu.printListInConsole(ListMenu.getServiceHeader(), ListMenu.getServiceMenu());
            Integer action;
            action = Integer.parseInt(visualUserMenu.getValidInputFromUser("Choose action: ", InputType.INTEGER));
            switch (action) {
                case (1):
                    VisualUserMenu.outputSplitLine();
                    findHotelByNameMenu();
                    break;
                case (2):
                    VisualUserMenu.outputSplitLine();
                    VisualUserMenu.printListInConsole(null, findHotelByCityMenu());
                    break;
                case (3):
                    VisualUserMenu.outputSplitLine();
                    findRoomMenu();
                    break;
                case (4):
                    VisualUserMenu.outputSplitLine();
                    getAllUserReservationsMenu();
                    break;
                case (5):
                    VisualUserMenu.outputSplitLine();
                    userController.logoutUser();
                    return;
                case (6):
                    VisualUserMenu.outputSplitLine();
                    System.out.println("\tThank you for using our service.");
                    System.exit(0);
                    break;
                default:
                    VisualUserMenu.outputSplitLine();
                    System.out.println(ERROR_INCORRECT_MENU_ITEM_SELECTED);

            }
        }
    }

    private List<Hotel> findHotelByNameMenu() {
        while (true) {
            String hotelName = visualUserMenu.getValidInputFromUser("Enter hotel name (enter \"0\" to return back to "
                + "menu): ", InputType.STRING);
            if (hotelName.equals("0")) {
                return null;
            }
            VisualUserMenu.outputSplitLine();
            List<Hotel> hotelList = hotelController.findHotelByName(hotelName);
            if (hotelList.isEmpty()) {
                System.out.println("Hotel " + hotelName + " not found.");
            } else {
                System.out.println("The list of found hotels by name: " + hotelName);
                VisualUserMenu.printListInConsole(null, hotelList);
            }
            return hotelList;
        }
    }

    private List<Hotel> findHotelByCityMenu() {
        while (true) {
            String city = visualUserMenu.getValidInputFromUser("Enter city (enter \"0\" to return back to menu): ",
                InputType
                    .STRING);
            if (city.equals("0")) {
                return null;
            }
            VisualUserMenu.outputSplitLine();
            List<Hotel> hotelList = hotelController.findHotelByCity(city);
            if (hotelList.isEmpty()) {
                System.out.println("Hotel in " + city + " not found.");
            } else {
                System.out.println("The list of found hotels in: " + city);
            }
            return hotelList;
        }
    }

    private Map<String, List<Room>> findRoomMenu() {
        String city = visualUserMenu.getValidInputFromUser("Enter city: ", InputType.STRING);
        Integer persons = Integer.parseInt(visualUserMenu.getValidInputFromUser("Enter the number of persons: ",
            InputType.INTEGER));
        Integer price = Integer.parseInt(visualUserMenu.getValidInputFromUser("Enter maximum price: ", InputType
            .INTEGER));
        String reservedFrom;
        String reservedTo;
        Date arrival;
        Date departure;
        for ( ; ; ) {
            reservedFrom = visualUserMenu.getValidInputFromUser("Enter arrival date in the format \"dd.mm.yyyy\": ",

                InputType.DATE);
            reservedTo = visualUserMenu.getValidInputFromUser("Enter the departure date in the format \"dd.mm"
                + ".yyyy\": "
                + "", InputType.DATE);
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            arrival = new Date(0, 0, 0);
            departure = new Date(0, 0, 1);
            try {
                arrival = df.parse(reservedFrom);
                departure = df.parse(reservedTo);
            } catch (ParseException e) {
                log.error("Error parsing input date to Date format", e);
            }
            if (arrival.after(departure) || arrival.before(new Date())) {
                System.out.println("Arrival date must exceed current date but must come before departure!");
                continue;
            }
            break;
        }
        Map<String, List<Room>> foundRooms = hotelController.getAvailableRooms(city, persons, price, arrival,
            departure);
        if (!foundRooms.isEmpty()) {
            System.out.println("The list of rooms found:\n ");
            VisualUserMenu.printMapInConsole(foundRooms);
            VisualUserMenu.outputSplitLine();
            bookRoomMenu(arrival, departure, foundRooms);
        } else {
            VisualUserMenu.outputSplitLine();
            System.out.println("No available rooms found!");
        }
        return foundRooms;
    }

    private void bookRoomMenu(Date reservedFrom, Date reservedTo, Map<String, List<Room>> foundRooms) {
        List<String> foundHotelNames = new ArrayList<>();
        foundRooms.forEach((key, value) -> foundHotelNames.add(key));
        long reserveId = 0L;
        System.out.println("Do you want to make a reservation?");
        VisualUserMenu.printListInConsole(ListMenu.getBookingHeader(), ListMenu.getBookingMenu());
        Integer action;
        do {
            action = Integer.parseInt(visualUserMenu.getValidInputFromUser("Choose action: ", InputType
                .INTEGER));
            switch (action) {
                case (1):
                    Integer hotelIndex;
                    Integer roomIndex;
                    do {
                        hotelIndex = Integer.parseInt(visualUserMenu.getValidInputFromUser("Enter the number of "
                            + "hotel: ", InputType.INTEGER));
                    } while (!visualUserMenu.validateIntegerSize(foundHotelNames.size(), hotelIndex));
                    String selectedHotelName = foundHotelNames.get(hotelIndex - 1);
                    do {
                        roomIndex = Integer.parseInt(visualUserMenu.getValidInputFromUser("Enter the number of "
                           + "room: ", InputType.INTEGER));
                    } while (!visualUserMenu.validateIntegerSize(foundRooms.get(selectedHotelName).size(), roomIndex));
                    Hotel selectedHotel = hotelController.findHotelByName(selectedHotelName).get(0);
                    Room selectedRoom = foundRooms.get(selectedHotelName).get(roomIndex - 1);
                    reserveId = reservationController.bookRoom(reservedFrom,
                        reservedTo, selectedRoom, selectedHotel);

                    System.out.println("Room reserved successfully!\n You reservation ID: " + reserveId);
                    break;
                default:
                    return;
            }
        } while (!visualUserMenu.validateIntegerSize(ListMenu.getBookingMenu().size(), action));
    }

    private void getAllUserReservationsMenu() {
        List<Reservation> reservations = reservationController.getAllUserReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
            return;
        }
        VisualUserMenu.printListInConsole(null, reservations);
        cancelReservationMenu(reservations);
    }

    private void cancelReservationMenu(List<Reservation> reservations) {
        Integer reservationIndex;
        do {
            reservationIndex = Integer.parseInt(visualUserMenu.getValidInputFromUser("Enter your reservation number "
                + "(enter "
                + "\"0\" to return back to menu): ", InputType
                .INTEGER));
            if (reservationIndex == 0) {
                return;
            }
        } while (!visualUserMenu.validateIntegerSize(reservations.size(), reservationIndex));
        reservationController.cancelReservation(reservations.get(reservationIndex - 1).getId());
        System.out.println("Reservation successfully cancelled");
    }
}
