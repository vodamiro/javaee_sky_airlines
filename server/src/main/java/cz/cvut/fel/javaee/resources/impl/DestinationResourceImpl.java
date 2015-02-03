/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.javaee.resources.impl;

import cz.cvut.fel.javaee.clients.GoogleGeolocClient;
import cz.cvut.fel.javaee.db.DataHolder;
import cz.cvut.fel.javaee.db.model.DestinationEntity;
import cz.cvut.fel.javaee.exceptions.BadRequestException;
import cz.cvut.fel.javaee.exceptions.NotFoundRequestException;
import cz.cvut.fel.javaee.resources.DestinationResource;
import cz.cvut.fel.javaee.resources.mapping.MappingDestination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Simo
 */

@Stateless
public class DestinationResourceImpl implements DestinationResource{
    @Inject
    private DataHolder dataHolder;
    
    @EJB 
    private GoogleGeolocClient googleGeolocClient;
    
    @Context
    UriInfo uriInfo;
    
    @Override
    public Collection<MappingDestination> getDestinations() {
        
        List<MappingDestination> destinations = new ArrayList<>();
        
        for (DestinationEntity destination: dataHolder.getAllDestinations()) {
            destinations.add(new MappingDestination(destination, uriInfo.getRequestUriBuilder()));
        }
        
        return destinations;
    }

    @Override
    public MappingDestination addDestination(MappingDestination newDestination){
     
        /*String validationError = newDestination.validate();
        if (validationError != null) {
            throw new BadRequestException("Validation error: "+validationError);
        }*/
        
        DestinationEntity destinationEntity = new DestinationEntity(dataHolder.getAndIncrementDestinationLastId(), newDestination.getName());
        destinationEntity = googleGeolocClient.retrieve(destinationEntity);
        
        boolean ret = dataHolder.addDestination(destinationEntity);
        
        if (!ret) {
            throw new BadRequestException("Cannot create destination");
        }
        
        return new MappingDestination(destinationEntity, uriInfo.getAbsolutePathBuilder());
    }
    
 
    @Override
    public MappingDestination getDestination( long id) {
        DestinationEntity destination = dataHolder.getDestinationById(id);
        
        if (destination==null) {
            throw new NotFoundRequestException("Destination not found.");
        }
        
        return new MappingDestination(destination, uriInfo.getAbsolutePathBuilder());
    }

    @Override
    public Response deleteDestination(@PathParam("id") long id) {
        
        if (dataHolder.deleteDestinationById(id)==false) {
            throw new NotFoundRequestException("Destination not found.");
        } 
        
        return Response.noContent().build(); // success
    }
    

    @Override
    public MappingDestination editDestination( long id, MappingDestination newDestinationData) {
        
        DestinationEntity destinationEntityEdited = new DestinationEntity(id, newDestinationData.getName(), newDestinationData.getLat(), newDestinationData.getLon());
        
        /* check if exists */
        if (dataHolder.getDestinationById(id)==null) {
            throw new NotFoundRequestException("Destination not found.");
        }

        boolean ret = dataHolder.editDestination(id, destinationEntityEdited);
        
        if (ret) {
            return getDestination(id);
        } else {
            throw new BadRequestException("Cannot edit destination");
        }
  
    }
    
}