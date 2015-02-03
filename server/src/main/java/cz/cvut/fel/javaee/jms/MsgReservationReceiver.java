/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.javaee.jms;

import cz.cvut.fel.javaee.jms.dto.Reservation;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


/**
 *
 * @author Simo
 */
@MessageDriven(name = "EmailMDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/EmailMDBQueue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class MsgReservationReceiver implements MessageListener {
    @Resource
private MessageDrivenContext mdc;
private final static Logger LOGGER = Logger.getLogger(MsgReservationReceiver.class.toString());
    
    public MsgReservationReceiver() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        ObjectMessage msg = null;
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                Reservation r = (Reservation) msg.getObject();
                LOGGER.info("Received Message: /n");   
                sendEmail(r);
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
        
    }
    
    public void sendEmail(Reservation reservation) {     
     StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append("__________*_____******_____******____\n");
        sb.append("__________*_____*__________*_________\n");
        sb.append("__________*_____*__________*_________\n");
        sb.append("__________*_____******_____******____\n");
        sb.append("__________*_____*__________*_________\n");
        sb.append("____*____*______*__________*_________\n");
        sb.append("_____*__*_______*__________*_________\n");
        sb.append("______**________******_____******____\n");
        sb.append('\n');
        sb.append('\n');
        sb.append('\n');
        sb.append("Reservation: ");
        sb.append(reservation.id);
        sb.append("\nFlight: ");
        sb.append(reservation.flight.name);
        sb.append("\nPassword: ");
        sb.append(reservation.password);
        sb.append("\nSeats: ");
        sb.append(reservation.seats);
        sb.append("\nFrom Destination: ");
        sb.append(reservation.flight.destinationFrom.name);
        sb.append("\nDestination to: ");
        sb.append(reservation.flight.destinationTo.name);
        
        LOGGER.info(sb.toString());
}
    
}
