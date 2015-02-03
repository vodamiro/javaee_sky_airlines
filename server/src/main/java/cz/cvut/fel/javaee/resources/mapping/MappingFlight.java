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

import cz.cvut.fel.javaee.db.DataHolder;
import cz.cvut.fel.javaee.db.model.FlightEntity;
import cz.cvut.fel.javaee.exceptions.BadRequestException;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Class for mapping DB entity to DTO used for REST response
* @author vodamiro
*/
@XmlRootElement(name="flight")
@RequestScoped
public class MappingFlight extends MappingFlightPrototype{
    
    private MappingDestination from; 
    private MappingDestination to;
    
    /**
     * Default constructor
     */
    public MappingFlight(){}
    
    /**
     * Fills class attributes from DB entity
     * @param dataHolder
     * @param flightEntity
     * @param ub
     * @param currency
     * @param currencyRate 
     */
    public MappingFlight(final DataHolder dataHolder, FlightEntity flightEntity, UriBuilder ub, String currency, float currencyRate){
        super(flightEntity, ub, currency, currencyRate);
        if(flightEntity != null){
            try {
                this.from = new MappingDestination(dataHolder.getDestinationById(flightEntity.getFrom_id()), ub); // TODO: Check ub !!!
            } catch (NullPointerException e) {
                throw new BadRequestException("From destination is invalid.");
            }
            try {
                this.to = new MappingDestination(dataHolder.getDestinationById(flightEntity.getTo_id()), ub); // TODO: Check ub !!!
            } catch (NullPointerException e) {
                throw new BadRequestException("To destination is invalid.");
            }
        }
    }

    public MappingDestination getFrom() {
        return from;
    }

    public void setFrom(MappingDestination from) {
        this.from = from;
    }

    public MappingDestination getTo() {
        return to;
    }

    public void setTo(MappingDestination to) {
        this.to = to;
    }
    
}
