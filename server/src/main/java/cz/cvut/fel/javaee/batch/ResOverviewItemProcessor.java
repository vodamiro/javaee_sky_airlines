
package cz.cvut.fel.javaee.batch;

import cz.cvut.fel.javaee.db.model.ReservationEntity;
import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("ResOverviewItemProcessor")
public class ResOverviewItemProcessor
    implements ItemProcessor {

    @Inject
    private JobContext jobContext;

    @Override
    public Object processItem(Object obj) throws Exception {
        ReservationEntity reservation = (ReservationEntity) obj;
       
        return reservation;
    }
    
}
