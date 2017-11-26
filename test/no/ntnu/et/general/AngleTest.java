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
public class AngleTest {
    
    public AngleTest() {
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
     * Test of getValue method, of class Angle.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Angle instance = new Angle();
        double expResult = 0.0;
        double result = instance.getValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copy method, of class Angle.
     */
    @Test
    public void testCopy() {
        System.out.println("copy");
        Angle angle = null;
        Angle expResult = null;
        Angle result = Angle.copy(angle);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Angle.
     */
    @Test
    public void testAdd_Angle() {
        System.out.println("add");
        Angle addedAngle = null;
        Angle instance = new Angle();
        instance.add(addedAngle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Angle.
     */
    @Test
    public void testAdd_double() {
        System.out.println("add");
        double addedAngle = 0.0;
        Angle instance = new Angle();
        instance.add(addedAngle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sum method, of class Angle.
     */
    @Test
    public void testSum() {
        System.out.println("sum");
        Angle angl1 = null;
        Angle angl2 = null;
        Angle expResult = null;
        Angle result = Angle.sum(angl1, angl2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of difference method, of class Angle.
     */
    @Test
    public void testDifference() {
        System.out.println("difference");
        Angle angl1 = null;
        Angle angl2 = null;
        double expResult = 0.0;
        double result = Angle.difference(angl1, angl2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class Angle.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        Angle instance = new Angle();
        instance.print();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of drawAngle method, of class Angle.
     */
    @Test
    public void testDrawAngle() {
        System.out.println("drawAngle");
        Graphics2D g2D = null;
        double xStart = 0.0;
        double yStart = 0.0;
        int length = 0;
        double scale = 0.0;
        Angle instance = new Angle();
        instance.drawAngle(g2D, xStart, yStart, length, scale);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
