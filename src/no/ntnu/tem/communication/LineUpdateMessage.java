/*
 * This code is written as a part of a Master Thesis
 * the spring of 2018.
 *
 * Geir Eikeland(Master 2018 @ NTNU)
 */
package no.ntnu.tem.communication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * A received update message has the follow fields, with byte length in parentheses:
 * StartX (2) | StartY (2) | StopX (2) | StopY (2) 
 * 
 * Units are cm for lengths
 * 
 * @author Geir Eikeland
 */
public class LineUpdateMessage {
    
    private byte[] data;
    private int startY;
    private int startX;
    private int stopY;
    private int stopX;
    public LineUpdateMessage(byte[] data) throws Message.MessageCorruptException, Message.ValueCorruptException {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.data = data;
        if(buffer.remaining() != 14)  throw new Message.MessageCorruptException();
        
        startX = buffer.getShort();
        startY = buffer.getShort();
        stopX = buffer.getShort();
        stopY = buffer.getShort();
    }
    public byte[] getBytes() {
        return data;
    }
    
    public int[] getLine() { return new int[]{startX, startY, stopX, stopY}; }
}
