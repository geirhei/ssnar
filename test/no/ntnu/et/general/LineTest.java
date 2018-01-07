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

    /**
     * Test of mergeSelf method, of class Line.
     */
    //@Test
    public void testMergeSelf() {
        System.out.println("mergeSelf");
        ArrayList<Line> repository = null;
        ArrayList<Line> expResult = null;
        //ArrayList<Line> result = Line.mergeSelf(repository);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMidpoint method, of class Line.
     */
    //@Test
    public void testGetMidpoint() {
        System.out.println("getMidpoint");
        Line line = new Line(new Position(2, 2), new Position(-2, -2));
        //Position expResult = null;
        Position result = Line.getMidpoint(line);
        System.out.println("x: " + result.getXValue() + ", y: " + result.getYValue());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMidpoint method, of class Line.
     */
    //@Test
    public void testGetMidpoint_Line() {
        System.out.println("getMidpoint");
        Line line = null;
        Position expResult = null;
        Position result = Line.getMidpoint(line);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMidpoint method, of class Line.
     */
    //@Test
    public void testGetMidpoint_Position_Position() {
        System.out.println("getMidpoint");
        Position a = null;
        Position b = null;
        Position expResult = null;
        Position result = Line.getMidpoint(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of extendLine method, of class Line.
     */
    //@Test
    public void testExtendLine() {
        System.out.println("extendLine");
        Position pL = new Position(4, 2);
        Position pR = new Position(-4, 2);
        Line newLine = new Line(pL, pR);
        Position p1 = new Position(-6, 2);
        Position p2 = new Position(-7, 2);
        Position p3 = new Position(-8, 2);
        Position p4 = new Position(-9, 4);
        
        Line.extendLine(p1, newLine);
        
        Line.extendLine(p2, newLine);
        Line.extendLine(p3, newLine);
        Line.extendLine(p4, newLine);
        newLine.print();
        assertEquals(newLine.pL, pL);
    }

    /**
     * Test of calculateError method, of class Line.
     */
    //@Test
    public void testCalculateError() {
        System.out.println("calculateError");
        Position p0 = new Position(-2, 2);
        Position p1 = new Position(-1, 0);
        Position p2 = new Position(-2, -2);
        double expResult = 1.0;
        double result = Math.abs(Line.calculateError(p0, p1, p2));
        assertEquals(expResult, result, 1e-9);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of generateLine method, of class Line.
     */
    //@Test
    public void testGenerateLine() {
        System.out.println("generateLine");
        double theta = 0.0;
        Position p0 = null;
        Position p1 = null;
        Position p2 = null;
        Line expResult = null;
        //Line result = Line.generateLine(theta, p0, p1, p2);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print1 method, of class Line.
     */
    //@Test
    public void testPrint1() {
        System.out.println("print1");
        Line instance = null;
        instance.print1();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateTheta method, of class Line.
     */
    //@Test
    public void testCalculateTheta() {
        System.out.println("calculateTheta");
        Position p0 = new Position(0, 0);
        Position p1 = new Position(2, 2);
        double expResult = 45.0;
        double result = Line.calculateTheta(p0, p1);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of isLine method, of class Line.
     */
    //@Test
    public void testIsLine() {
        System.out.println("isLine");
        Position p0 = null;
        Position p1 = null;
        Position p2 = null;
        boolean expResult = false;
        boolean result = Line.isLine(p0, p1, p2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of detectLines method, of class Line.
     */
    //@Test
    public void testDetectLines() {
        System.out.println("detectLines");
        Line newLine = new Line(new Position(4, 2), new Position(-4, 2));
        Position p0 = new Position(-5, 2);
        Position p1 = new Position(-6, 2);
        Position p2 = new Position(-7, 2);
        Position p3 = new Position(-8, 2);
        List<Position> observations = new ArrayList<Position>();
        observations.add(p0);
        observations.add(p1);
        observations.add(p2);
        observations.add(p3);
        //List<Line> expResult = null;
        List<Line> result = Line.detectLines(observations);
        Position pLres = new Position(4, 2);
        Position pRres = new Position(-8, 2);
        assertEquals(pLres, result.get(0).pL);
        assertEquals(pRres, result.get(0).pR);
        result.get(0).print();
        System.out.println("size: " + result.size());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calculateC method, of class Line.
     */
    //@Test
    public void testCalculateC() {
        System.out.println("calculateC");
        
        double x = 0.0;
        double y = -55.0;
        double xOrg = y;
        double yOrg = x;
        double thetaOrg = 90.0;
        double thetaNew = thetaOrg - 90;
        double aOrg = Math.sin(Math.toRadians(thetaOrg));
        double bOrg = -Math.cos(Math.toRadians(thetaOrg));
        double cOrg = -aOrg * xOrg - bOrg * yOrg;
        double cNew = Line.calculateC(thetaNew, x, y);
        assertEquals(cOrg, cNew, 1e-9);
        /*
        assertEquals(-1.0, result, 0.01);
        result = Line.calculateC(theta2, x, y);
        assertEquals(1.0, result, 0.01);
        result = Line.calculateC(theta3, x, y);
        assertEquals(1.0, result, 0.01);
        result = Line.calculateC(theta4, x, y);
        assertEquals(-1.0, result, 0.01);
        */
    }

    /**
     * Test of projectOntoLine method, of class Line.
     */
    @Test
    public void testProjectOntoLine() {
        System.out.println("projectOntoLine");
        Position p0 = new Position(19, 9);
        Position p1 = new Position(16, 9);
        Position p2 = new Position(14, 6);
        Position p3 = new Position(10, 4);
        Position p4 = new Position(6, 9);
        Position p5 = new Position(2, 9);
        Position p7 = new Position(10, -3);
        Position p8 = new Position(11, 0);
        
        Line line0 = new Line(p4, p5);
        line0.print();
        Position p6 = new Position(4, 14);
        Position res = Line.projectOntoLine(p6, line0);
        Position expected = new Position(4, 9);
        res.print();
        assertEquals(expected, res);
        
        Line line6 = new Line(p7, p3);
        res = Line.projectOntoLine(new Position(11, -4), line6);
        res.print();
        assertEquals(res, new Position(10, 0));
    }

    /**
     * Test of matchSegment method, of class Line.
     */
    //@Test
    public void testMatchSegment() {
        System.out.println("matchSegment");
        Line mapLine = new Line(new Position(0, 0), new Position(1, 1));
        Line line = new Line(new Position(0, 1), new Position(1, 0));
        line.print();
        boolean expResult = true;
        boolean result = Line.matchSegment(mapLine, line);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}