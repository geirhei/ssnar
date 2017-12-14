/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import no.ntnu.et.general.Angle;
import no.ntnu.et.general.Position;
import static no.ntnu.et.general.Position.angleBetween;
import no.ntnu.et.general.Utilities;

/**
 *
 * @author geirhei
 */
public class NxtMapping extends Thread {
    private boolean paused = false;
    private final SimRobot robot;
    public List<Position> observations;
    private final double robotWidth = 19.5;
    
    public NxtMapping(SimRobot robot) {
        this.robot = robot;
        observations = Collections.synchronizedList(new ArrayList<Position>());
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
        setName("NXT mapping");
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
                //robot.stop();
                break;
            }
            if (paused) {
                continue;
            }
            
            if (observations.size() >= 5) {
                System.out.println("Have 5 observations.");
            } else {
                continue;
            }
            
            Position s_0 = observations.get(0);
            Position s_1 = observations.get(1);
            Position s_2 = observations.get(2);
            // Check if distances greater than robotWidth in order to not block the way
            
            Angle theta_0 = angleBetween(new Position(0, 0), s_0); // returns negative angle, may need to be changed to positive
            theta_0.add(360);
            Angle theta_1 = angleBetween(new Position(0, 0), s_1);
            theta_1.add(360);
            Angle theta_2 = angleBetween(new Position(0, 0), s_2);
            theta_2.add(360);
            System.out.println("theta_0: " + theta_0.getValue());
            System.out.println("theta_1: " + theta_1.getValue());
            System.out.println("theta_2: " + theta_2.getValue());
            
        }
    }
    
    public void addObservation(double[] observation) {
        double theta = robot.getPose().getHeading().getValue() + robot.getTowerAngle().getValue();
        int r = (int) observation[0];
        //System.out.println("r: " + r);
        if (r <= 0 || r > 20) {
            return;
        }
        Position pos = Utilities.polar2cart((int) theta, r);
        synchronized (observations) {
            observations.add(pos);
        }
    }
}
