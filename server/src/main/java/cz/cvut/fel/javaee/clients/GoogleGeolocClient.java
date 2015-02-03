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

package cz.cvut.fel.javaee.clients;

import cz.cvut.fel.javaee.db.model.DestinationEntity;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 * This class is used for getting latitude and longitude from name of place
 * (using external API)
 * @author vodamiro
 */

@Local
public interface GoogleGeolocClient {

     
    /**
     * Returns DestinationEntity object that contains latitude and longitude
     * obtained from destination name
     * @param destination DestinationEntity object with filled latitude and longitude
     * @return DestinationEntity
     */
    public DestinationEntity retrieve(DestinationEntity destination);
    
}
