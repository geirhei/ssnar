/*
 * This code is written as a part of a Master Thesis
 * the spring of 2018.
 *
 * Geir Eikeland(Master 2018 @ NTNU)
 */
package no.ntnu.tem.communication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import no.ntnu.et.general.Line;
import no.ntnu.et.general.Position;

/**
 * A received update message has the follow fields, with byte length in parentheses:
 * StartX (2) | StartY (2) | StopX (2) | StopY (2) 
 * 
 * 
 * 
 * @author Geir Eikeland
 */
public class LineRepoMessage {
    
    private byte[] data;
    private int pX;
    private int pY;
    private int qX;
    private int qY;
    private int index;
    
    public LineRepoMessage(byte[] data) throws Message.MessageCorruptException, Message.ValueCorruptException {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.data = data;
        if(buffer.remaining() != 10)  throw new Message.MessageCorruptException();
        
        pX = buffer.getShort();
        pY = buffer.getShort();
        qX = buffer.getShort();
        qY = buffer.getShort();
        index = buffer.getShort();
    }
    
    public byte[] getBytes() {
        return data;
    }
    
    //public int[] getLine() { return new int[]{startX, startY, stopX, stopY}; }
    
    public Line getLine() {
        return new Line(new Position(pX, pY), new Position(qX, qY));
    }
    
    public int getIndex() {
        return index;
    }
}
