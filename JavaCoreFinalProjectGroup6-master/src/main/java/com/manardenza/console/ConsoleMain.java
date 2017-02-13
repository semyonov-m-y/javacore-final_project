package com.manardenza.console;

public class ConsoleMain {

    private ContentsUserMenu contentsUserMenu;

    public ConsoleMain(ContentsUserMenu contentsUserMenu) {
        this.contentsUserMenu = contentsUserMenu;
    }

    public void consoleMain() {
        ListMenu.makeMenus();
        while (true) {
            contentsUserMenu.mainMenu();
            contentsUserMenu.serviceMenu();
        }
    }
}