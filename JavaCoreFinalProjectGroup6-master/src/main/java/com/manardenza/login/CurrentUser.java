package com.manardenza.login;

import com.manardenza.entity.User;
import lombok.Data;

@Data
public class CurrentUser {

    private static CurrentUser loggedUser;
    private User user;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (loggedUser == null) {
            loggedUser = new CurrentUser();
        }
        return loggedUser;
    }
}
