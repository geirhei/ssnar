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
    public Line(Position a, Position b) {
        this.a = a;
        this.b = b;
        this.length = Math.sqrt( Math.pow(b.getXValue() - a.getXValue(), 2) + Math.pow(b.getYValue() - a.getYValue(), 2) );
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
    
    public static ArrayList<Line> mergeSelf(ArrayList<Line> repository) {
        ArrayList<Line> result = new ArrayList<Line>();
        ListIterator<Line> iter1 = repository.listIterator();
        while (iter1.hasNext()) {
            Line repoLine = iter1.next();
            
        }
        return result;
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
    
    /*
    private static boolean isMergeable(Line line1, Line line2, double u, double d) {
        double m1 = line1.getSlope();
        double m2 = line2.getSlope();
        double m = Math.abs(m1 - m2);
        double dist1 = Position.distanceBetween(line1.getA(), line2.getA());
        double dist2 = Position.distanceBetween(line1.getA(), line2.getB());
        double dist3 = Position.distanceBetween(line1.getB(), line2.getA());
        double dist4 = Position.distanceBetween(line1.getB(), line2.getB());
        return ( (m <= u) && (dist1 <= d || dist2 <= d || dist3 <= d || dist4 <= d) );
    }
    */
    
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
    public void print(){
        System.out.println("Start: " + start[0] + ", " + start[1]);
        System.out.println("Direction: " + direction[0] + ", " + direction[1]);
        System.out.println("Length: " + length);
    }
}
