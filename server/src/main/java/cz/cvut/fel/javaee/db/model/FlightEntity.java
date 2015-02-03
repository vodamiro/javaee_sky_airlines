/*
 * The MIT License (MIT)
 *
 * Copyright (c) <year> <copyright holders>

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cz.cvut.fel.javaee.db.model;

import cz.cvut.fel.javaee.db.DataHolder;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class respresents DB entity flight
 * @author Petr
 */
public class FlightEntity implements Serializable{
    
    private long id;
    private String name; // UNIQUE
    private String dateOfDeparture; // ISO 8601
    private float distance; // in km
    private int seats;
    private float price; // price with string currency i.e. 1500 CZK
    private long from; // destination ID
    private long to; // destination ID

    /**
     * Default constructor
     */
    public FlightEntity() {
        name = "";
        price = 0;
        dateOfDeparture = "";
    }
    
    /**
     * Full constructor
     * @param id Flight id
     * @param name Flight name
     * @param dateOfDeparture Date of departure in ISO-8601
     * @param distance Flight distance in km
     * @param seats Flight seats
     * @param price Flight price per one person
     * @param from_id From destination id
     * @param to_id To destination id
     */
    public FlightEntity(long id, String name, String dateOfDeparture, float distance, int seats, float price, long from_id, long to_id) {
        this.id = id;
        this.name = name;
        this.dateOfDeparture = dateOfDeparture; // ISO 8601 !!!
        this.distance = distance;
        this.seats = seats;
        this.price = price;
        this.from = from_id;
        this.to = to_id;
    }
    
    /**
     * Constructor
     * @param id Flight id
     * @param name Flight name
     * @param dateOfDeparture Date of departure in ISO-8601
     * @param seats Flight seats
     * @param from_id From destination id
     * @param to_id To destination id
     */
    public FlightEntity(long id, String name, String dateOfDeparture, int seats, long from_id, long to_id) {
        this.id = id;
        this.name = name;
        this.dateOfDeparture = dateOfDeparture; // ISO 8601 !!!
        this.seats = seats;
        this.from = from_id;
        this.to = to_id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public float getDistance() {
        return distance;
    }

    public int getSeats() {
        return seats;
    }

    public float getPrice() {
        return price;
    }

    public long getFrom_id() {
        return from;
    }

    public long getTo_id() {
        return to;
    }
   
    public DestinationEntity getFrom(DataHolder dataHolder) {
        return dataHolder.getDestinationById(from);
    }
    
    public DestinationEntity getTo(DataHolder dataHolder) {
        return dataHolder.getDestinationById(to);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setFrom_id(long from_id) {
        this.from = from_id;
    }

    public void setTo_id(long to_id) {
        this.to = to_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    /**
     * Get date in ISO8601 format
     * @return 
     */
    public Date getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm"); //format of ISO8601
        Date date;
        try {
            date = dateFormat.parse(dateOfDeparture);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }
    
    @Override
    public String toString() {
        return "FlightEntity{" + "id=" + id + ", name=" + name + ", dateOfDeparture=" + dateOfDeparture + ", distance=" + distance + ", seats=" + seats + ", price=" + price + ", from=" + from + ", to=" + to + '}';
    }

    private final int PRICE_PER_KM = 1000;
    
    /**
     * Set flight distance and compute (and set) flight price
     * @param distance Distance in km
     */
    public void setDistanceAndPrice(int distance) {
        this.distance = distance;
        this.price = distance * PRICE_PER_KM;
    }
    
}
