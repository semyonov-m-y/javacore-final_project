package com.manardenza.console;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ListMenu {

    @Getter
    private static List<String> siteHeader = new ArrayList<>();
    @Getter
    private static List<String> authorizationHeader = new ArrayList<>();
    @Getter
    private static List<String> authorizationMenu = new ArrayList<>();
    @Getter
    private static List<String> serviceHeader = new ArrayList<>();
    @Getter
    private static List<String> serviceMenu = new ArrayList<>();
    @Getter
    private static List<String> bookingHeader = new ArrayList<>();
    @Getter
    private static List<String> bookingMenu = new ArrayList<>();
    @Getter
    private static List<String> findRoomHeader = new ArrayList<>();
    @Getter
    private static List<String> findRoomMenu = new ArrayList<>();

    private ListMenu() {
    }

    public static void makeMenus() {
        siteHeader.add("**************************************************************");
        siteHeader.add("\t\tWelcome to manardenza.com");
        siteHeader.add("**************************************************************");
        siteHeader.add(" ");

        authorizationHeader.add("\n\tAuthorization");
        authorizationMenu.add("Login");
        authorizationMenu.add("Exit");

        serviceHeader.add("**************************************************************");
        serviceHeader.add("\n\tService menu");
        serviceMenu.add("Search for hotels by name");
        serviceMenu.add("Search for hotels by city");
        serviceMenu.add("Search for rooms");
        serviceMenu.add("Cancel reservation");
        serviceMenu.add("Log out");
        serviceMenu.add("Exit");

        bookingHeader.add("**************************************************************");
        bookingMenu.add("Yes");
        bookingMenu.add("No");

        findRoomHeader.add("**************************************************************");
        findRoomMenu.add("Enter city name: ");
        findRoomMenu.add("Enter the number of persons: ");
        findRoomMenu.add("Enter maximum price rate: ");
        findRoomMenu.add("Enter arrival date in the format dd.mm.yyyy: ");
        findRoomMenu.add("Enter the departure date in the format dd.mm.yyyy: ");
    }
}