/**
 * This code is written as a part of a Master Thesis
 * the fall of 2017.
 *
 * Geir Eikeland (Master 2017 @ NTNU)
 */
package no.ntnu.et.general;

/**
 * Interior = 0
 * Exterior = 1
 * 
 * @author geirhei
 */
public class Vertice {
    private int type = 0;
    
    public Vertice(int type) {
        this.type = type;
    }
    
    /**
     * Setter for type.
     * @return 
     */
    public int getType() {
        return type;
    }
    
    /**
     * Getter for type.
     * @param type 
     */
    void setType(int type) {
        this.type = type;
    }
}
