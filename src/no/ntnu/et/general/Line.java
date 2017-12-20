/*
 * This code is written as a part of a Master Thesis
 * the spring of 2016.
 *
 * Eirik Thon(Master 2016 @ NTNU)
 */
package no.ntnu.et.general;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import static no.ntnu.et.general.Position.distanceBetween;
import no.ntnu.et.map.MapLocation;
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
    public double varTheta;
    public double varC;
    public double aPar;
    public double bPar;
    public double c;
    public Position pR;
    public Position pL;
    
    public Line(Position pL, Position pR) {
        this.theta = calculateTheta(pL, pR);
        this.aPar = Math.sin(Math.toRadians(theta + 90));
        this.bPar = Math.cos(Math.toRadians(theta + 90));
        this.varC = 0.0;
        this.pR = pR;
        this.pL = pL;
        this.p = getMidpoint(this.pL, this.pR);
        this.c = this.aPar * this.p.getXValue() - this.bPar * this.p.getYValue();
        this.h = distanceBetween(pL, pR) / 2;
        //this.varTheta = Math.toDegrees(Math.atan(this.varC / this.h));
        this.varTheta = 0.0;
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
        double midX = (a.getXValue() + b.getXValue()) / 2;
        double midY = (a.getYValue() + b.getYValue()) / 2;
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
        double theta = calculateTheta(p0, p1);
        double a = Math.sin(Math.toRadians(theta + 90));
        double b = Math.cos(Math.toRadians(theta + 90));
        double c = calculateC(theta, p0.getXValue(), p0.getYValue());
        return a * p2.getXValue() - b * p2.getYValue() + c;
    }
    
    public static double calculateC(double theta, double x, double y) {
        double a = Math.sin(Math.toRadians(theta));
        double b = Math.cos(Math.toRadians(theta));
        return a * x - b * y;
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
        double std_w = 10.0; // cludged
        return Math.abs(calculateError(p0, p1, p2)) <= std_w / 2.0;
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
            
            newLine.c -= e;
            double xPNew = newLine.p.getXValue() - e * newLine.aPar;
            double yPNew = newLine.p.getYValue() - e * newLine.bPar;
            newLine.p = new Position(xPNew, yPNew);
            
            // Extend line
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
        double std_w = 10.0; // cludged
        double e = line.aPar * p.getXValue() - line.bPar * p.getYValue() + line.c;
        
        if (Math.abs(e) <= std_w / 2.0) {
            //line.varC = 0.5; // cludged
            line.varC = 0.0;
            //double k_c = line.varC / (line.varC + Math.pow(std_w, 2));
            double k_c = 1.0;
            line.c -= k_c * e;
            line.varC -= k_c * line.varC;
            
            double dTheta = Math.toDegrees(Math.atan(e / line.h));
            double stdTheta = 0.0;
            //double stdTheta = Math.toDegrees(Math.atan(std_w / line.h));
            line.varTheta = Math.pow(stdTheta, 2);
            
            //double k_theta = line.varTheta / (line.varTheta + Math.pow(stdTheta, 2));
            double k_theta = 1.0;
            line.theta += k_theta * dTheta;
            line.varTheta += k_theta * line.varTheta;
            
            // Update parameters
            line.aPar = Math.sin(Math.toRadians(line.theta + 90));
            line.bPar = Math.cos(Math.toRadians(line.theta + 90));
            
            // Project onto new line
            line.pR = projectOntoLine(line.pR, line);
            line.pL = projectOntoLine(p, line);
            
            // Update midpoint and h
            line.p = getMidpoint(line.pL, line.pR);
            line.h = distanceBetween(line.pL, line.p);
            
            /***
            Update uncertainties
            ***/
            
            return true;
        } else {
            return false;
        }
    }
    
    // untested
    public static Position projectOntoLine(Position p, Line line) {
        double x = p.getXValue() * Math.pow(line.bPar, 2) - p.getYValue() * line.aPar * line.bPar + line.aPar * line.c;
        double y = p.getXValue() * line.aPar * line.bPar + p.getYValue() * Math.pow(line.aPar, 2) + line.bPar * line.c;
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
    
    public static void lineCreate(ArrayList<Position> pointBuffer, List<Line> lineBuffer) {
        if (pointBuffer.size() <= 1) {
            pointBuffer.clear();
            return;
        } else if (pointBuffer.size() == 2) {
            Line line = new Line(pointBuffer.get(0), pointBuffer.get(1));
            lineBuffer.add(line);
            pointBuffer.clear();
            return;
        }
        
        Position a = pointBuffer.get(0);
        Position b = pointBuffer.get(1);
        
        int i = 2;
        while (i < pointBuffer.size()) {
            Line line;
            if (i == pointBuffer.size() - 1) {
                if (!isCollinear(a, b, pointBuffer.get(i))) {
                    line = new Line(a, pointBuffer.get(i-1));
                } else {
                    line = new Line(a, pointBuffer.get(i));
                }
                i++;
            } else if (i == pointBuffer.size() - 2) {
                if (!isCollinear(a, b, pointBuffer.get(i))) {
                    line = new Line(a, pointBuffer.get(i-1));
                    a = pointBuffer.get(i);
                    b = pointBuffer.get(i+1);
                    i++;
                } else {
                    i++;
                    break;
                }
            } else {
                if (!isCollinear(a, b, pointBuffer.get(i))) {
                    line = new Line(a, pointBuffer.get(i-1));
                    a = pointBuffer.get(i);
                    b = pointBuffer.get(i+1);
                    i += 2;
                } else {
                    i++;
                    break;
                }
            }
            
            System.out.println("length :" + line.getLength());
            // Discard lines that are too long
            if (line.getLength() < 20) {
                lineBuffer.add(line);
            }
            
        }
        pointBuffer.clear();
    }
    
    public static void lineMerge(List<Line> lineBuffer, List<Line> lineRepository) {
        // Array size check here

        if (lineRepository.isEmpty()) {
            synchronized (lineRepository) {
                for (Line bufferLine : lineBuffer) {
                    lineRepository.add(bufferLine);
                }
            }
            lineBuffer.clear();
            return;
        }
          
        double u = 0; // slope tolerance
        double d = 0; // distance tolerance
        
        ArrayList<Line> toAdd = new ArrayList<Line>();
        ListIterator<Line> iter1 = lineBuffer.listIterator();
        while (iter1.hasNext()) {
            Line bufferLine = iter1.next();
            boolean merged = false;
            double m1 = bufferLine.getSlope();
            synchronized (lineRepository) {
                ListIterator<Line> iter2 = lineBuffer.listIterator();
                while (iter2.hasNext()) {
                    Line line = iter2.next();
                    double m2 = line.getSlope();
                    if (!(Math.abs(m1 - m2) <= u)) {
                        continue;
                    }
                    double dist1 = Position.distanceBetween(bufferLine.getA(), line.getA());
                    double dist2 = Position.distanceBetween(bufferLine.getA(), line.getB());
                    double dist3 = Position.distanceBetween(bufferLine.getB(), line.getA());
                    double dist4 = Position.distanceBetween(bufferLine.getB(), line.getB());
                    
                    if (dist1 <= d || dist4 <= d) {
                        Line newLine = bufferLine;
                        iter2.set(newLine);
                    } else if (dist2 <= d) {
                        Line newLine = new Line(line.getA(), bufferLine.getB());
                        iter2.set(newLine);
                    } else if (dist3 <= d) {
                        Line newLine = new Line(bufferLine.getA(), line.getB());
                        iter2.set(newLine);
                    } else {
                        continue;
                    }
                    merged = true;
                }
            }
            if (!merged) {
                toAdd.add(bufferLine);
            }
        }
        
        // Add non-merged lines to end of lineRepository
        synchronized (lineRepository) {
            for (Line bufferLine : toAdd) {
                lineRepository.add(bufferLine);
            }
        }
        lineBuffer.clear();
    }
    
    public static void lineMerge1(List<Line> buffer, List<Line> repository) {
        if (repository.isEmpty()) {
            synchronized (repository) {
                for (Line bufferLine : buffer) {
                    repository.add(bufferLine);
                }
            }
            buffer.clear();
            return;
        }
        
        // is meargeable?
    }
    
    static boolean isMergeable(Line lineA, Line lineB) {
        //u1
        double slopeA = lineA.getSlope();
        double slopeB = lineB.getSlope();
        double angle = Math.abs(slopeB - slopeA);
        double u1 = calculateU1(angle);
        System.out.println("u1: " + u1);
        
        // u2
        Position midA = getMidpoint(lineA);
        Position midB = getMidpoint(lineB);
        // double u2 = calculateU2()
        
        //u3
        double midPointDist = Position.distanceBetween(midA, midB);
        double lengthA = lineA.getLength();
        double lengthB = lineB.getLength();
        double u3 = calculateU3(midPointDist, lengthA, lengthB);
        System.out.println("u3: " + u3);
        
        //u4
        // double u4 = calculateU4()
        
        //double similarityThreshold = 0.6;
        return (u1 >= 0.6 && u3 >= 0.6); // add u2 and u4
    }
    
    /**
     * Angle between the two line segments.
     * 
     * @param angle
     * @return 
     */
    static double calculateU1(double angle) {
        double a = 10.0;
        double b = 20.0;
        double res = 1.0;
        if (angle >= 0 && angle < a) {
            res = 1.0 - 0.5 / a * angle;
        } else if (angle >= a && angle <= b) {
            res = 0.5 - 0.5 / (b - a) * (angle - a);
        } else if (angle > b) {
            res = 0.0;
        }
        return res;
    }
    
    /**
     * Maximum distance of each line's midpoint to the other line.
     * 
     * @param dist
     * @return 
     */
    static double calculateU2(double dist) {
        double c = 10.0;
        double d = 30.0;
        double e = 60.0;
        double res = 1.0;
        if (dist > c && dist <= d) {
            res = 0.5;
        } else if (dist > d && dist <= e) {
            res = 0.5 - 0.5 / (e - d) * (dist - d);
        } else if (dist > e) {
            res = 0.0;
        }
        return res;
    }
    
    /**
     * Distance between the midpoints of the two-line segments.
     * 
     * @param dist
     * @param lenA
     * @param lenB
     * @return 
     */
    static double calculateU3(double dist, double lenA, double lenB) {
        double f;
        if (lenA <= lenB) {
            f = lenA;
        } else {
            f = lenB;
        }
        double g = f + 10.0;
        double res = 1.0;
        if (dist > f && dist < g) {
            res = 1.0 - 1.0 / (g - f) * (dist - f);
        } else if (dist >= g) {
            res = 0.0;
        }
        return res;
    }
    
    /**
     * Minimum distance from the endpoint of a line-segment to the other line-segment.
     * 
     * @param dist
     * @return 
     */
    static double calculateU4(double dist) {
        double h = 10.0;
        double i = 20.0;
        double res = 1.0;
        if (dist > h && h < i) {
            res = 1.0 - 1.0 / (i - h) * (dist - h);
        } else if (dist >= i) {
            res = 0.0;
        }
        return res;
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
        System.out.println("pL(x: " + pL.getXValue() + ", y: " + pL.getYValue() + "), pR(x: " + pR.getXValue() + ", y: " + pR.getYValue() + ")");
        System.out.println("theta: " + theta);
        System.out.println("c: " + c);
    }
}
