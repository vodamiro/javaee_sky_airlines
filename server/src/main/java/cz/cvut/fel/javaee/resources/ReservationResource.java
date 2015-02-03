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

import cz.cvut.fel.javaee.resources.mapping.MappingPayment;
import cz.cvut.fel.javaee.resources.mapping.MappingReservation;
import cz.cvut.fel.javaee.resources.mapping.MappingReservationForPost;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.HeaderParam;


/**
 * REST endopoint for resource Reservation
 * @author vodamiro
 */
@Path("/reservation")
@Local
@RequestScoped
public interface ReservationResource {
    
    
    /**
     * REST method returning collection of all reservations
     * @return Collection of resource objects
     */
    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Collection<MappingReservation> getReservations();
    
    /**
     * REST method for adding new item to DB. Takes item parameters from request
     * body.
     * @param newReservation Request body converted to DTO
     * @return Created object instance
     */
    @POST
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public MappingReservation addReservation(@Valid MappingReservationForPost newReservation);
    
    /**
     * REST method for retrieving object instance form DB by ID.
     * Takes ID as query parameter
     * body.
     * @param id Request object ID
     * @param password Reservation password
     * @return DTO representing object or AccessDenied if wrong password
     */
    @GET
    @Path("/{id}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public MappingReservation getReservation(@PathParam("id") long id, @HeaderParam("x-Password") String password);
    
    /**
     * REST method for email sending with reservation
     * @param id Reservation ID
     * @return Email content
     */
    @GET
    @Path("/{id}/email/")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getReservationEmail(@PathParam("id") long id);
    
    /**
     * REST method for deleting object from DB by ID.
     * Takes ID as query parameter. Only NON
     * body.
     * @param id Request object ID
     * @return Response
     */
    @DELETE
    @Path("/{id}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteReservation(@PathParam("id") long id);
    
    /**
     * REST method for object update in DB by ID.
     * Takes ID as query parameter
     * @param id Request object ID
     * @param newReservationData DTO from request with new data
     * @param password Password for resource update
     * @return Updated object DTO or AccessDenied when wrong password
     */
    @PUT
    @Path("/{id}/")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public MappingReservation editReservation(@PathParam("id") long id, @Valid MappingReservation newReservationData, @HeaderParam("x-Password") String password);
    
    /**
     * REST method for reservation paymend
     * @param id Reservation id
     * @param payment Payment DTO from request
     * @return Text file with reservation
     */
    @POST
    @Path("/{id}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @RolesAllowed("admin")
    public MappingReservation payReservation(@PathParam("id") long id, @Valid MappingPayment payment);
    
}
