/*
 * This code is written as p part of p Master Thesis
 * the spring of 2016.
 *
 * Eirik Thon(Master 2016 @ NTNU)
 */
package no.ntnu.et.general;

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
    
    static final double TOLERANCE = 30.0;
    
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
        double midX = (line.getP().getXValue() + line.getQ().getXValue()) / 2;
        double midY = (line.getP().getYValue() + line.getQ().getYValue()) / 2;
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
    
    /**
     * Returns the slope of the line.
     * 
     * @return double
     */
    private double getSlope() {
        return (q.getYValue() - p.getYValue()) / (q.getXValue() - p.getXValue());
    }
    
    public Position getP() {
        return p;
    }
    
    public Position getQ() {
        return q;
    }
    
    /**
     * Finds and extract line segments from a set of points in sequence.
     * 
     * @param pointBuffer
     * @param lineBuffer
     * @param bufferSize
     * @return the number of lines created
     */
    public static int lineCreate(Position[] pointBuffer, Line[] lineBuffer, int bufferSize) {
        //if (pointBuffer == null || lineBuffer == null) {
        //    throw new NullPointerException("Buffers cannot be null.");
        //}
        /*
        if (pointBuffer[bufferSize-1] == null) {
            throw new NullPointerException("Buffers cannot be null.");
        }
        */
        /*
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("Buffer size cannot be zero or less.");
        }
        */
        
        if (bufferSize < 2) {
            return 0;
        }
        
        int lineIndex = 0;
        Position a = pointBuffer[0];
        Position b = pointBuffer[1];
        if (bufferSize == 2) {
            Line line = new Line(a, b);
            lineBuffer[lineIndex] = line;
            return 1;
        }
        
        for (int i = 2; i < bufferSize; i++) {
            Line line;
            if (areCollinear(a, b, pointBuffer[i])) {
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
        return lineIndex;
    }
    
    /**
     * Compares each line in lineBuffer with all lines in lineRepo, and replaces
     * the one in the repo with the merged if possible. If a line in lineBuffer
     * cannot be merged is added to the end of the repo.
     * 
     * @param lineBuffer
     * @param lineRepo
     * @param bufferCtr
     * @param repoCtr 
     * @return  
     */
    public static int lineMerge(Line[] lineBuffer, Line[] lineRepo, int bufferCtr, int repoCtr, boolean[] updated) {
        if (lineBuffer == null || lineRepo == null) {
            throw new NullPointerException("Invalid line arguments.");
        }
        
        if (repoCtr == 0) {
            for (int i = 0; i < bufferCtr; i++) {
                lineRepo[repoCtr] = lineBuffer[i];
                updated[repoCtr] = true;
                repoCtr++;
            }
        } else {
            int index = repoCtr;
            boolean merged = false;
            for (int j = 0; j < bufferCtr; j++) {
                for (int k = 0; k < repoCtr; k++) {
                    if (isMergeable(lineBuffer[j], lineRepo[k])) {
                        lineRepo[k] = mergeSegments(lineBuffer[j], lineRepo[k]);
                        updated[k] = true;
                        merged = true;
                        break;
                    }
                }
                if (!merged) {
                    lineRepo[index] = lineBuffer[j];
                    updated[index] = true;
                    index++;
                    merged = false;
                }
            }
            repoCtr = index;
        }
        return repoCtr;
    }
    
    /**
     * Determines if two lines satisfy the conditions for merging.
     * 
     * @param line1
     * @param line2
     * @return true if mergeable, else false
     */
    public static boolean isMergeable(Line line1, Line line2) {
        if (line1 == null || line2 == null) {
            throw new NullPointerException("Invalid line arguments.");
        }
        final double u = 1.0;
        final double delta = 1.0;
        double m1 = line1.getSlope();
        double m2 = line2.getSlope();
        
        // Test slope
        if (Math.abs(m1 - m2) > u) {
            return false;
        }
        double d1 = Position.distanceBetween(line1.p, line2.p);
        double d2 = Position.distanceBetween(line1.p, line2.q);
        double d3 = Position.distanceBetween(line1.q, line2.p);
        double d4 = Position.distanceBetween(line1.q, line2.q);
        
        // Test distances
        return (d1 <= delta) || (d2 <= delta) || (d3 <= delta) || (d4 <= delta);
    }
    
    /**
     * Performs a merge procedure on the two lines, and returns the resulting
     * line object.
     * 
     * @param line1
     * @param line2
     * @return 
     */
    public static Line mergeSegments(Line line1, Line line2) {
        if (line1 == null || line2 == null) {
            throw new NullPointerException("Line arguments cannot be null.");
        }
        double a1 = line1.getSlope();
        double a2 = line2.getSlope();
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
    
    
    /*
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
    */
    
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
    private static boolean areCollinear(Position a, Position b, Position c) {
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
    
    public void print() {
        System.out.print("Line segment: ");
        System.out.print("p");
        p.print();
        System.out.print(" --- ");
        System.out.print("q");
        q.print();
        System.out.println();
    }
}
