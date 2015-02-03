/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.javaee.tests;

import cz.cvut.fel.javaee.db.DataHolder;
import cz.cvut.fel.javaee.db.model.DestinationEntity;
import cz.cvut.fel.javaee.resources.impl.DestinationResourceImpl;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@Stateless
public class DestTest {
    
    @Inject
    private DataHolder dh;
    
    @Test
    public void destIdUnique(){
        
        int count = 0;
        
        for(DestinationEntity d: dh.getAllDestinations()){
            count++;
        }
        
        DestinationEntity testD1 = new DestinationEntity(dh.getAndIncrementDestinationLastId(), "Otava");
        DestinationEntity testD2 = new DestinationEntity(dh.getAndIncrementDestinationLastId(), "New York");
        
        dh.addDestination(testD1);
        dh.addDestination(testD2);
        
        int tmpCount = 0;
        
        for(DestinationEntity d: dh.getAllDestinations()){
            tmpCount++;
        }
        
        assertEquals(count, tmpCount);
    }
    
    
    @Inject
    DestinationResourceImpl destinationImpl;
    
    @Test
    public void destAdd() {

        DestinationEntity  mdestination = new DestinationEntity();
        mdestination.setId((long)-10);
        mdestination.setLat((float) 2.5);
        mdestination.setLon((float) 3.5);
        mdestination.setName("TEST name");
               
        dh.addDestination(mdestination);
        DestinationEntity  destGet = dh.getDestinationById((long)-10);
        Assert.assertEquals(mdestination, destGet);
        
    }
    
     @Test
    public void destRemove() {
        
        Long id = dh.getAllDestinations().iterator().next().getId();
        DestinationEntity  mdestination = dh.getDestinationById(id);

        Assert.assertNotNull(mdestination); // get some Destination from db
         
        dh.deleteDestinationById(id);
        DestinationEntity  mdestination2 = dh.getDestinationById(id);
        
        Assert.assertNull(mdestination2); // dest was removed
    }
    
    @Test
    public void destUpdate() {
        
        Long id = dh.getAllDestinations().iterator().next().getId();
        DestinationEntity  destGet = dh.getDestinationById(id);
        Assert.assertNotNull(destGet);
        
        DestinationEntity mdestination = new DestinationEntity();
        mdestination.setId(destGet.getId());
        mdestination.setLat(destGet.getLat());
        mdestination.setLon(destGet.getLon());
        mdestination.setName(destGet.getName());
                
        Assert.assertEquals(mdestination, destGet);// copy is right
        Assert.assertNotSame(destGet.getLon(), 16L);
        mdestination.setLon(16L);
        boolean wasEdit = dh.editDestination(id, mdestination);
        
        DestinationEntity  destGet2 = dh.getDestinationById(id);
        
        if(wasEdit){
            Assert.assertNotSame(destGet2, destGet);// destination was updated
        }else{
            Assert.assertSame(destGet2, destGet);// destination was not updated
        }

    }
    
}
