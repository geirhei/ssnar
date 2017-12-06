/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

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
    public static int checkCollision(final Angle towerAngle, final int rightSensor, final int forwardSensor) {
        int distance = 0;
        if (towerAngle.getValue() <= 30) {
            distance = forwardSensor;
        } else if (towerAngle.getValue() >= 60) {
            distance = rightSensor;
        }
        if (distance > 0 && distance < 20) {
            if (towerAngle.getValue() <= 30) {
                return 1;
            } else if (towerAngle.getValue() <= 60) {
                return -1;
            }
        }
        return 0;
    }
    
    /*
    public static int determineDirection(Angle towerAngle, int[] distances) {
        int objectHeading = getShortestDistanceAngle(distances);
        int towerHeading = (int) towerAngle.getValue();
        
    }
*/
}
