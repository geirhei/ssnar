/*
 * This code is written as a part of a Master Thesis
 * the spring of 2016.
 *
 * Eirik Thon(Master 2016 @ NTNU)
 */
package no.ntnu.et.simulator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.JFrame;
import no.ntnu.et.general.Line;
import no.ntnu.et.general.Pose;
import no.ntnu.et.general.Position;
import no.ntnu.et.map.GridMap;
import no.ntnu.tem.communication.DroneUpdateMessage;
import no.ntnu.tem.communication.HandshakeMessage;
import no.ntnu.tem.communication.Message;
import no.ntnu.tem.communication.UpdateMessage;

/**
 * This class contains both the interface and the control system of the
 * simulator. This is the main class in the simulator package.
 *
 * @author Eirik Thon
 */
public class Simulator {

    private final SimWorld world;
    private SimulatorGUI gui;
    private boolean estimateNoiseEnabled;
    private boolean sensorNoiseEnabled;
    HashMap<String, RobotHandler> robotHandlers;
    private double simulationSpeed;
    private ConcurrentLinkedQueue<Message> inbox;
    private int mode;
    private HashMap<Integer, String> idNameMapping;
    private GridMap worldMap;

    /**
     * Constructor. Creates an instance of the Simulator class with a number of
     * robots given by the length of "robotNames" and map from the file
     * specified by "mapPath"
     *
     * @param robotNames String[]
     * @param mapPath String
     */
    public Simulator(ConcurrentLinkedQueue<Message> inbox, GridMap worldMap) {
        simulationSpeed = 1;
        world = new SimWorld();
        File mapFolder = new File("maps");
        File[] allMaps = mapFolder.listFiles();
        if (allMaps.length == 0) {
            System.out.println("No map files found");
        }
        String mapPath = allMaps[0].getAbsolutePath();
        world.initMap(mapPath);
        robotHandlers = new HashMap<String, RobotHandler>();
        estimateNoiseEnabled = true;
        sensorNoiseEnabled = true;
        this.mode = mode;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initializeGUI();
            }
        });
        this.inbox = inbox;
        this.worldMap = worldMap;
    }

    double getSimulationSpeed() {
        return simulationSpeed;
    }

    void setSimulationSpeed(double newSpeed) {
        simulationSpeed = (double) newSpeed / 100;
    }

    public void openGui() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(true);
            }
        });
    }

    public void closeGui() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.setVisible(false);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        // Create and run an instance if the Simulator class
    }

    /**
     * Stops the simulator, and closes the window
     */
    public void stop() {
        // Uses the closing routine defined in initializeGUI
        for (HashMap.Entry<String, RobotHandler> entry : robotHandlers.entrySet()) {
            entry.getValue().interrupt();
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gui.dispose();
            }
        });
    }

    public void createRobot(Pose initialPose, int RobotType) {
        SimRobot robot = world.createRobot(initialPose, RobotType);
        if (robot == null) {
            return;
        }
        if (!robotHandlers.containsKey(robot.getName())) {
            RobotHandler robotHandler = this.new RobotHandler(robot);
            robotHandler.setName(robot.getName());
            robotHandlers.put(robot.getName(), robotHandler);
            robotHandler.paused = true;
            robotHandlers.get(robot.getName()).start();
        }
    }

    public void disconnectRobot(String name) {
        if (robotHandlers.containsKey(name)) {
            robotHandlers.get(name).interrupt();
            robotHandlers.remove(name);
        }
    }

    /**
     * Turns estimation error on/off
     */
    void flipEstimateNoiseEnabled() {
        estimateNoiseEnabled = !estimateNoiseEnabled;
    }

    /**
     * Turns sensor error on/off
     */
    void flipSensorNoiseEnabled() {
        sensorNoiseEnabled = !sensorNoiseEnabled;
    }

    /**
     * Gives the command to the robot with name "name"
     * (changed to (x,y))
     *
     * @param name String
     * @param x double
     * @param y double
     */
    public void setRobotCommand(String name, double x, double y) {
        world.getRobot(name).setTarget(x, y);
    }

    public void pauseRobot(String name) {
        robotHandlers.get(name).pause();
    }

    public void unpauseRobot(String name) {
        robotHandlers.get(name).unpause();
    }

    /**
     * Initializes the graphical user interface
     */
    private void initializeGUI() {
        gui = new SimulatorGUI(this, world);
        gui.setTitle("Simulator");
        gui.setVisible(false);
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (!sensorNoiseEnabled) {
            gui.checkMeasurementErrorOff();
        }

        if (!estimateNoiseEnabled) {
            gui.checkEstimationErrorOff();
        }
    }

    /**
    * Controller object for a robot. This class contains a SimRobot object
    * and is responsible for making that robot run.
    */
    private class RobotHandler extends Thread {
        final private SimRobot myRobot;
        final private String myName;
        final private int myID;
        private double estimateNoise;
        private double sensorNoise;
        private final Random noiseGenerator;
        private boolean paused;
        public int update[];
        
        //private final BoundaryFollowingController boundaryFollowingController;
        private final NxtNavigation nxtNavigation;
        private final NxtMapping nxtMapping;
        

        /**
         * Constructor
         *
         * @param robot SimRobot
         */
        public RobotHandler(SimRobot robot) {
            myRobot = robot;
            myName = robot.getName();
            myID = robot.getId();
            noiseGenerator = new Random();
            paused = false;
            //boundaryFollowingController = new BoundaryFollowingController(myRobot);
            nxtNavigation = new NxtNavigation(myRobot);
            nxtMapping = new NxtMapping(myRobot, worldMap);
        }

        void pause() {
            paused = true;
        }

        void unpause() {
            paused = false;
        }

        /**
         * A continuous loop that calls methods of the robot in a specific way
         * in order to generate the robot behavior.
         */
        @Override
        public void run() {
            //boundaryFollowingController.start();
            nxtNavigation.start();
            //nxtMapping.start();
            
            int counter = 0;

            HandshakeMessage hm = myRobot.generateHandshake();
            byte[] hmBytes = hm.getBytes();
            byte[] hmMessageBytes = new byte[hmBytes.length + 1];
            hmMessageBytes[0] = Message.HANDSHAKE;
            System.arraycopy(hmBytes, 0, hmMessageBytes, 1, hmBytes.length);
            inbox.add(new Message(myRobot.getAddress(), hmMessageBytes));
            
            //
            Position s0 = new Position(4, 5);
            Position s1 = new Position(3, 4);
            Position s2 = new Position(2, 3);
            Position s3 = new Position(1, 2);
            Position s4 = new Position(0, 1);
            synchronized (myRobot.getObservations()) {
                myRobot.getObservations().add(s0);
                myRobot.getObservations().add(s1);
                myRobot.getObservations().add(s2);
                myRobot.getObservations().add(s3);
                myRobot.getObservations().add(s4);
            }
            
            //
            
            while (true) {
                // Wait between each loop
                try {
                    Thread.sleep((int) (10 / simulationSpeed));
                } catch (InterruptedException e) {
                    break;
                }
                if (paused) {
                    continue;
                }

                // Move robot
                if (estimateNoiseEnabled) {
                    //estimateNoise = noiseGenerator.nextGaussian() * 0.1;
                    estimateNoise = noiseGenerator.nextGaussian() * 0.01;
                } else {
                    estimateNoise = 0;
                }
                if (myRobot.moveRobot(estimateNoise) == true) {
                    inbox.add(new Message(myRobot.getAddress(), new byte[]{Message.IDLE}));
                }
                myRobot.turnTower();

                // Measure
                if (counter > 19) { // Every 200 ms ( Counter is increased every 10 ms )
                    if (sensorNoiseEnabled) {
                        sensorNoise = noiseGenerator.nextGaussian() * 2;
                    } else {
                        sensorNoise = 0;
                    }
                    myRobot.measureIR(sensorNoise);
                    update = myRobot.createMeasurement();
                    
                    if (myName.equals("SLAM")) {
                        myRobot.addObservation();
                        
                        UpdateMessage um = SimRobot.generateUpdate(update[0], update[1], update[2], update[3], update[4], update[5], update[6], update[7]);
                        byte[] umBytes = um.getBytes();
                        byte[] umMessageBytes = new byte[umBytes.length + 1];
                        umMessageBytes[0] = Message.UPDATE;
                        System.arraycopy(umBytes, 0, umMessageBytes, 1, umBytes.length);
                        inbox.add(new Message(myRobot.getAddress(), umMessageBytes));
                        
                        /*
                        Position a = new Position(10, 20);
                        Position b = new Position(20, 10);
                        Line newLine = new Line(a, b);
                        myRobot.getLineMap().add(newLine);
                        
                        if (myRobot.getLineMap().size() >= 1) {
                            DroneUpdateMessage dum = SimRobot.generateDroneUpdate(update[0], update[1], update[2], (int) a.getXValue(), (int) a.getYValue(), (int) b.getXValue(), (int) b.getYValue());
                            byte[] dumBytes = dum.getBytes();
                            byte[] dumMessageBytes = new byte[dumBytes.length + 1];
                            dumMessageBytes[0] = Message.DRONE_UPDATE;
                            System.arraycopy(dumBytes, 0, dumMessageBytes, 1, dumBytes.length);
                            inbox.add(new Message(myRobot.getAddress(), dumMessageBytes));
                            
                            myRobot.getLineMap().clear();
                        }
                        */
                    } else if (myName.equals("Drone")) {
                        DroneUpdateMessage um = SimRobot.generateDroneUpdate(update[0], update[1], update[2], update[4], update[5], update[6], update[7]);
                        byte[] umBytes = um.getBytes();
                        byte[] umMessageBytes = new byte[umBytes.length + 1];
                        umMessageBytes[0] = Message.DRONE_UPDATE;
                        System.arraycopy(umBytes, 0, umMessageBytes, 1, umBytes.length);
                        inbox.add(new Message(myRobot.getAddress(), umMessageBytes));
                    } else {
                        UpdateMessage um = SimRobot.generateUpdate(update[0], update[1], update[2], update[3], update[4], update[5], update[6], update[7]);
                        byte[] umBytes = um.getBytes();
                        byte[] umMessageBytes = new byte[umBytes.length + 1];
                        umMessageBytes[0] = Message.UPDATE;
                        System.arraycopy(umBytes, 0, umMessageBytes, 1, umBytes.length);
                        inbox.add(new Message(myRobot.getAddress(), umMessageBytes));
                    }

                    counter = 0;
                }
                counter++;
                
                if (myRobot.getObservations().size() >= 5) {
                    double theta = Math.toDegrees(Math.atan2(s2.getYValue() - s0.getYValue(), s2.getXValue() - s0.getXValue()));
                    if (theta < 0) {
                        theta += 360;
                    }
                    //System.out.println("theta: " + theta);
                    
                    Position a = new Position(4, 5);
                    Position b = new Position(2, 3);
                    
                    myRobot.getObservations().clear();
                }
                
            }
        }
    }
    
    
}
