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
    private int[] distances = new int[4];
    private boolean paused = false;
    private boolean roaming = false;
    private int state;
    private int lastState;
    private final static int IDLE = 0;
    private final static int TRANSLATING = 1;
    private final static int FOLLOWING = 2;
    private final static int WALL_FOLLOWING = 3;
    private final static int GRAZING = 4;
    private final static int LEFT = 1;
    private final static int RIGHT = -1;
    private final int stepDistance = 10; // cm
    private int targetHeading = -1;
    private final int distanceThreshold = 20; //cm
    private LinkedList<Position> positionHistory;
    private Random ran = new Random();
    //private Angle towerAngle;
    //private Angle robotHeading;
    private Angle shortestHeading;
    //private double[] measurement;
    private int obstacleLocation = 0;
    private Position targetPos;
    private int wallSide = -1;
    private final int maxVisualLength = 80;
    
    private final boolean debug = true;
    
    
    public BoundaryFollowingController(SimRobot robot) {
        this.robot = robot;
        //this.distances = robot.getDistances();
        for (int i = 0; i < distances.length; i++) {
            distances[i] = maxVisualLength;
        }
        state = IDLE;
        lastState = state;
        positionHistory = new LinkedList<Position>();
        targetPos = Position.copy(robot.getPose().getPosition());
        shortestHeading = new Angle(0);
        //measurement = this.robot.getMeasurement();
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
                Thread.sleep(50);
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
                    if (debug) {System.out.println("IDLE entered.");}
                    // Get the heading to the boundary that is closest to the robot
                    //int shortestDistanceHeading = getShortestDistanceHeading(0, 359);
                    //System.out.println("shortestDistance: " + shortestDistanceHeading);
                    //System.out.println("distances[5]: " + distances[5]);
                    int shortestDistanceHeading = -1;
                    if (shortestDistanceHeading == -1) {
                        //targetHeading = 170; // hard coded, should be random
                        //targetPos = Navigation.getTarget(new Angle(180), robot.getPose(), stepDistance);
                        robot.setTarget(0, 70);
                        //System.out.println("reached?");
                    }

                    state = TRANSLATING;
                case TRANSLATING:
                    if (debug) {System.out.println("TRANSLATING entered.");}
                    
                    if (robot.isTranslationFinished()) {
                        //targetPos = Navigation.getTarget(new Angle(180), robot.getPose(), stepDistance);
                        //robot.setTarget(targetPos.getXValue(), targetPos.getYValue());
                    }
                    //robotHeading = robot.getPose().getHeading();
                    //obstacleLocation = Navigation.checkCollision(robotHeading, distances);
                    
                    distances = Navigation.calculateDistances(robot.lastIrMeasurement, (int) robot.getTowerAngle().getValue());
                    System.out.println(distances[0]);
                    if (distances[0] > 0 && distances[0] < distanceThreshold) {
                        if (debug) {System.out.println("Stop!");}
                        robot.stop();
                        state = WALL_FOLLOWING;
                    }
                    
                    break;
                case WALL_FOLLOWING:
                    if (debug) {System.out.println("WALL_FOLLOWING entered.");}
                    
                    /*
                    if (wallSide == -1) {
                        wallSide = Navigation.checkCollision(robotHeading, distances);
                    }
                    */
                    /*
                    if (robot.isTranslationFinished()) {
                        int directionToWall = -1;
                        if (wallSide == LEFT) {
                            directionToWall = getShortestDistanceHeading(100, 260);
                        } else if (wallSide == RIGHT) {
                            int directionToWall_1 = getShortestDistanceHeading(280, 359);
                            int directionToWall_2 = getShortestDistanceHeading(0, 80);
                            if (directionToWall_1 < directionToWall_2) {
                                directionToWall = directionToWall_1;
                            } else {
                                directionToWall = directionToWall_2;
                            }
                        }
                        
                        System.out.println("wallDir: " + directionToWall);
                        shortestHeading = new Angle(directionToWall);
                        
                        targetPos = Navigation.calculateNewTarget(shortestHeading, robot.getPose().getPosition(), stepDistance, wallSide);
                        robot.setTarget(targetPos.getXValue(), targetPos.getYValue());
                    }
                    */
                    robot.stop();
                    
                    break;
                case GRAZING:
                    break;
                default:
                    if (debug) {System.out.println("default reached.");}
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
    private int getShortestDistanceHeading(int min, int max) {
        int shortestDistanceHeading = -1;
        double currentDistance = Double.POSITIVE_INFINITY;
        for (int i = min; i <= max; i++) {
            System.out.print(distances[i] + " ");
            if (distances[i] < currentDistance) {
                currentDistance = distances[i];
                shortestDistanceHeading = i;
            }
        }
        System.out.println();
        return shortestDistanceHeading;
    }
}
