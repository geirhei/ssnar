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
    //@Test
    /*
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
    */
    
    /**
     * Test of checkCollision method, of class Navigation.
     */
    //@Test
    public void testCheckCollision() {
        System.out.println("checkCollision");
        double[] distances = new double[360];
        for (int i = 0; i < 360; i++) {
            distances[i] = 25;
        }
        distances[89] = 24;
        Angle robotHeading = new Angle(90);
        
        int expResult = -1;
        int result = Navigation.checkCollision(robotHeading, distances);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTarget method, of class Navigation.
     */
    @Test
    public void testGetTarget() {
        System.out.println("getTarget");
        Angle heading = new Angle(180);
        Pose currentPose = new Pose(50, 50, 90);
        double stepDistance = 10;
        Position expResult = new Position(10, 20);
        Position result = Navigation.getTarget(heading, currentPose, stepDistance);
        //assertEquals(expResult, result);
        
        System.out.println("x: " + result.getXValue() + ", " + "y: " + result.getYValue());
        /*
        currentPose = new Pose(0, 0, 0);
        heading = new Angle(91);
        result = Navigation.getTarget(heading, currentPose, stepDistance);
        System.out.println("x: " + result.getXValue() + ", " + "y: " + result.getYValue());
        currentPose = new Pose(0, 0, 0);
        heading = new Angle(180);
        result = Navigation.getTarget(heading, currentPose, stepDistance);
        System.out.println("x: " + result.getXValue() + ", " + "y: " + result.getYValue());
        currentPose = new Pose(50, 50, 90);
        heading = new Angle(180);
        result = Navigation.getTarget(heading, currentPose, stepDistance);
        System.out.println("x: " + result.getXValue() + ", " + "y: " + result.getYValue());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        */
    }

    /**
     * Test of calculateNewTarget method, of class Navigation.
     */
    //@Test
    public void testCalculateNewTarget() {
        System.out.println("calculateNewTarget");
        Angle shortestHeading = new Angle(10);
        Position currentPos = new Position(0, 0);
        double stepDistance = 10;
        int wallSide = -1;
        Position expResult = null;
        Position result = Navigation.calculateNewTarget(shortestHeading, currentPos, stepDistance, wallSide);
        System.out.println("x: " + result.getXValue() + ", y: " + result.getYValue());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getShortestDistanceHeading method, of class Navigation.
     */
    //@Test
    public void testGetShortestDistanceHeading() {
        System.out.println("getShortestDistanceHeading");
        double[] distances = null;
        int expResult = 0;
        int result = Navigation.getShortestDistanceHeading(distances);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateDistances method, of class Navigation.
     */
    //@Test
    public void testCalculateDistances() {
        System.out.println("calculateDistances");
        double[] measurements = {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        int servoStep = 90;
        int[] expResult = null;
        int[] result = Navigation.calculateDistances(measurements, servoStep);
        for (int r : result) {
            System.out.println(r);
        }
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
