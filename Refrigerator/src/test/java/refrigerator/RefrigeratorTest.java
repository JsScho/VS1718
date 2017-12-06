/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refrigerator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author scurr
 */
public class RefrigeratorTest {
    
    public RefrigeratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addHistoryEntry method, of class Refrigerator.
     */
    @org.junit.Test
    public void testAddHistoryEntry() {
        Refrigerator refrigerator = new Refrigerator(10);
        Long time = System.currentTimeMillis();
        
        refrigerator.addHistoryEntry("Milch", 1000, time);
        assertEquals("Adding entry to empty history",1,refrigerator.getContent().get(0).getFillHistory().size());
        
        refrigerator.addHistoryEntry("Milch", 1000, time);
        assertEquals("Adding duplicate entry",1,refrigerator.getContent().get(0).getFillHistory().size());
        
        refrigerator.addHistoryEntry("Milch", 1300, time-100);
        assertEquals("Adding entry that is older than an already existing entry",1,refrigerator.getContent().get(0).getFillHistory().size());
        
        refrigerator.addHistoryEntry("Milch", 1000, time-100);
        assertEquals("Adding entry with same amount that is older than last entry",1,refrigerator.getContent().get(0).getFillHistory().size());
        
        refrigerator.addHistoryEntry("Milch", 1000, time+100);
        assertEquals("Adding entry with same amount that is newer than last entry",1,refrigerator.getContent().get(0).getFillHistory().size());

    }    
}
