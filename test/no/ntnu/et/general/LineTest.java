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
     * Test of lineMerge1 method, of class Line.
     */
    //@Test
    public void testLineMerge1() {
        System.out.println("lineMerge1");
        List<Line> buffer = null;
        List<Line> repository = null;
        Line.lineMerge1(buffer, repository);
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
     * Test of isMergeable1 method, of class Line.
     */
    //@Test
    public void testIsMergeable() {
        System.out.println("isMergeable");
        Position a = new Position(-10,2);
        Position b = new Position(2,2);
        Position c = new Position(-2,-2);
        Position d = new Position(2,-2);
        Line line1 = new Line(a, b);
        Line line2 = new Line(c, d);
        boolean expResult = true;
        boolean result = Line.isMergeable(line1, line2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calculateU1 method, of class Line.
     */
    //@Test
    public void testCalculateU1() {
        System.out.println("calculateU1");
        double angle = 0.0;
        double expResult = 0.0;
        double result = Line.calculateU1(angle);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateU2 method, of class Line.
     */
    //@Test
    public void testCalculateU2() {
        System.out.println("calculateU2");
        double dist = 0.0;
        double expResult = 0.0;
        double result = Line.calculateU2(dist);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateU3 method, of class Line.
     */
    //@Test
    public void testCalculateU3() {
        System.out.println("calculateU3");
        double dist = 0.0;
        double lenA = 0.0;
        double lenB = 0.0;
        double expResult = 0.0;
        double result = Line.calculateU3(dist, lenA, lenB);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateU4 method, of class Line.
     */
    //@Test
    public void testCalculateU4() {
        System.out.println("calculateU4");
        double dist = 0.0;
        double expResult = 0.0;
        double result = Line.calculateU4(dist);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        double theta = 180.0;
        double varTheta = 0.0;
        Position pR = new Position(6, 2);
        Position pL = new Position(2, 2);
//        Line line = new Line(theta, varTheta, pR, pL);
        Position p = new Position(1, -1);
        boolean expResult = true;
        //boolean result = Line.extendLine(p, line);
        //line.print();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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
        Position p0 = new Position(2, 2);
        Position p1 = new Position(4, 2);
        Position p2 = new Position(6, 2);
        Position p3 = new Position(7, 20);
        List<Position> observations = new ArrayList<Position>();
        observations.add(p0);
        observations.add(p1);
        observations.add(p2);
        observations.add(p3);
        //List<Line> expResult = null;
        List<Line> result = Line.detectLines(observations);
        //result.get(0).print();
        System.out.println("size: " + result.size());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calculateC method, of class Line.
     */
    @Test
    public void testCalculateC() {
        System.out.println("calculateC");
        double theta = 270.0;
        double x = -2.0;
        double y = 0.0;
        double expResult = 2.0;
        double result = Line.calculateC(theta, x, y);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of projectOntoLine method, of class Line.
     */
    //@Test
    public void testProjectOntoLine() {
        System.out.println("projectOntoLine");
        Position start = new Position(2, 2);
        Position end = new Position(6, 2);
        Line line = new Line(start, end);
        Position newPoint = new Position(0, 6);
        //Position expResult = null;
        Position result = Line.projectOntoLine(newPoint, line);
        System.out.println("res: (" + result.getXValue() + ", " + result.getYValue() + ")");
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}