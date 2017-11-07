/*
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import no.ntnu.et.general.Pose;
import no.ntnu.et.map.Cell;
import no.ntnu.et.map.MapLocation;

/**
 *
 * @author geirhei
 */
public class SlamRobot extends SimRobot {
    private SimWorld world;

    
    /**
     * Constructor for SlamRobot.
     *
     * @param world
     * @param initialPose
     * @param name
     * @param id
     * @param address
     */
    SlamRobot(SimWorld world, Pose initialPose, String name, int id, int address) {
        super(world, initialPose, name, id, address);
        this.world = world;
        
        
    }
    
    
}
