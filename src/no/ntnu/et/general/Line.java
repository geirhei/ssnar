/*
 * This code is written as p part of p Master Thesis
 * the spring of 2016.
 *
 * Eirik Thon(Master 2016 @ NTNU)
 */
package no.ntnu.et.general;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import static no.ntnu.et.general.Position.distanceBetween;
import static no.ntnu.et.general.Utilities.getProjectedPoint;
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
    
    public Position p;
    public Position q;
    
    /*
    public Position p;
    public double theta;
    public double h;
    public double aPar;
    public double bPar;
    public double c;
    public Position pR;
    public Position pL;
    public static final double STD_W = 30.0;
    public static final double VAR_C = Math.pow(STD_W, 2) * Math.pow(STD_W, 2) / (Math.pow(STD_W, 2) + Math.pow(STD_W, 2));
    public static final double VAR_I = 1.0;
    */
    
    static final double TOLERANCE = 50.0;
    
    /*
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
    */
    
    /**
     * Creates p new Line object
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
     * @param p
     * @param q 
     */
    
    public Line(Position p, Position q) {
        this.p = p;
        this.q = q;
        //this.length = Math.sqrt( Math.pow(q.getXValue() - p.getXValue(), 2) + Math.pow(q.getYValue() - p.getYValue(), 2) );
    }
    
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
        return (q.getYValue() - p.getYValue()) / (q.getXValue() - p.getXValue());
    }
    
    public Position getA() {
        return p;
    }
    
    public Position getB() {
        return q;
    }
    
    /*
    /**
     * Calculates the perpendicular error of p2 relative to p line through p0 and p1.
     * 
     * @param p0
     * @param p1
     * @param p2
     * @return error value
     */
    /*
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
    /*
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
    
    /**
     * TODO: Distance between points must be less than the width of the robot,
 else p possible passage may be blocked.
     * 
     * @param observations
     * @param clockwise
     * @return ArrayList of detected line segments
     */
    /*
    public static List<Line> detectLines(List<Position> observations, boolean clockwise) {
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
            
            // Attempt to start p line
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
            while (i < observations.size() && extendLine(observations.get(i), newLine, clockwise)) {
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
    /*
    public static boolean extendLine(Position p, Line line, boolean clockwise) {
        double e = calculateError(line.pL, line.pR, p);
        
        if (Math.abs(e) <= STD_W / 2.0) {
            double k_c = 0.01;
            //double k_c = VAR_C / (VAR_C + Math.pow(STD_W, 2));
            line.c -= k_c * e;
            
            double dTheta = Math.toDegrees(Math.atan(e / line.h));
            double k_theta = 0.001;
            if (clockwise) {
                line.theta -= k_theta * dTheta;   
            } else {
                line.theta += k_theta * dTheta;
            }
            
            // Update parameters
            line.aPar = findA(line.theta);
            line.bPar = findB(line.theta);
            
            // Project onto new line
            if (clockwise) {
                line.pR = projectOntoLine(line.pR, line);
                line.pL = projectOntoLine(p, line);
            } else {
                line.pR = projectOntoLine(p, line);
                line.pL = projectOntoLine(line.pL, line);
            }
            
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
    */
    /**
     * 
     * @param pointBuffer
     * @param lineBuffer
     * @param bufferSize number of values in pointBuffer
     */
    public static void lineCreate1(Position[] pointBuffer, Line[] lineBuffer, int bufferSize) {
        if (pointBuffer == null || bufferSize < 2) {
            return;
        }
        
        int lineIndex = 0;
        Position a = pointBuffer[0];
        Position b = pointBuffer[1];
        if (bufferSize == 2) {
            Line line = new Line(a, b);
            lineBuffer[lineIndex] = line;
            return;
        }
        
        for (int i = 2; i < bufferSize; i++) {
            Line line;
            if (isCollinear(a, b, pointBuffer[i])) {
                if (i == bufferSize - 1) {
                    line = new Line(a, pointBuffer[i]);
                } else {
                    continue;
                }
            } else {
                line = new Line(a, pointBuffer[i-1]);
                a = pointBuffer[i];
                b = pointBuffer[i+1];
                if (i < bufferSize - 2) {
                    i++;
                }
            }
            lineBuffer[lineIndex] = line;
            lineIndex++;
        }
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
    
    public static Line mergeSegments(Line line1, Line line2) {
        double a1 = (line1.q.getYValue() - line1.p.getYValue()) / (line1.q.getXValue() - line1.q.getXValue());
        double a2 = (line2.q.getYValue() - line2.p.getYValue()) / (line2.q.getXValue() - line2.q.getXValue());
        double b1 = line1.p.getYValue() - a1 * line1.p.getXValue();
        double b2 = line2.p.getYValue() - a2 * line2.p.getXValue();
        double l1 = Position.distanceBetween(line1.p, line1.q);
        double l2 = Position.distanceBetween(line2.p, line2.q);
        
        // Find parameters for the merged line
        double a = (l1 * a1 + l2 * a2) / (l1 + l2);
        double b = (l1 * b1 + l2 * b2) / (l1 + l2);
        
        // Find projections of all 4 points onto merged line
        Position[] projectedPoints = new Position[4];
        projectedPoints[0] = getProjectedPoint(line1.p, a, b);
        projectedPoints[1] = getProjectedPoint(line2.p, a, b);
        projectedPoints[2] = getProjectedPoint(line1.q, a, b);
        projectedPoints[3] = getProjectedPoint(line2.q, a, b);
        
        // Find the points farthest away from each other
        Position p = projectedPoints[0];
        Position q = projectedPoints[0];
        for (int i = 1; i < projectedPoints.length; i++) {
            if (projectedPoints[i].getXValue() < p.getXValue()) {
                p = projectedPoints[i];
            }
            if (projectedPoints[i].getXValue() > p.getXValue()) {
                q = projectedPoints[i];
            }
        }
        
        return new Line(p, q);
    }
    
    public static void lineMerge1(Line[] lineBuffer, Line[] lineRepo) {
        
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
     * Minimum distance from the endpoint of p line-segment to the other line-segment.
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
     * Creates p new Line object similar to the input Feature object. The Line
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
     * Creates p new Line object between the two Position given in the input
 parameters
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
        return Math.abs((y1 - y2) * (x1 - x3) - (y1 - y3) * (x1 - x2)) <= TOLERANCE; // epsilon because of float comparison 1e-9
    }
    
    /**
     * Prints all the values of the Line object
     */
    public void print1(){
        System.out.println("Start: " + start[0] + ", " + start[1]);
        System.out.println("Direction: " + direction[0] + ", " + direction[1]);
        System.out.println("Length: " + length);
    }
    
    /*
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
    */
}
