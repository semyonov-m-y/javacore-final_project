package com.manardenza.console;

import com.manardenza.Main;
import com.manardenza.entity.AbstractObject;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class VisualUserMenu {

    private org.slf4j.Logger log = LoggerFactory.getLogger(VisualUserMenu.class);

    public static void outputSplitLine() {
        System.out.println("**************************************************************");
    }

    public static <E> void printListInConsole(List<String> header, List<E> options) {
        if (header != null) {
            header.forEach(System.out::println);
        }
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%d %s %n", i + 1, options.get(i).toString());
            }
        }
    }

    public static <E extends AbstractObject> void printMapInConsole(Map<String, List<E>> map) {
        int key = 1;
        for (Map.Entry<String, List<E>> entry : map.entrySet()) {
            System.out.print(key++);
            System.out.println(" " + entry.getKey());
            if (entry.getValue().size() == 0) {
                System.out.println("\tNo available rooms found.");
            }
            for (int item = 0; item < entry.getValue().size(); item++) {
                System.out.print("\t" + (item + 1));
                System.out.print(" " + entry.getValue().get(item));
            }
        }
    }


    public String getValidInputFromUser(String message, InputType type) {
        System.out.println(message);
        String input = "";
        try {
            input = Main.getReader().readLine();
            while (!type.getIsValid().test(input)) {
                System.out.println(type.getErrorMessage());
                input = Main.getReader().readLine();
            }
        } catch (IOException e) {
            log.error(String.format("Error reading user input from console"));
        }
        return input;
    }

    public boolean validateIntegerSize(int max, int input) {
        return input >= 1 && input <= max;
    }


}
