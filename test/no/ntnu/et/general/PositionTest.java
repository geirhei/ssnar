/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

import java.awt.Graphics2D;
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
public class PositionTest {
    
    public PositionTest() {
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
     * Test of getXValue method, of class Position.
     */
    @Test
    public void testGetXValue() {
        System.out.println("getXValue");
        Position instance = new Position();
        double expResult = 0.0;
        double result = instance.getXValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getYValue method, of class Position.
     */
    @Test
    public void testGetYValue() {
        System.out.println("getYValue");
        Position instance = new Position();
        double expResult = 0.0;
        double result = instance.getYValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copy method, of class Position.
     */
    @Test
    public void testCopy() {
        System.out.println("copy");
        Position position = null;
        Position expResult = null;
        Position result = Position.copy(position);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class Position.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        Position instance = new Position();
        instance.print();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawCircle method, of class Position.
     */
    @Test
    public void testDrawCircle() {
        System.out.println("drawCircle");
        Graphics2D g2D = null;
        int diameter = 0;
        double scale = 0.0;
        Position instance = new Position();
        instance.drawCircle(g2D, diameter, scale);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawCross method, of class Position.
     */
    @Test
    public void testDrawCross() {
        System.out.println("drawCross");
        Graphics2D g2D = null;
        int size = 0;
        double scale = 0.0;
        Position instance = new Position();
        instance.drawCross(g2D, size, scale);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Position.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        Position other = null;
        Position instance = new Position();
        instance.add(other);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sum method, of class Position.
     */
    @Test
    public void testSum() {
        System.out.println("sum");
        Position pos1 = null;
        Position pos2 = null;
        Position expResult = null;
        Position result = Position.sum(pos1, pos2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of distanceBetween method, of class Position.
     */
    @Test
    public void testDistanceBetween() {
        System.out.println("distanceBetween");
        Position pos1 = null;
        Position pos2 = null;
        double expResult = 0.0;
        double result = Position.distanceBetween(pos1, pos2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of angleBetween method, of class Position.
     */
    @Test
    public void testAngleBetween() {
        System.out.println("angleBetween");
        Position pos1 = null;
        Position pos2 = null;
        Angle expResult = null;
        Angle result = Position.angleBetween(pos1, pos2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class Position.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");
        Angle rotation = null;
        Position instance = new Position();
        instance.transform(rotation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
