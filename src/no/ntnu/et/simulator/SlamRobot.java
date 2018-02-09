/*
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

import no.ntnu.et.general.Angle;
import no.ntnu.et.general.Pose;

/**
 *
 * @author geirhei
 */
public class SlamRobot extends SimRobot {
    static final double DEF_TURN_SPEED = 0.5f;
    static final double DEF_MOVE_SPEED = 0.1f;
    
    private double turnSpeed = DEF_TURN_SPEED;
    private double moveSpeed = DEF_MOVE_SPEED;
    
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
    }
    
    public double getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(double turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
    
    /**
     * Rotate or move the robot a little distance towards the target position.
     * The robot will not move if the movement leads to a collision between any
     * of the features in "allFeatures". If the destination is reached the robot
     * will not move. The estimated pose is also updated and the value of
     * "noise" is added to the estimate.
     *
     * @param allFeatures ArrayList<Feature>
     * @param noise double
     */
    @Override
    boolean moveRobot(double noise) {
        if (!rotationFinished) {
            if (Math.abs(measuredRotation) >= Math.abs(targetRotation)) {
                measuredRotation = 0;
                rotationFinished = true;
            }
            pose.rotate(new Angle(turnSpeed * rotationDirection));
            estimatedPose.rotate(new Angle((turnSpeed + noise) * rotationDirection));
            measuredRotation += (turnSpeed + noise) * rotationDirection;
        } else if (!translationFinished) {
            if (Math.abs(measuredDistance) >= Math.abs(targetDistance)) {
                measuredDistance = 0;
                translationFinished = true;
                return true;
            }
            Pose testPose = Pose.copy(pose);
            testPose.move(moveSpeed * movementDirection);
            estimatedPose.move((moveSpeed + noise) * movementDirection);
            measuredDistance += (moveSpeed + noise) * movementDirection;

        }

        return false;
    }
}
