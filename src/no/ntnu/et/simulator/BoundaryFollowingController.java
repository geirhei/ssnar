/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

import java.util.Random;

/**
 *
 * @author geirhei
 */
public class BoundaryFollowingController extends Thread {
    private final SimRobot robot;
    private final double[] distances;
    private boolean paused = false;
    private boolean roaming = false;
    private static int mode;
    private final static int LOST = 0;
    private final static int TRANSLATING = 1;
    private final static int FOLLOWING = 2;
    private final int stepDistance = 10; // cm
    private int targetHeading = -1;
    private final double distanceThreshold = 20; //cm
    
    
    public BoundaryFollowingController(SimRobot robot) {
        this.robot = robot;
        distances = robot.getDistances();
        mode = LOST;
    }
    
    @Override
    public void start() {
        if (!isAlive()) {
            super.start();
        }
        paused = false;
    }
    
    public void quit() {
        paused = true;
    }
    
    @Override
    public void run() {
        setName("Boundary following controller");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean finished = false;
        while (!finished) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                robot.stop();
                break;
            }
            if (paused) {
                continue;
            }
            
            switch (mode)
            {
                case LOST: // No obstacles measured nearby
                    // Get the direction to the boundary that is closest to the robot
                    int shortestDistanceHeading = getShortestDistanceHeading();
                    if (shortestDistanceHeading == -1) {
                        // Get random heading in the interval [0, 360)
                        Random ran = new Random();
                        targetHeading = ran.nextInt(359 + 1);
                    } else {
                        targetHeading = shortestDistanceHeading;
                    }
                    
                    robot.setMovement(targetHeading, stepDistance);
                    mode = TRANSLATING;
                    break;
                case TRANSLATING:
                    if (robot.isTranslationFinished()) {
                        mode = LOST;
                    }
                    if (getShortestDistance() < distanceThreshold) {
                        robot.stop();
                        mode = FOLLOWING;
                    }
                    break;
                case FOLLOWING: // Wall-following mode
                    
                    break;
                default:
                    // should not reach here
                    break;
            }
            
        }
    }
    
    private double getShortestDistance() {
        double shortestDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < shortestDistance) {
                shortestDistance = distances[i];
            }
        }
        return shortestDistance;
    }
    
    /**
     * Returns -1 if all fields in []distances have the same infinity value
     * 
     * @return int
     */
    private int getShortestDistanceHeading() {
        int shortestDistanceHeading = -1;
        double currentDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < currentDistance) {
                currentDistance = distances[i];
                shortestDistanceHeading = i;
            }
        }
        return shortestDistanceHeading;
    }
}
