/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

import static no.ntnu.et.general.Angle.sum;
import static no.ntnu.et.general.Utilities.polar2cart;

/**
 *
 * @author geirhei
 */
public class Navigation {
    
    /**
     * Method for retrieving the index of the array that has the lowest value
     * 
     * @param distances array [0,360)
     * @return int angle [0,360)
     */
    public static int getShortestDistanceAngle(int[] distances) {
        int angle = -1;
        int shortest = Integer.MAX_VALUE;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < shortest) {
                angle = i;
            }
        }
        return angle;
    }
    
    /**
     * Checks if the robot is in collision range with an obstacle, and return
     * which side the obstacle is on. 1 for left and -1 for right. 0 if no collision.
     * 
     * @param towerAngle relative to the robot
     * @param forwardSensor ir-data
     * @param rightSensor ir-data
     * @return true if it is close enough, false otherwise
     */
    /*
    public static int checkCollision(final Angle towerAngle, int rightSensor, int forwardSensor) {
        int distance = 0;
        if (towerAngle.getValue() <= 30) {
            distance = forwardSensor;
        } else if (towerAngle.getValue() >= 60) {
            distance = rightSensor;
        }
        if (distance > 0 && distance < 25) {
            if (towerAngle.getValue() <= 30) {
                return 1;
            } else if (towerAngle.getValue() >= 60) {
                return -1;
            }
        }
        return 0;
    }
    */
    
    public static int checkCollision(Angle robotHeading, double[] distances) {
        if (distances.length != 360) {
            return -2;
        }
        Angle robotHeadingMax = Angle.copy(robotHeading);
        Angle robotHeadingMin = Angle.copy(robotHeading);
        robotHeadingMax.add(30);
        robotHeadingMin.add(-30);
        int max = (int) robotHeadingMax.getValue();
        int min = (int) robotHeadingMin.getValue();
        //System.out.println("Max: " + max + ", " + "Min: " + min);
        int distanceHeading = -1;
        double threshold = 30;
        double current = Double.POSITIVE_INFINITY;
        for (int i = min; i <= max; i++) {
            //System.out.print(distances[i] + " ");
            if (distances[i] < current && distances[i] < threshold) {
                current = distances[i];
                distanceHeading = i;
            }
        }
        //System.out.println();
        if (distanceHeading == -1) {
            return 0;  
        } else if (distanceHeading >= robotHeading.getValue()) {
            return 1;
        } else if (distanceHeading < robotHeading.getValue()) {
            return -1;
        } else {
            return -2;
        }
    }
    
    public static Position getTarget(Angle heading, Pose currentPose, double stepDistance) {
        //Angle newHeading = sum(heading, currentPose.getHeading());
        double x = currentPose.getPosition().getXValue();
        double y = currentPose.getPosition().getYValue();
        double val = heading.getValue();
        x += Math.cos(Math.toRadians(val)) * stepDistance;
        y += Math.sin(Math.toRadians(val)) * stepDistance;
        return new Position(x, y);
    }
    
    public static Position calculateNewTarget(Angle shortestHeading, Position currentPos, double stepDistance, int wallSide) {
        if (wallSide != 1 && wallSide != -1) {
            System.out.println("Invalid wallSide! Must be -1 or 1.");
            return null;
        }
        Position offset = new Position(0, 0);
        if (wallSide == 1) { //LEFT
            Angle thetaOffset = Angle.copy(shortestHeading);
            thetaOffset.add(-90);
            offset = polar2cart(thetaOffset, stepDistance);
        } else if (wallSide == -1) { //RIGHT
            offset = polar2cart(shortestHeading, stepDistance);
        }
        Position target = Position.sum(currentPos, offset);
        return target;
    }
    
    /**
     * Returns -1 if all fields in []distances have the same infinity value
     * 
     * @return int in range [0,360) or -1
     */
    public static int getShortestDistanceHeading(double[] distances) {
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
    
    /*
    public static int determineDirection(Angle towerAngle, int[] distances) {
        int objectHeading = getShortestDistanceAngle(distances);
        int towerHeading = (int) towerAngle.getValue();
        
    }
*/
}
