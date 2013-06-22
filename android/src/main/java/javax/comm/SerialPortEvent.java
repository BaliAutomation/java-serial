package javax.comm;

import java.util.EventObject;

public class SerialPortEvent extends EventObject
{
    /**
     * Data available at the serial port.
     * This event will be generated once when new data arrive at the serial port. Even if the user doesn't
     * read the data, it won't be generated again until next time new data arrive.
     */
    public static final int DATA_AVAILABLE = 1;

    /**
     * Output buffer is empty.
     * The event will be generated after a write is completed, when the system buffer becomes empty again.
     */
    public static final int OUTPUT_BUFFER_EMPTY = 2;

    /**
     * Clear to send.
     */
    public static final int CTS = 3;

    /**
     * Data set ready.
     */
    public static final int DSR = 4;

    /**
     * Ring indicator.
     */
    public static final int RI = 5;

    /**
     * Carrier detect.
     */
    public static final int CD = 6;

    /**
     * Overrun Error
     */
    public static final int OE = 7;

    /**
     * Parity Error
     */
    public static final int PE = 8;

    /**
     * Framing Error
     */
    public static final int FE = 9;

    /**
     * Break Interrupt
     */
    public static final int BI = 10;

    private boolean OldValue;
    private boolean NewValue;
    private int eventType;

    /**
     * Constructs a SerialPortEvent with the specified serial port, event type, old and new values. Application programs should not directly create SerialPortEvent objects.
     *
     * @param srcport   source parallel port
     * @param eventtype event type
     * @param oldvalue  old value
     * @param newvalue  new value
     */
    public SerialPortEvent( SerialPort srcport, int eventtype, boolean oldvalue, boolean newvalue )
    {
        super( srcport );
        OldValue = oldvalue;
        NewValue = newvalue;
        eventType = eventtype;
    }

    /**
     * Gets the type of this event.
     *
     * @return integer that can be equal to one of the following static variables:
     *         BI, CD, CTS, DATA_AVAILABLE, DSR, FE, OE, OUTPUT_BUFFER_EMPTY, PE or RI.
     */
    public int getEventType()
    {
        return ( eventType );
    }

    /**
     * Gets the new value of the state change that caused the SerialPortEvent to be propagated.
     *
     * For example, when the CD bit changes, newValue reflects the new value of the CD bit.
     */
    public boolean getNewValue()
    {
        return ( NewValue );
    }

    /**
     * Gets the old value of the state change that caused the SerialPortEvent to be propagated.
     *
     * For example, when the CD bit changes, oldValue reflects the old value of the CD bit.
     */
    public boolean getOldValue()
    {
        return ( OldValue );
    }
}
