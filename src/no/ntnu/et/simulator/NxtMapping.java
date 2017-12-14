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
import no.ntnu.et.general.Line;
import no.ntnu.et.general.Position;
import static no.ntnu.et.general.Position.angleBetween;
import no.ntnu.et.general.Utilities;
import no.ntnu.et.map.GridMap;
import no.ntnu.tem.application.Application;

/**
 *
 * @author geirhei
 */
public class NxtMapping extends Thread {
    private boolean paused = false;
    private final SimRobot robot;
    public List<Position> observations;
    private final double robotWidth = 19.5;
    private GridMap map;
    
    public NxtMapping(SimRobot robot, GridMap map) {
        this.robot = robot;
        observations = Collections.synchronizedList(new ArrayList<Position>());
        this.map = map;
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
            
            Position s0 = observations.get(0);
            Position s1 = observations.get(1);
            Position s2 = observations.get(2);
            // Check if distances greater than robotWidth in order to not block the way
            
            Angle theta0 = angleBetween(new Position(0, 0), s0); // returns negative angle, may need to be changed to positive
            Angle theta1 = angleBetween(new Position(0, 0), s1);
            Angle theta2 = angleBetween(new Position(0, 0), s2);
            theta0.add(360);
            theta1.add(360);
            theta2.add(360);
            double theta0Rad = Math.toRadians(theta0.getValue());
            double theta1Rad = Math.toRadians(theta1.getValue());
            double theta2Rad = Math.toRadians(theta2.getValue());
            
            System.out.println("theta0: " + theta0.getValue());
            System.out.println("theta1: " + theta1.getValue());
            System.out.println("theta2: " + theta2.getValue());
            
            //double a0 = Math.sin(theta0Rad);
            double a1 = Math.sin(theta1Rad);
            //double a2 = Math.sin(theta2Rad);
            //double b0 = -Math.cos(theta0Rad);
            double b1 = -Math.cos(theta1Rad);
            //double b2 = -Math.cos(theta2Rad);
            //double c0 = -a0 * s0.getXValue() + b0 * s0.getYValue();
            double c1 = -a1 * s1.getXValue() + b1 * s1.getYValue();
            //double c2 = -a2 * s2.getXValue() + b2 * s2.getYValue();
            //double e0 = a0 * s0.getXValue() - b0 * s0.getYValue() + c0;
            double e1 = a1 * s1.getXValue() - b1 * s1.getYValue() + c1;
            //double e2 = a2 * s2.getXValue() - b2 * s2.getYValue() + c2;
            System.out.println("e1: " + e1);
            
            double var_wi = 1;
            if (Math.abs(e1) <= var_wi / 2) {
                Line newLin1 = new Line(s0, s2);
                Line newLin2 = new Line(new Position(0, -25), new Position(50, 25));
                synchronized (map.lineArray) {
                    map.lineArray.add(newLin1);
                    map.lineArray.add(newLin2);
                }
                
                System.out.println("Length: " + newLin1.getLength());
                System.out.println("Line added.");
            }
            finished = true;
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
