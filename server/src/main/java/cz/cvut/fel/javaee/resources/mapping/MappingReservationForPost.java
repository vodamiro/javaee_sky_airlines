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
import cz.cvut.fel.javaee.db.model.ReservationEntity;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Class for mapping DB entity to DTO used for REST response
* @author vodamiro
*/
@XmlRootElement(name="reservation")
public class MappingReservationForPost extends MappingReservationPrototype {
    
    private long flight;
    
    /**
     * Default constructor
     */
    public MappingReservationForPost(){}
    
    /**
     * Fills class attributes from DB entity
     * @param reservationEntity
     * @param ub 
     */
    public MappingReservationForPost(ReservationEntity reservationEntity, UriBuilder ub){
        super(reservationEntity, ub);
        if(reservationEntity != null){
            this.flight = reservationEntity.getFlight_id();
        }
    }
    
    public long getFlight() {
        return flight;
    }

    public void setFlight(long flight) {
        this.flight = flight;
    }

}
