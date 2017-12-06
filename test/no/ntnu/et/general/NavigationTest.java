/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author geirhei
 */
public class NavigationTest {
    
    public NavigationTest() {
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
     * Test of getShortestDistanceAngle method, of class Navigation.
     */
    //@Test
    public void testGetShortestDistanceAngle() {
        System.out.println("getShortestDistanceAngle");
        int[] distances = null;
        int expResult = 0;
        int result = Navigation.getShortestDistanceAngle(distances);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCollision method, of class Navigation.
     */
    @Test
    public void testIsInCollision() {
        System.out.println("isInCollision");
        Angle towerAngle = new Angle(70);
        int forwardSensor = 15;
        int rightSensor = 15;
        int expResult = -1;
        int result = Navigation.checkCollision(towerAngle, forwardSensor, rightSensor);
        assertEquals(expResult, result);
        
        
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkCollision method, of class Navigation.
     */
    //@Test
    public void testCheckCollision() {
        System.out.println("checkCollision");
        Angle towerAngle = null;
        int rightSensor = 0;
        int forwardSensor = 0;
        int expResult = 0;
        int result = Navigation.checkCollision(towerAngle, rightSensor, forwardSensor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
