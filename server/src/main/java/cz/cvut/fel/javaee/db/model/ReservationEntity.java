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
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;

/**
 * Class that simulates DB entity reservation.
 * @author Petr
 */

public class ReservationEntity implements Serializable {
  
    private static long lastId = 0;//for autoincrementation
    
    private long id;
    private long flight_id;
    private String created;  // ISO 8601
    private String password;
    @Min(1)
    private int seats;
    private String state;

    /**
     * Empty constructor of ReservationEntity
     */
    public ReservationEntity() {
        this.created = "";
        this.password = "";
        this.state = "";
    }

    /**
     * Constructor
     * There is autoincrement for id
     * If parameter id is higher then last used id(lastId), counter of new id continues from value parameter id
     * If parameter id is lower then last used id(lastId), counter of new id continues from value of last used id(lastID)
     * @param id Reservation id
     * @param flight_id Flight id
     * @param seats Requested seats count
     */
    public ReservationEntity(long id, long flight_id, int seats) {
        this.id = id;
        this.flight_id = flight_id;
        this.created = getTime();
        this.password = generatePassword();
        this.seats = seats;
        this.state = "new";
    }
    
    public ReservationEntity(long id, String state) {
        this.id = id;
        this.state = state;
    }
    
    /**
     * Get time in ISO-8601 format 
     * @return ISO-8601 datetime
     */
    private String getTime(){
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .format(GregorianCalendar.getInstance()
                        .getTime()));
    }
    
    private String generatePassword(){   
        return (new BigInteger(60, new SecureRandom()).toString(32));
    }

    public long getId() {
        return id;
    }

    public long getFlight_id() {
        return flight_id;
    }

    public String getCreated() {
        return created;
    }

    public String getPassword() {
        return password;
    }

    public int getSeats() {
        return seats;
    }

    public String getState() {
        return state;
    }

    /**
     * Return flight entity asociated with flight id for this reservation
     * @param dataHolder
     * @return 
     */
    public FlightEntity getFlight(final DataHolder dataHolder) {
        return dataHolder.getFlightById(flight_id);
    }

    /**
     * Set flight id that belongs to this reservation
     * @param flight_id 
     */
    public void setFlight_id(long flight_id) {
        this.flight_id = flight_id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean validate() {
        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ReservationEntity>> constraintViolations = validator.validate(this);
        if (constraintViolations.isEmpty()==false) {
            for (ConstraintViolation violation: constraintViolations) {
                  Logger.getLogger("Validation").log(Level.INFO,  violation.getMessage());
            }

            return false;
        }

        return true;
    }
     
}
