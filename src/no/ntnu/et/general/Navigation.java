/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

import static no.ntnu.et.general.Utilities.polar2cart;

/**
 *
 * @author geirhei
 */
public class Navigation {
    
    /**
     * Method for retrieving the index of the array that has the lowest value
     * 
     * @param circleArr array [0,360)
     * @return int angle [0,360). -1 if no distances are measured in any direction
     */
    public static int getShortestDistanceHeading(int[] circleArr) {
        int angle = -1;
        int shortest = 40;
        for (int i = 0; i < circleArr.length; i++) {
            if (circleArr[i] < shortest) {
                angle = i;
            }
        }
        return angle;
    }
    
    /**
     * Checks if distance value is below threshold, and returns the side of the
     * robot that is closest to the obstacle. 1 is front, 2 is left, and so on.
     * 0 represents 'no collision'
     * 
     * @param distances
     * @param towerAngle
     * @param threshold
     * @return 
     */
    public static int checkCollision(int[] distances, int towerAngle, int threshold) {
        if (distances.length != 4) {
            throw new IllegalArgumentException("Length of distances[] must be 4");
        }
        
        int res = 0;
        if (distances[0] > 0 && distances[0] < threshold) {
            if (towerAngle == 0) {
                res = 1;
            } else if (towerAngle > 0 && towerAngle <= 30) {
                res = 2;
            } else if (towerAngle >= 60 && towerAngle <= 90) {
                res = 4;
            }
        } else if (distances[1] > 0 && distances[1] < threshold) {
            res = 2;
        } else if (distances[3] > 0 && distances[3] < threshold) {
            res = 4;
        }
        return res;
    }
    
    /**
     * Calculates and fills an int[4] array with the measured distances in all four directions around the robot.
     * In the order [d_front, d_left, d_back, d_right]
     * Only updates values when servo step is below 30 and above 60. Else the distances are unchanged,
     * or set to max line of sight if == 0
     * 
     * @param measurements double[]
     * @param distances int[] containing the distances in the respective distances around the robot.
     * @param servoStep the number of steps the servo tower is rotated
     * @return updated distance array of integers
     */
    public static int[] calculateDistances(double[] measurements, int[] distances, int servoStep) {
        if (measurements.length != 4) {
            System.out.println("Invalid length of []measuremenets.");
            return null;
        }
        if (servoStep < 0 || servoStep > 90) {
            System.out.println("Servo step out of range.");
            return null;
        }
        
        double servoStepRad = Math.toRadians(servoStep);
        if (servoStep >= 0 && servoStep <= 30) {
            for (int i = 0; i < measurements.length; i++) {
                distances[i] = (int) (measurements[i] * Math.cos(servoStepRad));
            }
        } else if (servoStep >= 60 && servoStep <= 90) {
            distances[0] = (int) (measurements[3] * Math.sin(servoStepRad));
            for (int i = 1; i < measurements.length; i++) {
                distances[i] = (int) (measurements[i-1] * Math.sin(servoStepRad));
            }
        }
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] == 0 || distances[i] > 40) {
                distances[i] = 40;
            }
        }
        return distances;
    }
    
    
    public static Position calculateNewTarget(Pose currentPose, double error, double stepDistance) {
        /*
        if (Math.abs(error) > stepDistance) {
            error = (double) stepDistance / 10.0;
        }
        */
        //System.out.println("error: " + error);
        double thetaOffsetRad = Math.asin((double)error / (double)stepDistance);
        //System.out.println("thetaOffsetRad: " + thetaOffsetRad);
        double thetaOffset = Math.toDegrees(thetaOffsetRad);
        //System.out.println("thetaOffset: " + thetaOffset);
        /*
        if (thetaOffset < -45) {
            thetaOffset = -45;
        } else if (thetaOffset > 45) {
            thetaOffset = 45;
        }
        */
        double thetaTarget = currentPose.getHeading().getValue() + thetaOffset;
        // System.out.println("theta: " + theta);
        Position offset = polar2cart((int) thetaTarget, stepDistance);
        Position target = Position.sum(currentPose.getPosition(), offset);
        //System.out.println("x: " + target.getXValue() + ", y: " + target.getYValue());
        return target;
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
    
    public static void updateCircleArr(int[] circleArr, double[] measurement, int robotHeading, int servoStep) {
        if (circleArr.length != 360 || measurement.length != 4) {
            System.out.println("Invalid array length.");
            return;
        }
        
        for (int i = 0; i < measurement.length; i++) {
            int currentAngle = robotHeading + servoStep + i * 90;
            if (currentAngle >= 360) {
                currentAngle -= 360;
            } else if (currentAngle < 0) {
                currentAngle += 360;
            }
            if (measurement[i] > 0 && measurement[i] <= 40) {
                circleArr[currentAngle] = (int) measurement[i];
            } else {
                circleArr[currentAngle] = Integer.MAX_VALUE;
            }
        }
    }
    
}
