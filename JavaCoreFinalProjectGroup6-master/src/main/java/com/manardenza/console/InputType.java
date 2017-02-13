package com.manardenza.console;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Predicate;

public enum InputType {
    STRING(input -> true, ""),
    INTEGER(input -> StringUtils.isNumeric(input), "Error! Please enter a number"),
    DATE(input -> {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        try {
            df.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }, "You need to enter date in format dd.mm.yyyy");

    @Getter
    private String errorMessage;
    @Getter
    private Predicate<String> isValid;

    InputType(Predicate<String> value, String errorMessage) {
        this.errorMessage = errorMessage;
        this.isValid = value;
    }
}
