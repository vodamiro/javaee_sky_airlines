/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.javaee.resources.impl;

import cz.cvut.fel.javaee.db.DataHolder;
import cz.cvut.fel.javaee.db.model.ReservationEntity;
import cz.cvut.fel.javaee.exceptions.BadRequestException;
import cz.cvut.fel.javaee.exceptions.NotFoundRequestException;
import cz.cvut.fel.javaee.resources.ReservationResource;
import cz.cvut.fel.javaee.resources.mapping.MappingPayment;
import cz.cvut.fel.javaee.resources.mapping.MappingReservation;
import cz.cvut.fel.javaee.resources.mapping.MappingReservationForPost;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public class ReservationResourceImpl implements ReservationResource{
     
    @Inject
    private DataHolder dataHolder;
    
    @Context
    UriInfo uriInfo;

    @Override
    public Collection<MappingReservation> getReservations() {
        
        List<MappingReservation> reservations = new ArrayList<>();
        
        for (ReservationEntity reservation: dataHolder.getAllReservations()) {
            reservations.add(new MappingReservation(dataHolder, reservation, uriInfo.getRequestUriBuilder()));
        }
        
        return reservations;
    }
    

    @Override
    public MappingReservation addReservation(MappingReservationForPost newReservation){ // MUST USE MappingReservationForPost because in post is NOT flight object but flight ID only

        
        ReservationEntity reservationEntity = new ReservationEntity(dataHolder.getAndIncrementReservationLastId(), newReservation.getFlight(), newReservation.getSeats());
        reservationEntity.validate();
        
        boolean ret = dataHolder.addReservation(reservationEntity);
        
        if (!ret) {
            throw new BadRequestException("Cannot create reservation");
        }
        
        return new MappingReservation(dataHolder, reservationEntity, uriInfo.getAbsolutePathBuilder());
    }
    

    @Override
    public MappingReservation getReservation( long id, String password) {
      
        ReservationEntity reservation = dataHolder.getReservationById(id);
        if (reservation==null) {
            throw new NotFoundRequestException("Reservation not found.");
        }

        /*if (password==null || !password.equals(reservation.getPassword())) {
            throw new AccessDeniedRequestException("Bad x-Password value !");
        }*/
        
        return new MappingReservation(dataHolder, reservation, uriInfo.getAbsolutePathBuilder());
    }
    

    @Override
    public Response getReservationEmail( long id) {
        
        ReservationEntity reservation = dataHolder.getReservationById(id);
        
        if (reservation==null) {
            throw new NotFoundRequestException("Reservation not found.");
        }

        try {
            
           // TODO: Send e-mail to JMS
            
           Response.ResponseBuilder response = Response.ok("OK");
           return response.build();

       } catch(Exception e) {
           e.printStackTrace();
       }
        
       throw new BadRequestException("Cannot email reservation");
    }
    

    @Override
    public Response deleteReservation( long id) {
        
        int ret = dataHolder.deleteReservationById(id);
        if (ret==-1) {
            throw new NotFoundRequestException("Reservation not found.");
        } else if (ret==0) {
            throw new BadRequestException("Cannot delete payed reservation");
        }
        
        return Response.noContent().build(); // success
    }
    

    @Override
    public MappingReservation editReservation(long id, MappingReservation newReservationData, String password) {
        
        ReservationEntity reservationEntityEdited = new ReservationEntity(id, newReservationData.getState());
        
        /* check if exists */
        if (dataHolder.getReservationById(id)==null) {
            throw new NotFoundRequestException("Reservation not found.");
        }
        
        /*if (password==null || !password.equals(dataHolder.getReservationById(id).getPassword())) {
            throw new AccessDeniedRequestException("Bad x-Password value !");
        }*/

        boolean ret = dataHolder.editReservation(id, reservationEntityEdited);
        if (ret) {
            return getReservation(id, password);
        } else {
            throw new BadRequestException("Cannot edit reservation");
        }
  
    }
    
    @Override
    public MappingReservation payReservation( long id, MappingPayment payment){ 
        
        ReservationEntity paymentEntity = new ReservationEntity(-1, payment.getBank_account());
        
        boolean ret = dataHolder.payReservation(id, paymentEntity);
        if (ret) {
            return getReservation(id, dataHolder.getReservationById(id).getPassword());
        } else {
            throw new BadRequestException("Cannot edit reservation");
        }
    }
    
}