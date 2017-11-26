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
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author geirhei
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({no.ntnu.et.general.VertexTest.class, no.ntnu.et.general.AngleTest.class, no.ntnu.et.general.NavigationTest.class, no.ntnu.et.general.ObservationTest.class, no.ntnu.et.general.PositionTest.class, no.ntnu.et.general.LineTest.class, no.ntnu.et.general.UtilitiesTest.class, no.ntnu.et.general.PoseTest.class})
public class GeneralSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
