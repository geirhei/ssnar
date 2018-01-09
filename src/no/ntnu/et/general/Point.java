/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

/**
 *
 * @author geirhei
 */
public class Point {
    public double r;
    public double theta;
    
    public Point(double r, double theta) {
        this.r = r;
        this.theta = theta;
    }
    
    /**
     * prints the r-, theta-values of the Position object
     */
    public void print() {
        System.out.print("r: ");
        System.out.printf("%1.2f", r);
        System.out.print(", theta: ");
        System.out.printf("%1.2f", theta);
        System.out.println();
    }
}
