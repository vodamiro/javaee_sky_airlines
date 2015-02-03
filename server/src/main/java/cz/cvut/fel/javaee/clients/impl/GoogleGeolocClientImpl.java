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

package cz.cvut.fel.javaee.clients.impl;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import cz.cvut.fel.javaee.clients.GoogleGeolocClient;
import cz.cvut.fel.javaee.db.model.DestinationEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * This class is used for getting latitude and longitude from name of place
 * (using external API)
 * @author vodamiro
 */
@Stateless
public class GoogleGeolocClientImpl implements GoogleGeolocClient{

    /**
     * Geolocation DTO response object
     */
    class GoogleGeolocResponseObject {
        
        List<GoogleGeolocResponseResultObject> results;
        String status;

        public GoogleGeolocResponseObject() {
        }
      
    }
    
    /**
     * Geolocation DTO response object
     */
    class GoogleGeolocResponseResultObject {

        String formatted_address;
        GoogleGeolocResponseResultGeometryObject geometry;
        
        public GoogleGeolocResponseResultObject() {
        }
                
    }
    
    /**
     * Geolocation DTO response object
     */
    class GoogleGeolocResponseResultGeometryObject {
        
       GoogleGeolocResponseResultGeometryLocationObject location;

        public GoogleGeolocResponseResultGeometryObject() {
        }
       
    }
    
    /**
     * Geolocation DTO response object
     */
    class GoogleGeolocResponseResultGeometryLocationObject {
     
        Float lat, lng;
        
        public GoogleGeolocResponseResultGeometryLocationObject() {
        }
    }
    
    /**
     * Returns DestinationEntity object that contains latitude and longitude
     * obtained from destination name
     * @param destination DestinationEntity object with filled latitude and longitude
     * @return DestinationEntity
     */
    @Override
    public DestinationEntity retrieve(DestinationEntity destination) {
        
        if (destination==null) {
            return null;
        }
        
        String address = destination.getName();
        
        Client client = Client.create();
        WebResource webResource = client
            .resource("http://maps.googleapis.com/maps/api/geocode/json")
            .queryParam("address",address)
            .queryParam("sensor", "false");
        
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        
        String output = response.getEntity(String.class);
        Gson gson = new Gson();
        GoogleGeolocClientImpl.GoogleGeolocResponseObject object = gson.fromJson(output, GoogleGeolocClientImpl.GoogleGeolocResponseObject.class);
        
        if (object.status.equals("OK")) {
            destination.setName(object.results.get(0).formatted_address);
            destination.setLat(object.results.get(0).geometry.location.lat);
            destination.setLon(object.results.get(0).geometry.location.lng);
            return destination;
        }
        
        return destination;
    }
    
}
