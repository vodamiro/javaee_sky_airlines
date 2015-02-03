
package cz.cvut.fel.javaee.batch;

import cz.cvut.fel.javaee.db.DataHolder;
import cz.cvut.fel.javaee.db.model.ReservationEntity;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Properties;
import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("ResOverviewItemReader")
public class ResOverviewItemReader
    extends AbstractItemReader {

    @Inject
    private DataHolder dataHolder;

    @Inject
    private JobContext jobContext;

    Iterator<ReservationEntity> reservations;

    @Override
    public void open(Serializable e) throws Exception {
        Properties jobParameters = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId());
         reservations = dataHolder.getAllDailyReservations(jobParameters.getProperty("state")).iterator();
    }

    @Override
    public Object readItem() throws Exception {
        return reservations.hasNext() ? reservations.next() : null;
    }
    
}
