package com.manardenza.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public abstract class AbstractObject implements Serializable{

    private long id;

    protected AbstractObject() {
        id = generatePositiveRandId();
    }

    private static long generatePositiveRandId() {
        long id = UUID.randomUUID().getMostSignificantBits();
        if (id < 0) {
            id = generatePositiveRandId();
        }
        return id;
    }
}