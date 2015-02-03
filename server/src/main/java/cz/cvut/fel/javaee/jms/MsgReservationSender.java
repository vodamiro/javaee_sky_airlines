/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.javaee.jms;


import cz.cvut.fel.javaee.jms.dto.Flight;
import cz.cvut.fel.javaee.jms.dto.Reservation;
import cz.cvut.fel.javaee.jms.dto.Destination;
import cz.cvut.fel.javaee.db.DataHolder;
import cz.cvut.fel.javaee.db.model.DestinationEntity;
import cz.cvut.fel.javaee.db.model.FlightEntity;
import cz.cvut.fel.javaee.db.model.ReservationEntity;
import java.io.IOException;
import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Simo
 */
@WebServlet(name = "MsgReservationSender", urlPatterns = {"/MsgReservationSender"})
public class MsgReservationSender extends HttpServlet {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:/queue/EmailMDBQueue")
    private javax.jms.Queue queue;

    @Inject
    private DataHolder dataHolder;
     

    /**
     * Processes requests for both HTTP GET and POST methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
        long id = Long.valueOf(request.getParameter("id"));
        Reservation r = transformReservation(id);
        if (r==null) {
            response.sendError(404, "Reservation not found.");
            return;
        }
        
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer((javax.jms.Destination) queue);

            ObjectMessage message = session.createObjectMessage();

            message.setObject((Serializable) r);
            messageProducer.send(message);
            messageProducer.close();
            connection.close();
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        
    }

    protected Reservation transformReservation(Long id) {
        Reservation finalR = new Reservation();
        Flight f2 = new Flight();
        Destination df2 = new Destination();
        Destination dt2 = new Destination();
        
        ReservationEntity r1 = dataHolder.getReservationById(id);
        
        if (r1==null) {
            return null;
        }
        
        FlightEntity f1 = dataHolder.getFlightById(r1.getFlight_id());
        DestinationEntity df1 = dataHolder.getDestinationById(f1.getFrom_id());
        DestinationEntity dt1 = dataHolder.getDestinationById(f1.getTo_id());
        
        df2.id = df1.getId();
        df2.lat = df1.getLat();
        df2.lon = df1.getLon();
        df2.name = df1.getName();
        
        dt2.id = dt1.getId();
        dt2.lat = dt1.getLat();
        dt2.lon = dt1.getLon();
        dt2.name = dt1.getName();
        
        f2.dateOfDeparture = f1.getDateOfDeparture();
        f2.destinationFrom = df2;
        f2.destinationTo = dt2;
        f2.distance = f1.getDistance();
        f2.id = f1.getId();
        f2.name = f1.getName();
        f2.price = f1.getPrice();
        f2.seats = f1.getSeats();
        
        finalR.created = r1.getCreated();
        finalR.flight = f2;
        finalR.id = r1.getId();
        finalR.password = r1.getPassword();
        finalR.seats = r1.getSeats();
        finalR.state = r1.getState();
        
        
        return finalR;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
