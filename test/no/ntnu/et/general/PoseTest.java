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
public class PoseTest {
    
    public PoseTest() {
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
     * Test of getPosition method, of class Pose.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        Pose instance = new Pose();
        Position expResult = null;
        Position result = instance.getPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeading method, of class Pose.
     */
    @Test
    public void testGetHeading() {
        System.out.println("getHeading");
        Pose instance = new Pose();
        Angle expResult = null;
        Angle result = instance.getHeading();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHeading method, of class Pose.
     */
    @Test
    public void testSetHeading() {
        System.out.println("setHeading");
        Angle heading = null;
        Pose instance = new Pose();
        instance.setHeading(heading);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copy method, of class Pose.
     */
    @Test
    public void testCopy() {
        System.out.println("copy");
        Pose pose = null;
        Pose expResult = null;
        Pose result = Pose.copy(pose);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of paint method, of class Pose.
     */
    @Test
    public void testPaint() {
        System.out.println("paint");
        Graphics2D g2D = null;
        double scale = 0.0;
        Pose instance = new Pose();
        instance.paint(g2D, scale);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rotate method, of class Pose.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        Angle rotation = null;
        Pose instance = new Pose();
        instance.rotate(rotation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of move method, of class Pose.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        double distance = 0.0;
        Pose instance = new Pose();
        instance.move(distance);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sum method, of class Pose.
     */
    @Test
    public void testSum() {
        System.out.println("sum");
        Pose pose1 = null;
        Pose pose2 = null;
        Pose expResult = null;
        Pose result = Pose.sum(pose1, pose2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class Pose.
     */
    @Test
    public void testTransform() {
        System.out.println("transform");
        Pose otherPose = null;
        Pose instance = new Pose();
        instance.transform(otherPose);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
