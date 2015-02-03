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

import cz.cvut.fel.javaee.db.model.FlightEntity;
import javax.ejb.Stateless;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Class for mapping DB entity to DTO used for REST response
* @author vodamiro
*/
@XmlRootElement(name="flight")
@Stateless
public class MappingFlightPrototype {
   
    protected Long id;
    @NotNull @Size(min=1, max=25)
    protected String name; // UNIQUE
    protected String dateOfDeparture;
    @Min(0)
    protected Float distance; 
    @Min(1) @Max(1000) 
    protected Integer seats;
    @NotNull 
    protected String price;
    protected String url;
    
    /**
     * Default constructor
     */
    public MappingFlightPrototype(){}
    
    /**
     * Fills class attributes from DB entity
     * @param flightEntity
     * @param ub
     * @param currency
     * @param currencyRate 
     */
    public MappingFlightPrototype(FlightEntity flightEntity, UriBuilder ub, String currency, float currencyRate){
        if(flightEntity != null){
            this.id = flightEntity.getId();
            this.name = flightEntity.getName();
            this.dateOfDeparture = flightEntity.getDateOfDeparture();
            this.distance = flightEntity.getDistance();
            this.seats = flightEntity.getSeats();
            this.price = Float.toString(flightEntity.getPrice()*currencyRate) + " " + currency;
            this.url ="/flight/" + Long.toString(flightEntity.getId());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(String dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
   
}
