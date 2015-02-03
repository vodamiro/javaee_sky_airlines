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

import java.io.Serializable;
import java.util.Objects;

/**
 * This class respresents DB entity destination
 * @author Petr
 */
public class DestinationEntity implements Serializable{

    private Long id;
    private String name;
    private Float lat;
    private Float lon;

    public DestinationEntity() {
        this.id = null;
        this.name = null;
        this.lat = null;
        this.lon = null;
    }
    
    /**
     * Constructor
     * There is autoincrement for id
     * If parameter id is higher then last used id(lastId), counter of new id continues from value parameter id
     * If parameter id is lower then last used id(lastId), counter of new id continues from value of last used id(lastID)
     * @param id
     * @param name
     * @param lat
     * @param lon 
     */
    public DestinationEntity(long id, String name, float lat, float lon) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    
    /**
     * Constructor
     * There is autoincrement for id
     * If parameter id is higher then last id, counting of id continues from this value
     * @param id
     * @param name
     */
    public DestinationEntity(long id, String name) {
        this.id = id;
        this.name = name;
        this.lat = new Float(0);
        this.lon = new Float(0);
    }
    
    /**
     * Return ID of DestinationEntity
     * @return 
     */
    public long getId() {
        return id;
    }

    /**
     * Return name of DestinationEntity
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Return latitude
     * @return 
     */
    public float getLat() {
        return lat;
    }

    /**
     * Return longitude
     * @return 
     */
    public float getLon() {
        return lon;
    }

    /**
     * Set name of destination
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set latitude of DestinationEntity
     * @param lat 
     */
    public void setLat(float lat) {
        this.lat = lat;
    }

    /**
     * Set longitude of DestionationEntity
     * @param lon 
     */
    public void setLon(float lon) {
        this.lon = lon;
    }
    
        public void setId(long l) {
       this.id = l;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DestinationEntity other = (DestinationEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.lat, other.lat)) {
            return false;
        }
        if (!Objects.equals(this.lon, other.lon)) {
            return false;
        }
        return true;
    }
    
}
