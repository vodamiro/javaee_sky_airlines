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
import cz.cvut.fel.javaee.clients.DistanceClient;
import cz.cvut.fel.javaee.db.model.DestinationEntity;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Class used for distance between two places retrieve (using external API)
 * @author vodamiro
 */
@Stateless
public class DistanceClientImpl implements DistanceClient{
    
    class DistanceResponseObject {
        int distance;
        
        DistanceResponseObject() {
            
        }

        /**
         * Return distance
         * @return int value of distance
         */
        public int getDistance() {
            return distance;
        }

        /**
         * Set distance
         * @param distance distance between two places
         */
        public void setDistance(int distance) {
            this.distance = distance;
        }

    }
    
    /**
     * Flag used for determine if WS is available
     */
    private static final boolean WORKING = false;
    
    private static final String MASHAPE_KEY = "O6Sr4CPWbkmshiaQPmAYt5S49bjep1YjGOvjsnox3M0tWfddzb";
    
    /**
     * Return distance between two places
     * require two places(param type DestinationEntity)
     * @param from DestinationEntity, first point for calculating distance
     * @param to DestinationEntity, second point for calculating distance
     * @return int value of distance between two places
     */
    @Override
    public int retrieve(DestinationEntity from, DestinationEntity to) {
        return retrieve(from.getLat(), from.getLon(), to.getLat(), to.getLon());
    }
    
    private int retrieve(float fromLat, float fromLon, float toLat, float toLon) {
        
        if (!WORKING) {
            return 1000;
        }
        
        Client client = Client.create();
        WebResource webResource = client
            .resource("https://orfeomorello-flight.p.mashape.com/mashape/distancebetweenpoints/startlat/"+Float.toString(fromLat)+
                    "/startlng/"+Float.toString(fromLon)+"/endlat/"+Float.toString(toLat)+"/endlng/"+Float.toString(toLon)+
                    "/unit/K");
        ClientResponse response = webResource.accept("application/json").header("X-Mashape-Key", MASHAPE_KEY).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        String output = response.getEntity(String.class);
        
        Gson gson = new Gson();
        DistanceClientImpl.DistanceResponseObject object = gson.fromJson(output, DistanceClientImpl.DistanceResponseObject.class);
                
        return object.getDistance();
    }
    
}
