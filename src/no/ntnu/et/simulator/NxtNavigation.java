/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

import no.ntnu.et.general.Position;

/**
 *
 * @author geirhei
 */
public class NxtNavigation extends Thread {
    private boolean paused = false;
    private final SimRobot robot;
    
    public NxtNavigation(SimRobot robot) {
        this.robot = robot;
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
        setName("NXT navigation");
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
            
            Position target = new Position(15, 50);
            //robot.setTarget(target.getXValue(), target.getYValue());
            finished = true;
        }
    }
}
