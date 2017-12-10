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
    private final static int FORWARDS = 1;
    private final static int LEFT = 2;
    private final static int BACKWARDS = 3;
    private final static int RIGHT = 4;
    private final int stepDistance = 10; // cm
    private int targetHeading = -1;
    private final int distanceThreshold = 30; //cm
    private LinkedList<Position> positionHistory;
    private Random ran = new Random();
    //private Angle towerAngle;
    //private Angle robotHeading;
    private Angle shortestHeading;
    private int obstacleLocation = 0;
    private Position targetPos;
    private final int maxVisualLength = 80;
    private int obstacleSide = -1;
    private int newDistance = maxVisualLength;
    private int currentDistance = newDistance;
    private final int targetDistance = 20;
    
    private final boolean debug = true;
    
    
    public BoundaryFollowingController(SimRobot robot) {
        this.robot = robot;
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
            
            distances = Navigation.calculateDistances(robot.lastIrMeasurement, (int) robot.getTowerAngle().getValue());
            
            switch (state)
            {
                case IDLE: // No obstacles measured nearby
                    if (debug) {System.out.println("IDLE entered.");}
                    robot.setTarget(0, 80);
                    state = TRANSLATING;
                    
                case TRANSLATING:
                    if (debug) {System.out.println("TRANSLATING entered.");}
                    
                    if (robot.isTranslationFinished()) {
                        //targetPos = Navigation.getTarget(new Angle(180), robot.getPose(), stepDistance);
                        //robot.setTarget(targetPos.getXValue(), targetPos.getYValue());
                    }
                    
                    obstacleSide = Navigation.checkCollision(distances, (int) robot.getTowerAngle().getValue(), distanceThreshold);
                    if (obstacleSide != 0) {
                        if (debug) {System.out.println("Stop!");}
                        robot.stop();
                        state = WALL_FOLLOWING;
                    }
                    break;
                    
                case WALL_FOLLOWING:
                    //if (debug) {System.out.println("WALL_FOLLOWING entered.");}
                    
                    if (obstacleSide == -1) {
                        System.out.println("obstacleSide == -1");
                        break;
                    }
                    
                    if (robot.isTranslationFinished()) {
                        if (obstacleSide == LEFT) {
                            //System.out.println("entered obstacleSide == LEFT");
                            if (distances[1] != 80) {
                                currentDistance = distances[1];
                            }
                            System.out.println("currentDistance: " + currentDistance);
                            int error = targetDistance - currentDistance;
                            Position newTarget = Navigation.calculateNewTarget(robot.getPose(), error, stepDistance);
                            robot.setTarget(newTarget.getXValue(), newTarget.getYValue());
                        } else if (obstacleSide == RIGHT) {
                            currentDistance = distances[3];
                            int error = -(targetDistance - currentDistance);
                        } else if (obstacleSide == FORWARDS) {
                            
                        }
                    }
                    
                    //robot.stop();
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
