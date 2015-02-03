/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.javaee.resources.impl;

import cz.cvut.fel.javaee.clients.CurrencyClient;
import cz.cvut.fel.javaee.db.DataHolder;
import cz.cvut.fel.javaee.db.model.FlightEntity;
import cz.cvut.fel.javaee.exceptions.BadRequestException;
import cz.cvut.fel.javaee.exceptions.NotFoundRequestException;
import cz.cvut.fel.javaee.resources.FlightResource;
import cz.cvut.fel.javaee.resources.mapping.MappingFlight;
import cz.cvut.fel.javaee.resources.mapping.MappingFlightForPost;
import cz.cvut.fel.javaee.util.FlightsReturn;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Simo
 */

@Stateless
public class FlightResourceImpl implements FlightResource{
    
    @Inject
    private DataHolder dataHolder;
    
   /* @Inject 
    private DistanceClient distanceClient;

    @Inject 
    private CurrencyClient currencyClient;*/
    
    @Context
    UriInfo uriInfo;

    @Override
    public Response getFlights(String order,  String filter,  String base,  String offset,  String currency) {
                
        List<MappingFlight> flights = new ArrayList<>();
        
        FlightsReturn ret = dataHolder.getAllFlights(filter, order, base, offset);
        
        float currencyRatio;
        if (currency != null) {
            currencyRatio = 1f; //currencyClient.retrieve(currency);
        } else {
            currency = CurrencyClient.PRIMARY_CURRENCY;
            currencyRatio = 1f;
        } 
           
        for (FlightEntity flight: ret.getFlights() ) {
            flights.add(new MappingFlight(dataHolder, flight, uriInfo.getRequestUriBuilder(), currency, currencyRatio));
        }
    
        return Response
       .status(Response.Status.OK)
       .entity(flights.iterator())
       .header("X-Count-records", Integer.toString(ret.getCount()))
       .build();
        
    }
    

    @Override
    public Response addFlight(MappingFlightForPost newFlight){ // MUST USE MappingFlight because in post are NOT from and to objects but IDs only
        
        if (newFlight==null) {
            Logger.getLogger(MappingFlight.class.getCanonicalName()).log(Level.SEVERE, "newFlight is null");
            throw new BadRequestException("Cannot create flight");
        } else {
            Logger.getLogger(MappingFlight.class.getCanonicalName()).log(Level.SEVERE, newFlight.toString());
        }
        
        FlightEntity flightEntity = new FlightEntity(dataHolder.getAndIncrementFlightLastId(), newFlight.getName(), newFlight.getDateOfDeparture(), newFlight.getSeats(), newFlight.getFrom(), newFlight.getTo());
        
        //int distance = distanceClient.retrieve(flightEntity.getFrom(dataHolder), flightEntity.getTo(dataHolder));
       
        //flightEntity.setDistanceAndPrice(distance);
       
        boolean ret = dataHolder.addFlight(flightEntity);
        
        if (!ret) {
            throw new BadRequestException("Cannot create flight");
        }
        
        return Response.status(Response.Status.OK).build();
    }
    
    @Override
    public MappingFlight getFlight( long id,  String currency) {
        FlightEntity flight = dataHolder.getFlightById(id);
        
        if (flight==null) {
            throw new NotFoundRequestException("Flight not found.");
        }
        
        float currencyRatio;
        if (currency != null) {
            currencyRatio = 1f;//currencyClient.retrieve(currency);
        } else {
            currency = CurrencyClient.PRIMARY_CURRENCY;
            currencyRatio = 1f;
        } 
        
        return new MappingFlight(dataHolder, flight, uriInfo.getAbsolutePathBuilder(), currency, currencyRatio);
    }
    

    @Override
    public Response deleteFlight( long id) {
        
        if (dataHolder.deleteFlightById(id)==false) {
            throw new NotFoundRequestException("Flight not found.");
        } 
        
        return Response.noContent().build(); // success
    }
    

    @Override
    public MappingFlight editFlight( long id, MappingFlightForPost data,  String currency) {
        
        Logger.getLogger(MappingFlight.class.getCanonicalName()).log(Level.SEVERE, data.toString());
        
      // Convert to primary currency
      float price = Float.parseFloat(data.getPrice().split("\\ ")[0]);
      String inCurrency = data.getPrice().split("\\ ")[1];        
     // price = currencyClient.retrieve(inCurrency, CurrencyClient.PRIMARY_CURRENCY)*price;
      
      FlightEntity flightEntityEdited = new FlightEntity(id, data.getName(), data.getDateOfDeparture(), data.getDistance(), data.getSeats(), price, data.getFrom(), data.getTo());
        
        /* check if exists */
        if (dataHolder.getFlightById(id)==null) {
            throw new NotFoundRequestException("Flight not found.");
        }

        boolean ret = dataHolder.editFlight(id, flightEntityEdited);
        if (ret) {
            return getFlight(id, currency);
        } else {
            throw new BadRequestException("Cannot edit flight");
        }
      
    }
    
}