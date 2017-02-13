package com.manardenza.dao;

import com.manardenza.entity.Reservation;

import java.io.File;

public class ReservationDaoImpl extends AbstractDao<Reservation> {

    public ReservationDaoImpl(File databaseFile) {
        super(databaseFile);
    }
}
