package com.manardenza.dao;

import com.manardenza.controller.HotelController;
import com.manardenza.entity.AbstractObject;
import com.manardenza.exceptions.DbFileException;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<T extends AbstractObject> {

    protected List<T> database = new ArrayList<>();
    private File databaseFile;
    private org.slf4j.Logger log = LoggerFactory.getLogger(HotelController.class);

    protected AbstractDao(File databaseFile) {
        this.databaseFile = databaseFile;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(databaseFile))) {
            oos.writeObject(database);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DbFileException("Error while saving database file");
        }
    }

    private void readFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(databaseFile))) {
            database = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new DbFileException("Error while reading database file");
        }
    }

    public T save(T obj) {
        database.add(obj);
        saveToFile();
        return obj;
    }

    public boolean delete(T obj) {
        boolean result = database.remove(obj);
        if (result) {
            saveToFile();
        }
        return result;
    }

    public boolean saveAll(List<T> list) {
        boolean result = database.addAll(list);
        if (result) {
            saveToFile();
        }
        return result;
    }

    public List<T> getAll() {
        readFromFile();
        return database;
    }
}