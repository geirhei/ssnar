/*
 * This code is written as a part of a Master Thesis
 * the spring of 2016.
 *
 * Eirik Thon(Master 2016 @ NTNU)
 */
package no.ntnu.et.general;

import java.util.ArrayList;
import java.util.List;
import static no.ntnu.et.general.Position.distanceBetween;
import no.ntnu.et.simulator.Feature;

/**
 * This class is used to represent lines by specifying the start point of the
 * line, its direction and its length.'
 * 
 * @author Eirik Thon
 */
public class Line {
    
    private double[] start;
    
    private double[] direction;
    
    private double length;
    
    private Position a;
    private Position b;
    
    public Position p;
    public double theta;
    public double h;
    public double aPar;
    public double bPar;
    public double c;
    public Position pR;
    public Position pL;
    
    public static final double STD_W = 30.0;
    
    public Line(Position pL, Position pR) {
        this.theta = calculateTheta(pL, pR);
        this.aPar = findA(theta);
        this.bPar = findB(theta);
        this.pR = pR;
        this.pL = pL;
        this.p = getMidpoint(this.pL, this.pR);
        this.c = calculateC(this.theta, this.p.getXValue(), this.p.getYValue());
        this.h = distanceBetween(pL, pR) / 2;
    }
    
    /**
     * Creates a new Line object
     * @param start
     * @param direction
     * @param length 
     */
    public Line(double[] start, double[] direction, double length) {
        this.start = start;
        this.direction = direction;
        this.length = length;
    }
    
    /**
     * Constructor Line object used in line merge
     * @param a
     * @param b 
     */
    /*
    public Line(Position a, Position b) {
        this.a = a;
        this.b = b;
        this.length = Math.sqrt( Math.pow(b.getXValue() - a.getXValue(), 2) + Math.pow(b.getYValue() - a.getYValue(), 2) );
    }
    */
    /**
     * Empty constructor
     */
    private Line() {};

    /**
     * Returns the start position of the line
     * @return double[] start position 
     */
    public double[] getStart() {
        return start;
    }
    
    public static Position getMidpoint(Line line) {
        double midX = (line.getA().getXValue() + line.getB().getXValue()) / 2;
        double midY = (line.getA().getYValue() + line.getB().getYValue()) / 2;
        return new Position(midX, midY);
    }
    
    public static Position getMidpoint(Position a, Position b) {
        double midX = (a.getXValue() + b.getXValue()) / 2.0;
        double midY = (a.getYValue() + b.getYValue()) / 2.0;
        return new Position(midX, midY);
    }

    /**
     * Returns the two values specifying the direction of the line
     * @return double[]
     */
    public double[] getDirection() {
        return direction;
    }

    /**
     * Returns the length of the line
     * @return double
     */
    public double getLength() {
        return length;
    }
    
    private double getSlope() {
        return (b.getYValue() - a.getYValue()) / (b.getXValue() - a.getXValue());
    }
    
    public Position getA() {
        return a;
    }
    
    public Position getB() {
        return b;
    }
    
    /**
     * Calculates the perpendicular error of p1 relative to a line through p0 and p1.
     * 
     * @param p0
     * @param p1
     * @param p2
     * @return error value
     */
    public static double calculateError(Position p0, Position p1, Position p2) {
        Line line = new Line(p0, p1);
        double a = findA(line.theta);
        double b = findB(line.theta);
        double c = calculateC(line.theta, line.p.getXValue(), line.p.getYValue());
        return a * p2.getYValue() + b * p2.getXValue() + c;
    }
    
    private static double findA(double theta) {
        return -Math.sin(Math.toRadians(theta + 90));
    }
    
    private static double findB(double theta) {
        return -Math.cos(Math.toRadians(theta + 90));
    }
    
    public static double calculateC(double theta, double x, double y) {
        return -findA(theta) * y - findB(theta) * x;
    }
    
    /**
     * Helper method for calculating the angle between two lines. Positive direction
     * is counter-clockwise. Wraps to the interval [0,360).
     * 
     * @param p0
     * @param p1
     * @return double angle
     */
    public static double calculateTheta(Position p0, Position p1) {
        double theta = Math.toDegrees(Math.atan2(p1.getYValue() - p0.getYValue(), p1.getXValue() - p0.getXValue()));
        if (theta < 0) {
            theta += 360;
        }
        return theta;
    }
    
    public static boolean isLine(Position p0, Position p1, Position p2) {
        if (p0 == null || p1 == null || p2 == null) {
            return false;
        }
        return Math.abs(calculateError(p0, p1, p2)) <= STD_W / 2.0;
    }
    
    public static List<Line> detectLines(List<Position> observations) {
        if (observations == null || observations.size() < 3) {
            return null;
        }
        ArrayList<Line> lines = new ArrayList<Line>();
        
        int i = 0;
        while (i < observations.size() - 2) {
            Position p0 = observations.get(i);
            Position p1 = observations.get(i+1);
            Position p2 = observations.get(i+2);
            Position p3;
            if (i < observations.size() - 3) {
                p3 = observations.get(i+3);
            } else {
                p3 = null;
            }
            
            // Attempt to start a line
            Line newLine;
            double e;
            if (isLine(p0, p1, p2)) {
                newLine = new Line(p0, p2);
                e = calculateError(p0, p2, p1);
                i += 3;
            } else if (isLine(p0, p1, p3)) {
                newLine = new Line(p0, p3);
                e = calculateError(p0, p3, p1);
                i += 4;
            } else if (isLine(p0, p2, p3)) {
                newLine = new Line(p0, p3);
                e = calculateError(p0, p3, p2);
                i += 4;
            } else {
                i += 4;
                continue;
            }
            
            newLine.c -= 0.01 * e;
            double xPNew = newLine.p.getXValue() - e * newLine.bPar;
            double yPNew = newLine.p.getYValue() - e * newLine.aPar;
            newLine.p = new Position(xPNew, yPNew);
            
            // Extend line
            // Is the problem here? extendLine takes newLines as an argument several times?
            
            while (i < observations.size() && extendLine(observations.get(i), newLine)) {
                i++;
                System.out.println("Line extended!");
            }
            
            lines.add(newLine);
        }
        return lines;
    }
    
    /**
     * Attempts to extend the given line with the given position.
     * 
     * @param p Position
     * @param line Line
     * @return true if successful, false if not
     */
    public static boolean extendLine(Position p, Line line) {
        double e = calculateError(line.pL, line.pR, p);
        
        if (Math.abs(e) <= STD_W / 2.0) {
            double k_c = 0.01;
            line.c -= k_c * e;
            
            double dTheta = Math.toDegrees(Math.atan(e / line.h));
            double k_theta = 0.001;
            line.theta += k_theta * dTheta;
            
            // Update parameters
            line.aPar = findA(line.theta);
            line.bPar = findB(line.theta);
            
            // Project onto new line
            line.pR = projectOntoLine(p, line);
            line.pL = projectOntoLine(line.pL, line);
            
            // Update midpoint and h
            line.p = getMidpoint(line.pL, line.pR);
            line.h = distanceBetween(line.pL, line.p);
            
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean matchSegment(Line mapLine, Line line) {
        final double varTheta_m = 5.0;
        final double varTheta_o = 5.0;
        final double varC_o = 5.0;
        final double varC_m = 5.0;
        if (Math.pow(mapLine.theta - line.theta, 2) > varTheta_m + varTheta_o) {
            return false;
        }
        if (Math.pow(mapLine.c - line.c, 2) > varC_o + varC_m) {
            return false;
        }
        double x_o = line.p.getXValue();
        double x_m = mapLine.p.getXValue();
        double y_o = line.p.getYValue();
        double y_m = mapLine.p.getYValue();
        if (Math.pow(x_o - x_m, 2) + Math.pow(y_o - y_m, 2) > line.h + mapLine.h) {
            return false;
        }
        return true;
    }
    
    public static void updateMapSegment(Line observedLine, Line mapLine) {
        // Update c
        double k_c = 0.01;
        mapLine.c += k_c * (observedLine.c - mapLine.c);
        
        // Update theta
        double k_theta = 0.01;
        mapLine.theta += k_theta * (observedLine.theta - mapLine.theta);
        
        // Update parameters
        mapLine.aPar = findA(mapLine.theta);
        mapLine.bPar = findB(mapLine.theta);
        
        // Project endpoints onto corrected segment
        Position observedL = projectOntoLine(observedLine.pL, mapLine);
        Position observedR = projectOntoLine(observedLine.pR, mapLine);
        Position mapL = projectOntoLine(mapLine.pL, mapLine);
        Position mapR = projectOntoLine(mapLine.pR, mapLine);
        
        if (Position.distanceBetween(observedL, mapR) > Position.distanceBetween(mapL, mapR)) {
            mapLine.pL = observedL;
        } else {
            mapLine.pL = mapL;
        }
        if (Position.distanceBetween(observedR, mapL) > Position.distanceBetween(mapR, mapL)) {
            mapLine.pR = observedR;
        } else {
            mapLine.pR = mapR;
        }
        
        // Update midpoint and h
        mapLine.p = getMidpoint(mapLine.pL, mapLine.pR);
        mapLine.h = distanceBetween(mapLine.pL, mapLine.p);
    }
    
    public static Position projectOntoLine(Position p, Line line) {
        double x = Math.pow(line.aPar, 2) * p.getXValue() - line.aPar * line.bPar * p.getYValue() - line.bPar * line.c;
        double y = Math.pow(line.bPar, 2) * p.getYValue() - line.aPar * line.bPar * p.getXValue() - line.aPar * line.c;
        return new Position(x, y);
    }
    
    /**
     * Creates a new Line object similar to the input Feature object. The Line
     * object starts in the start position of the feature and ends in its end 
     * position
     * @param feature Feature
     * @return 
     */
    public static Line convertFeatureToLine(Feature feature){
        double[] start = new double[2];
        start[0] = feature.getStartPosition().getXValue();
        start[1] = feature.getStartPosition().getYValue();
        double length = feature.getLength();
        double xDiff = feature.getEndPosition().getXValue()-feature.getStartPosition().getXValue();
        double yDiff = feature.getEndPosition().getYValue()-feature.getStartPosition().getYValue();
        double[] vector = {xDiff/length, yDiff/length};
        return new Line(start, vector, length);
    }
    
    /**
     * Creates a new Line object between the two Position given in the input
     * parameters
     * @param startPos Position
     * @param endPos Position
     * @return Line
     */
    public static Line getLineBetweenPositions(Position startPos, Position endPos){
        double length = Position.distanceBetween(startPos, endPos);
        if(length == 0){
            return null;
        }
        double[] start = new double[2];
        start[0] = startPos.getXValue();
        start[1] = startPos.getYValue();
        double xDiff = endPos.getXValue()-startPos.getXValue();
        double yDiff = endPos.getYValue()-startPos.getYValue();
        double[] vector = {xDiff/length, yDiff/length};
        return new Line(start, vector, length);
    }
    
    /**
     * Determines if 3 given points are collinear
     * 
     * @param a
     * @param b
     * @param c
     * @return 
     */
    private static boolean isCollinear(Position a, Position b, Position c) {
        double x1 = a.getXValue();
        double y1 = a.getYValue();
        double x2 = b.getXValue();
        double y2 = b.getYValue();
        double x3 = c.getXValue();
        double y3 = c.getYValue();
        return Math.abs((y1 - y2) * (x1 - x3) - (y1 - y3) * (x1 - x2)) <= 0.5; // epsilon because of float comparison 1e-9
    }
    
    /**
     * Prints all the values of the Line object
     */
    public void print1(){
        System.out.println("Start: " + start[0] + ", " + start[1]);
        System.out.println("Direction: " + direction[0] + ", " + direction[1]);
        System.out.println("Length: " + length);
    }
    
    public void print() {
        System.out.println("Line:");
        System.out.print("pL: ");
        pL.print();
        System.out.print("pR: ");
        pR.print();
        System.out.print("theta: ");
        System.out.printf("%1.2f", theta);
        System.out.println();
        System.out.print("c: ");
        System.out.printf("%1.2f", c);
        System.out.println();
    }
}
