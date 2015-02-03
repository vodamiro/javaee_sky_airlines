
package cz.cvut.fel.javaee.batch;

import cz.cvut.fel.javaee.db.model.ReservationEntity;
import java.util.List;
import java.util.logging.Logger;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named("ResOverviewItemWriter")
public class ResOverviewItemWriter
    extends AbstractItemWriter {
    

private final static Logger LOGGER = Logger.getLogger(ResOverviewItemWriter.class.toString());

@Override
    public void writeItems(List list) throws Exception {
        for (Object obj : list) {
            ReservationEntity reservation = (ReservationEntity) obj;
            StringBuilder sb = new StringBuilder();
            sb.append("Reservation:\n");
            sb.append("ID: ");
            sb.append(reservation.getId());
            sb.append("\n");
            sb.append("Created: ");
            sb.append(reservation.getCreated());
            sb.append("\n");
            sb.append("Seats: ");
            sb.append(reservation.getSeats());
            sb.append("\n");
            sb.append("Flight ID: ");
            sb.append(reservation.getFlight_id());
            
            LOGGER.info(sb.toString());   
        }
    }
    
}
