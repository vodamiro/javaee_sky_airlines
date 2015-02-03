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

import cz.cvut.fel.javaee.db.model.DestinationEntity;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
* Class for mapping DB entity to DTO used for REST response
* @author vodamiro
*/
@XmlRootElement(name="destination")
public class MappingDestination {
    
    private Long id;
    @NotNull @Size(min=1, max=50)
    private String name;
    @Min(-90) @Max(90)
    private Float lat;
    @Min(-180) @Max(180)
    private Float lon;
    private String url;
    
    /**
     * Default constructor
     */
    public MappingDestination(){}
    
    /**
     * Fills class attributes from DB entity
     * @param destinationEntity
     * @param ub 
     */
    public MappingDestination(DestinationEntity destinationEntity, UriBuilder ub){
        if(destinationEntity != null){
            this.id = destinationEntity.getId();
            this.name = destinationEntity.getName();
            this.lat = destinationEntity.getLat();
            this.lon = destinationEntity.getLon();
            this.url ="/destination/" + Long.toString(destinationEntity.getId());
        }
    }

    @XmlElement(required=true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }
    
    public void setUrl(String uri) {
        this.url = uri;
    }
    
    public String getUrl() {
        return this.url;
    }
        
}
