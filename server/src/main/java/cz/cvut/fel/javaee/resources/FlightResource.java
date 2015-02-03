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

package cz.cvut.fel.javaee.resources;

import cz.cvut.fel.javaee.resources.mapping.MappingFlight;
import cz.cvut.fel.javaee.resources.mapping.MappingFlightForPost;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST endopoint for resource Flight
 * @author vodamiro
 */
@Path("/flight")
@Local
@RequestScoped
public interface FlightResource {

    /**
     * REST method returning collection of all flights
     * @param order Ordering header parameter
     * @param filter Filtering header parameter
     * @param base Base header parameter
     * @param offset Offset header parameter
     * @param currency Currenct header parameter
     * @return Collection of resource objects
     */
    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getFlights(@HeaderParam("x-Order") String order, @HeaderParam("x-Filter") String filter, @HeaderParam("x-Base") String base, @HeaderParam("x-Offset") String offset, @HeaderParam("x-Currency") String currency);
    
    /**
     * REST method for adding new item to DB. Takes item parameters from request
     * body.
     * @param newFlight Request body converted to DTO
     * @return Created object instance
     */
    @POST
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addFlight(MappingFlightForPost newFlight);
    
    /**
     * REST method for retrieving object instance form DB by ID.
     * Takes ID as query parameter
     * body.
     * @param id Request object ID
     * @return DTO representing object
     */
    @GET
    @Path("/{id}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public MappingFlight getFlight(@PathParam("id") long id, @HeaderParam("x-Currency") String currency);
    
    /**
     * REST method for deleting object from DB by ID.
     * Takes ID as query parameter
     * body.
     * @param id Request object ID
     * @return Response
     */
    @DELETE
    @Path("/{id}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteFlight(@PathParam("id") long id);
    
    /**
     * REST method for object update in DB by ID.
     * Takes ID as query parameter
     * @param id Request object ID
     * @param data DTO from request with new data
     * @param currency Currency parameter
     * @return Updated object DTO
     */
    @PUT
    @Path("/{id}/")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public MappingFlight editFlight(@PathParam("id") long id, @Valid MappingFlightForPost data, @HeaderParam("X-Currency") String currency);
    
}
