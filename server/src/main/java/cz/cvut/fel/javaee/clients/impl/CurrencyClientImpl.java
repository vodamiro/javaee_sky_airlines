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
import cz.cvut.fel.javaee.clients.CurrencyClient;
import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * This class is used to convert currencies (using external API)
 * API is not woking so far, so there is conversion ratio 1:1
 * 
 * @author vodamiro
 */
@Stateless
public class CurrencyClientImpl implements CurrencyClient{
    
    /**
     * Flag used for determine if WS is available
     */
    private static final boolean WORKING = false;
    
    /**
     * DTO for response parsing
     */
    class CurrencyResponseObject {
        
        String from, to;
        float rate;
        
        CurrencyResponseObject() {}

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public float getRate() {
            return rate;
        }

        public void setRate(float rate) {
            this.rate = rate;
        }

    }

    /**
     * Return price in converted currency
     * default currency is set from PRIMARY_CURRENCY
     * @param toCurrency string with currency you want to get
     * @return 
     */
    @Override
    public float retrieve(final String toCurrency) {
        return retrieve(PRIMARY_CURRENCY, toCurrency);
    }
    
    /**
     * Return price in converted currency
     * Currency API is not working so it returns 1
     * @param fromCurrency string with original currency
     * @param toCurrency string with currency you want to get
     * @return 
     */
    @Override
    public float retrieve(final String fromCurrency, final String toCurrency) {
       
        if (WORKING) {
            Client client = Client.create();
            WebResource webResource = client
                    .resource("http://rate-exchange.appspot.com/currency")
                    .queryParam("from", fromCurrency)
                    .queryParam("to", toCurrency);
            
            ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
            String output = response.getEntity(String.class);

            Gson gson = new Gson();
            CurrencyClientImpl.CurrencyResponseObject object = gson.fromJson(output, CurrencyClientImpl.CurrencyResponseObject.class);

            return object.getRate(); 
        } else {
            return 1;
        }
    }
    
}
