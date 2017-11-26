/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.et.simulator.Feature;
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
public class LineTest {
    
    public LineTest() {
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
     * Test of getStart method, of class Line.
     */
    //@Test
    public void testGetStart() {
        System.out.println("getStart");
        Line instance = new Line(new Position(5, 3), new Position(8, 6));
        double[] expResult = {5, 3};
        double[] result = instance.getStart();
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirection method, of class Line.
     */
    
    //@Test
    public void testGetDirection() {
        System.out.println("getDirection");
        Line instance = null;
        double[] expResult = null;
        double[] result = instance.getDirection();
        //assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLength method, of class Line.
     */
    //@Test
    public void testGetLength() {
        System.out.println("getLength");
        Line instance = null;
        double expResult = 0.0;
        double result = instance.getLength();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getA method, of class Line.
     */
    //@Test
    public void testGetA() {
        System.out.println("getA");
        Line instance = null;
        Position expResult = null;
        Position result = instance.getA();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getB method, of class Line.
     */
    //@Test
    public void testGetB() {
        System.out.println("getB");
        Line instance = null;
        Position expResult = null;
        Position result = instance.getB();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of convertFeatureToLine method, of class Line.
     */
    //@Test
    public void testConvertFeatureToLine() {
        System.out.println("convertFeatureToLine");
        Feature feature = null;
        Line expResult = null;
        Line result = Line.convertFeatureToLine(feature);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLineBetweenPositions method, of class Line.
     */
    //@Test
    public void testGetLineBetweenPositions() {
        System.out.println("getLineBetweenPositions");
        Position startPos = null;
        Position endPos = null;
        Line expResult = null;
        Line result = Line.getLineBetweenPositions(startPos, endPos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lineCreate method, of class Line.
     */
    //@Test
    public void testLineCreate() {
        System.out.println("lineCreate");
        ArrayList<Position> pointBuffer = null;
        ArrayList<Line> lineBuffer = null;
        Line.lineCreate(pointBuffer, lineBuffer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lineMerge method, of class Line.
     */
    //@Test
    public void testLineMerge() {
        System.out.println("lineMerge");
        ArrayList<Line> lineBuffer = null;
        List<Line> lineRepository = null;
        Line.lineMerge(lineBuffer, lineRepository);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class Line.
     */
    //@Test
    public void testPrint() {
        System.out.println("print");
        Line instance = null;
        instance.print();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
