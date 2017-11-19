/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.simulator;

/**
 *
 * @author geirhei
 */
public class BoundaryFollowingController extends Thread {
    //private final double[] distances;
    private boolean paused = false;
    
    public BoundaryFollowingController() {
        //this.distances = distances;
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
                //stopAllRobots();
                break;
            }
            if (paused) {
                continue;
            }
        }
    }
    
    
}
