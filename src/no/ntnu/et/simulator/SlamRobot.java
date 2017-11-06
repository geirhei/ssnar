/*
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

import java.util.ArrayList;
import no.ntnu.et.general.Pose;
import no.ntnu.et.general.Position;
import no.ntnu.et.general.Vertex;

/**
 *
 * @author geirhei
 */
public class SlamRobot extends SimRobot {
    private SimWorld world;
    private ArrayList<Vertex> vertices = new ArrayList();
    
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
        
        vertices.add(new Vertex(Vertex.EXTERIOR, new Position(25, 25)) );
    }
    
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
}
