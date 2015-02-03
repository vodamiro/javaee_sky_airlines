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

package cz.cvut.fel.javaee.util;

import cz.cvut.fel.javaee.db.model.FlightEntity;

/**
 * This class represents list of all flights with count field - used as DTO 
 * @author vodamiro
 */
public class FlightsReturn {

    Iterable<FlightEntity> flights;
    int count;
    
    /**
     * Constructor
     * @param result Query result - list of flight entities
     * @param size  Count of list items
     */
    public FlightsReturn(Iterable<FlightEntity> result, int size) {
        flights = result;
        count = size;
    }

    /**
     * Return list of all flights
     * @return 
     */
    public Iterable<FlightEntity> getFlights() {
        return flights;
    }

    /**
     * Return number of all flights
     * @return 
     */
    public int getCount() {
        return count;
    }
    
}
