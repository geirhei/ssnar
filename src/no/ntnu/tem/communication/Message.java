/*
 * This code is written as a part of a Master Thesis
 * the spring of 2017.
 *
 * Kristian Lien (Master 2017 @ NTNU)
 */
package no.ntnu.tem.communication;

import java.util.Arrays;

/**
 *
 * @author Kristian
 */

public class Message {
    public static final int HANDSHAKE = 0;
    public static final int UPDATE = 1;
    public static final int ORDER = 2;
    public static final int PRIORITY_ORDER = 3;
    public static final int IDLE = 4;
    public static final int PAUSE = 5;
    public static final int UNPAUSE = 6;
    public static final int CONFIRM = 7;
    public static final int FINISH = 8;
    public static final int PING = 9;
    public static final int PING_RESPONSE = 10;
    public static final int DEBUG = 11;
    public static final int DRONE_UPDATE = 12;
    public static final int BATTERY_UPDATE = 13;
   
    private final int sender;
    private final int type;
    protected final byte[] data;
    
    public Message(int sender, byte[] contents) {
        this.data = Arrays.copyOfRange(contents, 1, contents.length);
        this.type = contents[0];
        this.sender = sender;
    }

    public int getType() {
        return type;
    }
    
    public int getSender() { 
        return sender; 
    }
    
    public byte[] getData() {
        return data;
    }
    public class GenericMessage {
    
    }
     /**
     * Exception thrown when the message is corrupt
     */
    public static class MessageCorruptException extends Exception {

        public MessageCorruptException() {
        }
    }

    /**
     * Exception thrown when a value is outside its range
     */
    public static class ValueCorruptException extends Exception {

        public ValueCorruptException() {
        }
    }
}

