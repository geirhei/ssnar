/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

import static java.lang.Math.random;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import no.ntnu.et.general.Angle;
import no.ntnu.et.general.Navigation;
import no.ntnu.et.general.Pose;
import no.ntnu.et.general.Position;

/**
 *
 * @author geirhei
 */
public class BoundaryFollowingController extends Thread {
    private final SimRobot robot;
    private final double[] distances;
    private boolean paused = false;
    private boolean roaming = false;
    private int state;
    private int lastState;
    private final static int IDLE = 0;
    private final static int TRANSLATING = 1;
    private final static int FOLLOWING = 2;
    private final static int WALL_FOLLOWING = 3;
    private final static int LEFT = 1;
    private final static int RIGHT = -1;
    private final int stepDistance = 10; // cm
    private int targetHeading = -1;
    private final double distanceThreshold = 20; //cm
    private LinkedList<Position> positionHistory;
    private Random ran = new Random();
    private Angle towerAngle;
    private double[] measurement;
    
    private final boolean debug = true;
    
    
    public BoundaryFollowingController(SimRobot robot) {
        this.robot = robot;
        distances = robot.getDistances();
        state = IDLE;
        lastState = state;
        positionHistory = new LinkedList<Position>();
        measurement = this.robot.getMeasurement();
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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                robot.stop();
                break;
            }
            if (paused) {
                continue;
            }
            
            
            
            switch (state)
            {
                case IDLE: // No obstacles measured nearby
                    if (debug) {
                        System.out.println("IDLE entered.");
                    }
                    // Get the heading to the boundary that is closest to the robot
                    int shortestDistanceHeading = getShortestDistanceHeading();
                    if (shortestDistanceHeading == -1) {
                        // Get random heading in the interval [0, 360)
                        targetHeading = ran.nextInt(359 + 1);
                    } else {
                        targetHeading = shortestDistanceHeading;
                    }
                    state = TRANSLATING;
                case TRANSLATING:
                    if (debug) {
                        System.out.println("TRANSLATING entered.");
                    }
                    if (robot.isTranslationFinished()) {
                        //robot.setMovement(targetHeading, stepDistance);
                        robot.setMovement(180, stepDistance);
                    }
                    
                    towerAngle = robot.getTowerAngle();
                    measurement = robot.getMeasurement();
                    
                    int obstacleLocation = Navigation.checkCollision(towerAngle, (int) measurement[0], (int) measurement[1]);
                    if (obstacleLocation != 0) {
                        robot.stop();
                        state = WALL_FOLLOWING;
                    }
                    break;
                case WALL_FOLLOWING:
                    if (debug) {
                        System.out.println("WALL_FOLLOWING entered.");
                    }
                    
                    robot.stop();
                    //measurement = robot.getMeasurement();
                    //int wallSide = Navigation.checkCollision(towerAngle, (int) measurement[0], (int) measurement[1]);
                    
                    
                    break;
                default:
                    if (debug) {
                        System.out.println("default reached.");
                    }
                    // should not reach here
                    break;
            }
            
        }
    }
    
    private void setFollowHeading(int obstacleHeading, int robotHeading) {
        if (obstacleHeading > robotHeading) {
            targetHeading = obstacleHeading - 90;
            if (targetHeading < 0) {
                targetHeading += 360;
            }
        } else if (obstacleHeading < robotHeading) {
            targetHeading = obstacleHeading + 90;
            if (targetHeading >= 360) {
                targetHeading -= 360;
            }
        } else {
            int turnDirection = ran.nextBoolean() ? 1 : -1;
            targetHeading = turnDirection * 90;
        }
    }
    
    /**
     * Checks the distances in every direction and returns the nearest one.
     * 
     * @return double the distance to the closest obstacle
     */
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
     * @return int in range [0,360) or -1
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
