/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.javaee.resources.impl;

import cz.cvut.fel.javaee.resources.Resources;
import cz.cvut.fel.javaee.resources.mapping.MappingResource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author vodamiro
 */
@Stateless
public class ResourcesImpl implements Resources {
    
    @Context
    UriInfo uriInfo;
    
    /**
     * REST method returning list of resources' names and urls
     * @return List of resources' names and urls
     */
    @Override
    public Collection<MappingResource> getResources() {
        
        List<MappingResource> resources = new ArrayList<>();
        
        resources.add(new MappingResource("Flights", "flight", uriInfo.getRequestUriBuilder()));
        resources.add(new MappingResource("Destinations", "destination", uriInfo.getRequestUriBuilder()));
        resources.add(new MappingResource("Reservations", "reservation", uriInfo.getRequestUriBuilder()));
        
        return resources;
    }
    
}

