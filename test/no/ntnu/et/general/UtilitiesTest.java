/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

import java.awt.Color;
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
public class UtilitiesTest {
    
    public UtilitiesTest() {
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
     * Test of polar2cart method, of class Utilities.
     */
    //@Test
    public void testPolar2cart() {
        System.out.println("polar2cart");
        Angle theta = null;
        double r = 0.0;
        Position expResult = null;
        Position result = Utilities.polar2cart(theta, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of string2Position method, of class Utilities.
     */
    //@Test
    public void testString2Position() {
        System.out.println("string2Position");
        String string = "";
        Position expResult = null;
        Position result = Utilities.string2Position(string);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findPositionAlongLine method, of class Utilities.
     */
    //@Test
    public void testFindPositionAlongLine() {
        System.out.println("findPositionAlongLine");
        double[] start = null;
        double[] unitVector = null;
        double distance = 0.0;
        Position expResult = null;
        Position result = Utilities.findPositionAlongLine(start, unitVector, distance);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of selectColor method, of class Utilities.
     */
    //@Test
    public void testSelectColor() {
        System.out.println("selectColor");
        int i = 0;
        Color expResult = null;
        Color result = Utilities.selectColor(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lineCircleIntersection method, of class Utilities.
     */
    //@Test
    public void testLineCircleIntersection() {
        System.out.println("lineCircleIntersection");
        Line line = null;
        Position circleCenter = null;
        double circleRadius = 0.0;
        double expResult = 0.0;
        double result = Utilities.lineCircleIntersection(line, circleCenter, circleRadius);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lineLineIntersection method, of class Utilities.
     */
    //@Test
    public void testLineLineIntersection() {
        System.out.println("lineLineIntersection");
        Line line1 = null;
        Line line2 = null;
        double expResult = 0.0;
        double result = Utilities.lineLineIntersection(line1, line2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMeasurementHeadings method, of class Utilities.
     */
    //@Test
    public void testGetMeasurementHeadings() {
        System.out.println("getMeasurementHeadings");
        int[] towerHeadings = null;
        int[] expResult = null;
        int[] result = Utilities.getMeasurementHeadings(towerHeadings);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrthogonalProjection method, of class Utilities.
     */
    //@Test
    public void testGetOrthogonalProjection() {
        System.out.println("getOrthogonalProjection");
        Position point = null;
        Line line = null;
        Position expResult = null;
        Position result = Utilities.getOrthogonalProjection(point, line);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of polar2cart method, of class Utilities.
     */
    //@Test
    public void testPolar2cart_Angle_double() {
        System.out.println("polar2cart");
        Angle theta = null;
        double r = 0.0;
        Position expResult = null;
        Position result = Utilities.polar2cart(theta, r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of polar2cart method, of class Utilities.
     */
    @Test
    public void testPolar2cart_int_double() {
        System.out.println("polar2cart");
        int theta = -10;
        double r = 10;
        //Position expResult = null;
        Position result = Utilities.polar2cart(theta, r);
        System.out.println("x: " + result.getXValue() + ", y: " + result.getYValue());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
