/*
 * This code is written as p part of p Master Thesis
 * the spring of 2016.
 *
 * Eirik Thon(Master 2016 @ NTNU)
 */
package no.ntnu.et.general;

import static no.ntnu.et.general.Position.distanceBetween;
import static no.ntnu.et.general.Utilities.dot;
import static no.ntnu.et.general.Utilities.getProjectedPoint;
import static no.ntnu.et.general.Utilities.norm;
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
        this.start = null;
        this.direction = null;
        this.length = Position.distanceBetween(p, q);
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
        double midX = (line.getP().getXValue() + line.getQ().getXValue()) / 2.0;
        double midY = (line.getP().getYValue() + line.getQ().getYValue()) / 2.0;
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
    public static Line[] selfMerge(Line[] buffer, int length) {
        Line[] result = new Line[length];
        
    }
    */
    
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
        final double u = 5.0;
        final double delta = 5.0;
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
    
    
    
    static boolean isMergeable1(Line line1, Line line2) {
        // u1
        double a1 = line1.getSlope();
        double a2 = line2.getSlope();
        double slope = Math.abs(a1 - a2);
        double u1 = calculateU1(slope);
        System.out.println("u1: " + u1);
        
        // u2
        Position mid1 = getMidpoint(line1);
        Position mid2 = getMidpoint(line2);
        // double u2 = calculateU2()
        
        // u3
        double midPointDist = Position.distanceBetween(mid1, mid2);
        double len1 = line1.getLength();
        double len2 = line2.getLength();
        double u3 = calculateU3(midPointDist, len1, len2);
        System.out.println("u3: " + u3);
        
        // u4
        // double u4 = calculateU4()
        
        //double similarityThreshold = 0.6;
        return (u1 >= 0.6 && u3 >= 0.6); // add u2 and u4
    }
    
    
    /**
     * Angle between the two line segments. Slope values a and b correspond
     * to 10 and 20 degrees
     * 
     * @param slope
     * @return 
     */
    static double calculateU1(double slope) {
        double a = 0.2;
        double b = 0.35;
        double res = 1.0;
        if (slope >= 0 && slope < a) {
            res = 1.0 - 0.5 / a * slope;
        } else if (slope >= a && slope <= b) {
            res = 0.5 - 0.5 / (b - a) * (slope - a);
        } else if (slope > b) {
            res = 0.0;
        }
        return res;
    }
    
    /**
     * Maximum distance of each line's midpoint to the other line. Values are
     * given in mm.
     * 
     * @param dist [mm]
     * @return 
     */
    static double calculateU2(double dist) {
        final double c = 10.0; //[mm]
        final double d = 30.0; //[mm]
        final double e = 60.0; //[mm]
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
     * @param dist [mm]
     * @param lenA [mm]
     * @param lenB [mm]
     * @return 
     */
    static double calculateU3(double dist, double lenA, double lenB) {
        double f;
        if (lenA <= lenB) {
            f = lenA;
        } else {
            f = lenB;
        }
        final double g = f + 10.0; //[mm]
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
     * @param dist [mm]
     * @return 
     */
    static double calculateU4(double dist) {
        final double h = 10.0; //[mm]
        final double i = 20.0; //[mm]
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
    
    public static double pointToSegment(Position p, Line s) {
        double[] v = new double[]{ s.q.getXValue() - s.p.getXValue(), s.q.getYValue() - s.p.getYValue() };
        double[] w = new double[]{ p.getXValue() - s.p.getXValue(), p.getYValue() - s.p.getYValue() };

        double c1 = dot(w,v);
        if ( c1 <= 0 )
             return distanceBetween(p, s.p);

        double c2 = dot(v,v);
        if ( c2 <= c1 )
             return distanceBetween(p, s.q);

        double b = c1 / c2;
        Position Pb = new Position(s.p.getXValue() + b * v[0], s.p.getYValue() + b * v[1]);
        return distanceBetween(p, Pb);
    }
    
    /**
     * Calculates the minimum distance between 2 segments. Adapted from 3D-version
     * at http://geomalgorithms.com/a07-_distance.html#dist3D_Segment_to_Segment()
     * 
     * @param line1
     * @param line2
     * @return 
     */
    public static double segmentToSegment(Line line1, Line line2) {
        final double SMALL_NUM = 1e-9;
        double[] u = new double[]{ line1.q.getXValue() - line1.p.getXValue(), line1.q.getYValue() - line1.p.getYValue() };
        double[] v = new double[]{ line2.q.getXValue() - line2.p.getXValue(), line2.q.getYValue() - line2.p.getYValue() };
        double[] w = new double[]{ line1.p.getXValue() - line2.p.getXValue(), line1.p.getYValue() - line2.p.getYValue() };
        double a = dot(u, u);         // always >= 0
        double b = dot(u, v);
        double c = dot(v, v);         // always >= 0
        double d = dot(u, w);
        double e = dot(v, w);
        double D = a * c - b * b;        // always >= 0
        double sc, sN, sD = D;       // sc = sN / sD, default sD = D >= 0
        double tc, tN, tD = D;       // tc = tN / tD, default tD = D >= 0

        // compute the line parameters of the two closest points
        if (D < SMALL_NUM) { // the lines are almost parallel
            sN = 0.0;         // force using point P0 on segment S1
            sD = 1.0;         // to prevent possible division by 0.0 later
            tN = e;
            tD = c;
        }
        else {                 // get the closest points on the infinite lines
            sN = (b * e - c * d);
            tN = (a * e - b * d);
            if (sN < 0.0) {        // sc < 0 => the s=0 edge is visible
                sN = 0.0;
                tN = e;
                tD = c;
            }
            else if (sN > sD) {  // sc > 1  => the s=1 edge is visible
                sN = sD;
                tN = e + b;
                tD = c;
            }
        }

        if (tN < 0.0) {            // tc < 0 => the t=0 edge is visible
            tN = 0.0;
            // recompute sc for this edge
            if (-d < 0.0)
                sN = 0.0;
            else if (-d > a)
                sN = sD;
            else {
                sN = -d;
                sD = a;
            }
        }
        else if (tN > tD) {      // tc > 1  => the t=1 edge is visible
            tN = tD;
            // recompute sc for this edge
            if ((-d + b) < 0.0)
                sN = 0;
            else if ((-d + b) > a)
                sN = sD;
            else {
                sN = (-d +  b);
                sD = a;
            }
        }
        // finally do the division to get sc and tc
        sc = (Math.abs(sN) < SMALL_NUM ? 0.0 : sN / sD);
        tc = (Math.abs(tN) < SMALL_NUM ? 0.0 : tN / tD);

        // get the difference of the two closest points
        u[0] *= sc;
        u[1] *= sc;
        v[0] *= tc;
        v[1] *= tc;
        double[] dP = new double[] {w[0] + u[0] - v[0], w[1] + u[1] - v[1] }; // =  S1(sc) - S2(tc)

        return norm(dP);   // return the closest distance
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
