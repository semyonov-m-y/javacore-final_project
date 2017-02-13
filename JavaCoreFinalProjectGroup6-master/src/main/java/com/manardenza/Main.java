package com.manardenza;

import com.manardenza.utils.DatabaseSeeder;
import com.manardenza.utils.Injector;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    @Getter
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        DatabaseSeeder.seedDatabase();
        Injector.getConsoleMain().consoleMain();
        reader.close();
    }
}
