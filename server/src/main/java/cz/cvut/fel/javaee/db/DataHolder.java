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

package cz.cvut.fel.javaee.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.cvut.fel.javaee.db.model.DestinationEntity;
import cz.cvut.fel.javaee.db.model.FlightEntity;
import cz.cvut.fel.javaee.db.model.ReservationEntity;
import cz.cvut.fel.javaee.util.FlightsReturn;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import org.infinispan.Cache;


/**
 * Class for object storage
 * There are function get, set and delete for each table
 */
//@Named
//@ApplicationScoped
@Stateless
public class DataHolder {

    @Resource(lookup="java:jboss/infinispan/container/myCache")
    private org.infinispan.manager.CacheContainer container;
 
    private Cache<String, Long> lastIds;
    private Cache<Long, FlightEntity> flights;
    private Cache<Long, ReservationEntity> reservations;
    private Cache<Long, DestinationEntity> destinations; 

    @PostConstruct
    public void init() {

   
        lastIds = container.getCache("lastIdsCache");
        destinations = container.getCache("destinationsCache");
        flights = container.getCache("flightsCache");
        reservations = container.getCache("reservationsCache");

        /**
         * add initial records to "Tables"
         */
        initTables();
    }
    
    private void initTables() {
        initDestination();
        initFlight();
        initReservation();
    }
    
     private void initDestination(){
         
        if (destinations.size()>0) {
            return;
        } 
        
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Praha", 50, 14));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Kladno", 20, 24));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Brno", 50, 24));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Pardubice", 40, 14));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Kolin", 30, 25));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Pribram", 14, 26));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Hradec", 13, 25));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Vary", 15, 10));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Viden", 20, 18));
        addDestination(new DestinationEntity(getAndIncrementDestinationLastId(),"Opava", 29, 29));
    }
    
    private void initFlight(){
        
        if (flights.size()>0) {
            return;
        } 
        
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let1", "2014-05-16T23:54", 100, 10, 10000, 1, 2));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let2", "2014-04-23T22:14", 50, 30, 15000, 1, 3));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let3", "2014-07-12T12:00", 20, 25, 10000, 2, 5));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let4", "2014-07-28T15:54", 3, 12, 10000, 8, 1));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let5", "2014-08-16T08:04", 70, 14, 10000, 6, 3));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let6", "2014-01-15T12:25", 55, 18, 10000, 3, 6));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let7", "2014-05-16T23:54", 43, 25, 10000, 4, 2));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let8", "2014-05-16T23:54", 16, 10, 10000, 1, 5));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let9", "2014-05-16T23:54", 22, 30, 10000, 4, 1));
        addFlight(new FlightEntity(getAndIncrementFlightLastId(), "let10", "2014-05-16T23:54", 19, 14, 10000, 3, 7));
    }
    
    private void initReservation(){
                
        if (reservations.size()>0) {
            return;
        } 
        
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 1, 2));
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 3, 5));
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 2, 1));
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 8, 1));
        ReservationEntity re = new ReservationEntity(getAndIncrementReservationLastId(), 4, 2);
        re.setState("payed");
        addReservation(re);
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 3, 1));
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 2, 1));
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 7, 2));
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 2, 1));
        addReservation(new ReservationEntity(getAndIncrementReservationLastId(), 6, 1));
    }
    
    /// Destinations
    /**
     * Return list of all destinations
     * @return Iterable
     */
    public Iterable<DestinationEntity> getAllDestinations() {
        
        Iterable<DestinationEntity> items = destinations.values();
        if (items == null) {
            return new ArrayList<>();
        } 

        return items;
    }

    /**
     * Return destination by ID
     * @param id ID of destination what we want to get
     * @return 
     */
    public  DestinationEntity getDestinationById(long id) {
       
        DestinationEntity d = destinations.get(id);
        if (d == null) {
            return null;
        } else {
            if (d.getId()==id) {
                return d;
            }
        }
               
        return null;
    }
    
    /**
     * Return destination by name
     * @param name Name of destination we want to get
     * @return 
     */
    public  DestinationEntity getDestinationByName(String name) {
       
        DestinationEntity result = null;
        
        for (DestinationEntity d: destinations.values()) {
            if (d.getName().equals(name)) {
                result = d;
                break;
            }
        }
        
        return result;
    }

    /**
     * Delete destination by ID
     * @param id ID of destination we want to delete
     * @return 
     */
    public  boolean deleteDestinationById(long id) {
      
        DestinationEntity d = destinations.get(id);
        if (d == null) {
            return false;
        } else {
            if (d.getId()==id) {
                destinations.remove(id);
                return true;
            }
        }
        
        return false;
      /*  for (int i=0; i<destinations.size() ; i++) {
            d = destinations.get(i);
            if (d.getId()==id) {
                destinations.remove(i);
                return true;
            }
        }*/
    }

    /**
     * Add destination to list of all destinations
     * @param destinationEntity destination we want to add to database
     * @return 
     */
    public  boolean addDestination(DestinationEntity destinationEntity) {
        
        if (getDestinationByName(destinationEntity.getName())==null) {
            destinations.put(destinationEntity.getId(),destinationEntity);
            return true;
        }
        
        return false;
    }

    /**
     * Get destination by ID and change atributes to values of atributes of second parameter destinationEntityEdited
     * @param id ID of destination we want to edit
     * @param destinationEntityEdited object with edited parameters we want to save
     * @return 
     */
    public  boolean editDestination(long id, DestinationEntity destinationEntityEdited) {
  
       DestinationEntity d = getDestinationById(id);
       if (d!=null) {
           if (d.getName().equals(destinationEntityEdited.getName()) || getDestinationByName(destinationEntityEdited.getName())==null) {
               d.setName(destinationEntityEdited.getName());
               d.setLat(destinationEntityEdited.getLat());
               d.setLon(destinationEntityEdited.getLon());
                destinations.remove(id);
                destinations.put(id,d);
               return true;
           }
       }

       return false;
    }
    

    /// Reservations
    /**
     * Return list of all reservation
     * @return Iterable
     */
    public  Iterable<ReservationEntity> getAllReservations() {
        
        Iterable<ReservationEntity> items = reservations.values();
        if (items == null) {
            return new ArrayList<>();
        } 

        return items;
    }

    /**
     * Add reservation to list of all reservations
     * @param reservationEntity reservation that should be add to database
     * @return boolean
     */
    public  boolean addReservation(ReservationEntity reservationEntity) {
      
        if (getFlightById(reservationEntity.getFlight_id())!=null) {
            reservations.put(reservationEntity.getId(), reservationEntity);
            return true;
        }
        
        return false;
    }

    /**
     * Return reservation by ID
     * @param id ID of reservation we want to get
     * @return ReservationEntity
     */
    public  ReservationEntity getReservationById(long id) {
        
        ReservationEntity d = reservations.get(id);
        if (d == null) {
            return null;
        } else {
            if (d.getId()==id) {
                return d;
            }
        }

        return null;
    }

    /**
     * Delete reservation by ID
     * @param id ID of reservation we want to delete
     * @return 
     */
    public int deleteReservationById(long id) {
   
        ReservationEntity d = reservations.get(id);
        if (d == null) {
            return -1;
        } else {
            if (d.getId()==id) {
                if (d.getState().equals("payed")==false) {
                    reservations.remove(id);
                    return 1;
                }
                return 0;
            }
        }
        
        return -1;
    }
    
    /**
     * Delete all reservations of one flight
     * @param id ID of flight
     */
    private void deleteReservationsWithFlightId(long id) {
        
        for (ReservationEntity reservation: reservations.values()) {
             if (reservation.getFlight_id()==id) {
                reservations.remove(reservation.getId());
             }
        }
        
    }

    /**
     * Get reservation by ID and change atributes to values of atributes of second parameter reservationEntityEdited
     * @param id ID of reservation we want to edit
     * @param reservationEntityEdited object with edited atributes
     * @return 
     */
    public  boolean editReservation(long id, ReservationEntity reservationEntityEdited) {
        
       ReservationEntity d = getReservationById(id);
       if (d!=null) {
           if (d.getState().equals("new") && reservationEntityEdited.getState().equals("payed")) {
               d.setState(reservationEntityEdited.getState());
                reservations.remove(id);
                reservations.put(id,d);
               return true;
           }
       }

       return false;
    }

    /**
     * Not implemented yet
     * @param id
     * @param paymentEntity
     * @return 
     */
    public  boolean payReservation(long id, ReservationEntity paymentEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Flights
    /**
     * Return list of flights filtred by filter parameter and sort by order parametr
     * base parameter - first index of returned flight
     * offset parameter - number of offset from base
     * @param filter String with filter options
     * @param order sorting order
     * @param base first item, that should be returned
     * @param offset number of item, that should be returned, counted from base
     * @return 
     */
    public  FlightsReturn getAllFlights(String filter, String order, String base, String offset) {
        
        if (filter==null || order==null || base==null || offset==null) {
            if (flights==null) {
                return new FlightsReturn(new ArrayList<FlightEntity>(), 0);
            } else {
                return new FlightsReturn(flights.values(), flights.size());
            }               
        }
        
        Logger.getLogger("Headers").log(Level.INFO, filter +" "+ order +" "+ base +" "+ offset);
        
        // Trim inputs
        filter = filter.trim();
        order = order.trim();
        base = base.trim();
        offset = offset.trim();
        
        // Parse offset
        int from = 0;
        if (!offset.isEmpty()) {
            try {
                from = Integer.parseInt(offset);
            } catch (NumberFormatException e) {
                from = 0;
            }
        }
        
        // Parse base
        int count = Integer.MAX_VALUE;
        if (!base.isEmpty()) {
            try {
                count = Integer.parseInt(base);
            } catch (NumberFormatException e) {
                count = Integer.MAX_VALUE;
            }
        }
        
        // All flights
        ArrayList<FlightEntity> items = new ArrayList<>();
        items.addAll(flights.values());
      
        // Filter
        if (filter.contains("dateOfDepartureFrom=") && filter.contains("dateOfDepartureTo=") && filter.contains(",")) {
            filter = filter.replace("dateOfDepartureFrom=", "");
            filter = filter.replace("dateOfDepartureTo=", "");
            String dateFromStr = filter.split(",")[0];
            String dateToStr = filter.split(",")[1];
            
            //Date dateFrom = DatatypeConverter.parseDateTime(dateFromStr).getTime();
            //Date dateTo = DatatypeConverter.parseDateTime(dateToStr).getTime();
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date dateFrom = null;
            Date dateTo = null;
            try {
                dateFrom = df1.parse(dateFromStr);
                dateTo = df1.parse(dateToStr);
            } catch (ParseException ex) {
                Logger.getLogger(DataHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (dateTo!=null && dateFrom!=null) {

                // Filter out items
                items.clear();
                for (FlightEntity flight: flights.values()) {
                    Date date = flight.getDate();
                    if (date.after(dateFrom) && date.before(dateTo)) {
                        items.add(flight);
                    }
                    
                }
  
            }
        }

        // Ordering
        if (order.contains("date")) { // order by date
            
            if (order.contains("desc")) {
                Collections.sort(items, new DateComparatorDesc());
            } else if (order.contains("asc")) {
                Collections.sort(items, new DateComparatorAsc());
            }
            
        } else if (order.contains("name")) { // order by name
            
            if (order.contains("desc")) {
                Collections.sort(items, new NameComparatorDesc());                
            } else if (order.contains("asc")) {
                Collections.sort(items, new NameComparatorAsc());                
            }
        }
        
        int to = items.size();
        if (count!=Integer.MAX_VALUE) {
            to = Math.min(to, from+count);
        }
        
        ArrayList<FlightEntity> result = new ArrayList<>();
        
        // Copy item between FROM TO
        for (int i=from ; i<to ; i++) {
            result.add(items.get(i));
        }
        
        Logger.getLogger("Headers").log(Level.INFO, Integer.toString(items.size()));
            
        return new FlightsReturn(result, items.size());
    }

    public synchronized long getAndIncrementFlightLastId() {
        Long result = lastIds.get("flight_id");
        if (result==null) {
            result = 1L;
        }
        Logger.getLogger(DataHolder.class.getCanonicalName()).log(Level.SEVERE, result.toString());
        lastIds.put("flight_id", result+1);
        return result;
    }
    
   public synchronized long getAndIncrementDestinationLastId() {
        Long result = lastIds.get("destination_id");
        if (result==null) {
            result = 1L;
        }
         Logger.getLogger(DataHolder.class.getCanonicalName()).log(Level.SEVERE, result.toString());
        lastIds.put("destination_id", result+1);
        return result;
    }
        
    public synchronized long getAndIncrementReservationLastId() {
        Long result = lastIds.get("reservation_id");
        if (result==null) {
            result = 1L;
        }
         Logger.getLogger(DataHolder.class.getCanonicalName()).log(Level.SEVERE, result.toString());
        lastIds.put("reservation_id", result+1);
        return result;
    }

    /**
     * Return list of all reservation
     * @param state
     * @return Iterable
     */
    public List<ReservationEntity> getAllDailyReservations(String state) {
        
        List<ReservationEntity> reservationsList = new LinkedList<>();
        for (ReservationEntity reservation: reservations.values()) {
             if (reservation.getState().endsWith(state)) {
                reservationsList.add(reservation);
             }
        }

        return reservationsList;
    }
    
    /**
     * Date comparator - descending
     */
    public class DateComparatorDesc implements Comparator<FlightEntity> {
        @Override
        public int compare(FlightEntity p, FlightEntity q) {
            if (p.getDate().before(q.getDate())) {
                return -1;
            } else if (p.getDate().after(q.getDate())) {
                return 1;
            } else {
                return 0;
            }        
        }
    }

    /**
     * Date comparator - ascending
     */
    public class DateComparatorAsc implements Comparator<FlightEntity> {
        @Override
        public int compare(FlightEntity p, FlightEntity q) {
            if (p.getDate().before(q.getDate())) {
                return 1;
            } else if (p.getDate().after(q.getDate())) {
                return -1;
            } else {
                return 0;
            }
        }
    }
        
    /**
     * Name comparator - descending
     */
    public class NameComparatorDesc implements Comparator<FlightEntity> {
        @Override
        public int compare(FlightEntity p, FlightEntity q) {
            return p.getName().compareTo(q.getName());
        }
    }

    /**
     * Name comparator - ascending
     */
    public class NameComparatorAsc implements Comparator<FlightEntity> {
        @Override
        public int compare(FlightEntity p, FlightEntity q) {
            return -p.getName().compareTo(q.getName());
        }
    }

    /**
     * Add flight to list of all flights
     * @param flightEntity Fligth that should be add to database
     * @return boolean
     */
    public boolean addFlight(FlightEntity flightEntity) {
        
        if (getFlightByName(flightEntity.getName())==null) {
            if (getDestinationById(flightEntity.getFrom_id())!=null) {
                if (getDestinationById(flightEntity.getFrom_id())!=null) {
                    flights.put(flightEntity.getId(),flightEntity);
                    return true;
                }
            }
        }
        
        return false;
     
    }

    /**
     * Return flight by Name
     * @param name Name of flight we want to get
     * @return FlightEntity
     */
    public  FlightEntity getFlightByName(String name) {
       
        FlightEntity result = null;
        
        for (FlightEntity d: flights.values()) {
            if (d.getName().equals(name)) {
                result = d;
                break;
            }
        }
        
        return result;
    }
    
    /**
     * Delete flight by ID
     * @param id ID of flight we want to delete
     * @return boolean
     */
    public boolean deleteFlightById(long id) {
        
        FlightEntity d = flights.get(id);
        if (d == null) {
            return false;
        } else {
            if (d.getId()==id) {
                deleteReservationsWithFlightId(id);
                flights.remove(id);
                return true;
            }
        }
        
        return false;
    }

    /**
     * Return flight by ID
     * @param id ID of flight we want to get
     * @return FlightEntity
     */
    public FlightEntity getFlightById(long id) {
        
        FlightEntity d = flights.get(id);
        if (d == null) {
            return null;
        } else {
            if (d.getId()==id) {
                return d;
            }
        }

        return null;
    }

    /**
     * Get flight by ID and change atributes to values of atributes of second parameter flightEntityEdited
     * @param id ID of flight we want to edit
     * @param flightEntityEdited object with edited atributes
     * @return boolean
     */
    public  boolean editFlight(long id, FlightEntity flightEntityEdited) {
        
       FlightEntity d = getFlightById(id);
       if (d!=null) {
           if (d.getName().equals(flightEntityEdited.getName()) || getFlightByName(flightEntityEdited.getName())==null) {
               if (getDestinationById(flightEntityEdited.getFrom_id())!=null && getDestinationById(flightEntityEdited.getTo_id())!=null) {
                    d.setName(flightEntityEdited.getName());
                    d.setFrom_id(flightEntityEdited.getFrom_id());
                    d.setTo_id(flightEntityEdited.getTo_id());
                    d.setPrice(flightEntityEdited.getPrice());
                    d.setDistance(flightEntityEdited.getDistance());
                    d.setSeats(flightEntityEdited.getSeats());
                    d.setDateOfDeparture(flightEntityEdited.getDateOfDeparture());
                    flights.remove(id);
                    flights.put(id,d);
                    return true;
               }
           }
       }

       return false;
    }
    
}
