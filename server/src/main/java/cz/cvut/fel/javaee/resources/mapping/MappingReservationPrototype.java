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

package cz.cvut.fel.javaee.resources.mapping;

import cz.cvut.fel.javaee.db.model.ReservationEntity;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Class for mapping DB entity to DTO used for REST response
* @author vodamiro
*/
@XmlRootElement(name="reservation")
public class MappingReservationPrototype {
    
    protected long id;
    protected String created;  // ISO 8601
    protected String password;
    @Min(1) @Max(10)
    protected int seats;
    @Pattern(regexp = "(^payed$|^new$|^invalid$)", message = "Invalid state. Allowed states: payed, new, invalid")
    protected String state;
    protected String url;
    
    /**
     * Default constructor
     */
    public MappingReservationPrototype(){}
    
    /**
     * Fills class attributes from DB entity
     * @param reservationEntity
     * @param ub 
     */
    public MappingReservationPrototype(ReservationEntity reservationEntity, UriBuilder ub){
        if(reservationEntity != null){
            this.id = reservationEntity.getId();
            this.created = reservationEntity.getCreated();
            this.password = reservationEntity.getPassword();
            this.seats = reservationEntity.getSeats();
            this.state = reservationEntity.getState();
            this.url ="/reservation/" + Long.toString(reservationEntity.getId());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
