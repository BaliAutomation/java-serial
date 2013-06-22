package javax.comm;

import java.util.TooManyListenersException;

/**
 * An RS-232 serial communications port.
 *
 * SerialPort describes the low-level interface to a serial communications port made available by the
 * underlying system. SerialPort defines the minimum required functionality for serial communications ports.
 */
public abstract class SerialPort extends CommPort
{
    public static final int DATABITS_5 = 5;
    public static final int DATABITS_6 = 6;
    public static final int DATABITS_7 = 7;
    public static final int DATABITS_8 = 8;
    public static final int PARITY_NONE = 0;
    public static final int PARITY_ODD = 1;
    public static final int PARITY_EVEN = 2;
    public static final int PARITY_MARK = 3;
    public static final int PARITY_SPACE = 4;
    public static final int STOPBITS_1 = 1;
    public static final int STOPBITS_2 = 2;
    public static final int STOPBITS_1_5 = 3;
    public static final int FLOWCONTROL_NONE = 0;
    public static final int FLOWCONTROL_RTSCTS_IN = 1;
    public static final int FLOWCONTROL_RTSCTS_OUT = 2;
    public static final int FLOWCONTROL_XONXOFF_IN = 4;
    public static final int FLOWCONTROL_XONXOFF_OUT = 8;

    /**
     * Sets serial port parameters.
     *
     * DEFAULT: 9600 baud, 8 data bits, 1 stop bit, no parity
     *
     * @param bitrate  If the baudrate passed in by the application is unsupported by the driver, the driver will
     *                 throw an UnsupportedCommOperationException
     * @param datasize <ul>
     *                 <li>DATABITS_5: 5 bits</li>
     *                 <li>DATABITS_6: 6 bits</li>
     *                 <li>DATABITS_7: 7 bits</li>
     *                 <li>DATABITS_8: 8 bits</li>
     *                 </ul>
     * @param stopbits <ul>
     *                 <li>STOPBITS_1: 1 stop bit</li>
     *                 <li>STOPBITS_2: 2 stop bits</li>
     *                 <li>STOPBITS_1_5: 1.5 stop bits</li>
     *                 </ul>
     * @param parity   <ul>
     *                 <li>PARITY_NONE: no parity</li>
     *                 <li>PARITY_ODD: odd parity</li>
     *                 <li>PARITY_EVEN: even parity</li>
     *                 <li>PARITY_MARK: mark parity</li>
     *                 <li>PARITY_SPACE: space parity</li>
     *                 </ul>
     *
     * @throws UnsupportedCommOperationException
     *          if any of the above parameters are specified incorrectly. All four of the parameters will revert to the values before the call was made.
     */
    public abstract void setSerialPortParams( int bitrate, int datasize, int stopbits, int parity )
        throws UnsupportedCommOperationException;

    /**
     * Gets the currently configured baud rate.
     *
     * @return integer value indicating the baud rate
     */
    public abstract int getBaudRate();

    /**
     * Gets the currently configured number of data bits.
     *
     * @return integer that can be equal to DATABITS_5, DATABITS_6, DATABITS_7, or DATABITS_8
     */
    public abstract int getDataBits();

    /**
     * Gets the currently defined stop bits.
     *
     * @return integer that can be equal to STOPBITS_1, STOPBITS_2, or STOPBITS_1_5
     */
    public abstract int getStopBits();

    /**
     * Get the currently configured parity setting.
     *
     * @return integer that can be equal to PARITY_NONE, PARITY_ODD, PARITY_EVEN, PARITY_MARK or PARITY_SPACE.
     */
    public abstract int getParity();

    /**
     * Sets the flow control mode.
     *
     * @param flowcontrol Can be a bitmask combination of
     *                    <ul>
     *                    <li>FLOWCONTROL_NONE: no flow control</li>
     *                    <li>FLOWCONTROL_RTSCTS_IN: RTS/CTS (hardware) flow control for input</li>
     *                    <li>FLOWCONTROL_RTSCTS_OUT:	RTS/CTS (hardware) flow control for output</li>
     *                    <li>FLOWCONTROL_XONXOFF_IN:	XON/XOFF (software) flow control for input</li>
     *                    <li>FLOWCONTROL_XONXOFF_OUT:	XON/XOFF (software) flow control for output</li>
     *                    </ul>
     *
     * @throws UnsupportedCommOperationException
     *          if any of the flow control mode was not supported by the  underline OS, or if input and output flow
     *          control are set to different values, i.e. one hardware and one software. The flow control mode will
     *          revert to the value before the call was made.
     */
    public abstract void setFlowControlMode( int flowcontrol )
        throws UnsupportedCommOperationException;

    /**
     * Gets the currently configured flow control mode.
     *
     * @return an integer bitmask of the modes FLOWCONTROL_NONE, FLOWCONTROL_RTSCTS_IN, FLOWCONTROL_RTSCTS_OUT,
     *         FLOWCONTROL_XONXOFF_IN, and FLOWCONTROL_XONXOFF_OUT.
     */
    public abstract int getFlowControlMode();

    /**
     * Sets or clears the DTR (Data Terminal Ready) bit in the UART, if supported by the underlying implementation.
     *
     * @param state <ul>
     *              <li>true: set DTR</li>
     *              <li>false: clear DTR</li>
     *              </ul>
     */
    public abstract void setDTR( boolean state );

    /**
     * Sets or clears the RTS (Request To Send) bit in the UART, if supported by the underlying implementation.
     *
     * @param state <ul>
     *              <li>true: set RTS</li>
     *              <li>false: clear RTS</li>
     *              </ul>
     */
    public abstract void setRTS( boolean state );

    /**
     * Gets the state of the RTS (Request To Send) bit in the UART, if supported by the underlying implementation.
     *
     * @return true if RTS is currently active. false otherwise.
     */
    public abstract boolean isRTS();

    /**
     * Gets the state of the CTS (Clear To Send) bit in the UART, if supported by the underlying implementation.
     *
     * @return true if CTS is currently active. false otherwise.
     */
    public abstract boolean isCTS();

    /**
     * Gets the state of the DTR (Data Terminal Ready) bit in the UART, if supported by the underlying implementation.
     *
     * @return true if DTR is currently active. false otherwise.
     */
    public abstract boolean isDTR();

    /**
     * Gets the state of the DSR (Data Set Ready) bit in the UART, if supported by the underlying implementation.
     *
     * @return true if DSR is currently active. false otherwise.
     */
    public abstract boolean isDSR();

    /**
     * Gets the state of the CD (Carrier Detect) bit in the UART, if supported by the underlying implementation.
     *
     * @return true if DCD is currently active. false otherwise.
     */
    public abstract boolean isCD();

    /**
     * Gets the state of the RI (Ring Indicator) bit in the UART, if supported by the underlying implementation.
     *
     * @return true if RI is currently active. false otherwise.
     */
    public abstract boolean isRI();

    /**
     * Sends a break of millis milliseconds duration.
     *
     * Note that it may not be possible to time the duration of the break under certain Operating Systems.
     * Hence this parameter is advisory.
     *
     * @param duration duration of break to send
     */
    public abstract void sendBreak( int duration );

    /**
     * Registers a SerialPortEventListener object to listen for SerialEvents.
     *
     * Interest in specific events may be expressed using the notifyOnXXX calls. The serialEvent method of
     * SerialPortEventListener will be called with a SerialEvent object describing the event.
     * The current implementation only allows one listener per SerialPort. Once a listener is registered,
     * subsequent call attempts to addEventListener will throw a TooManyListenersException without effecting
     * the listener already registered.
     *
     * All the events received by this listener are generated by one dedicated thread that belongs to the
     * SerialPort object. After the port is closed, no more event will be generated. Another call to open() of
     * the port's CommPortIdentifier object will return a new CommPort object, and the lsnr has to be added again
     * to the new CommPort object to receive event from this port.
     *
     * @param listener The SerialPortEventListener object whose serialEvent method will be called with a SerialEvent describing the event.
     *
     * @throws java.util.TooManyListenersException
     *          - If an initial attempt to attach a listener succeeds, subsequent attempts will throw TooManyListenersException without effecting the first listener.
     */
    public abstract void addEventListener( SerialPortEventListener listener )
        throws TooManyListenersException;

    /**
     * Deregisters event listener registered using addEventListener.
     *
     * This is done automatically at port close.
     */
    public abstract void removeEventListener();

    /**
     * Expresses interest in receiving notification when input data is available.
     *
     * This may be used to drive asynchronous input. When data is available in the input buffer, this
     * event is propagated to the listener registered using addEventListener.
     * The event will be generated once when new data arrive at the serial port. Even if the user doesn't
     * read the data, it won't be generated again until next time new data arrive.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnDataAvailable( boolean enable );

    /**
     * Expresses interest in receiving notification when the output buffer is empty.
     *
     * This may be used to drive asynchronous output. When the output buffer becomes empty, this event is
     * propagated to the listener registered using addEventListener. The event will be generated after a
     * write is completed, when the system buffer becomes empty again.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnOutputEmpty( boolean enable );

    /**
     * Expresses interest in receiving notification when the CTS (Clear To Send) bit changes.
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnCTS( boolean enable );

    /**
     * Expresses interest in receiving notification when the DSR (Data Set Ready) bit changes.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnDSR( boolean enable );

    /**
     * Expresses interest in receiving notification when the RI (Ring Indicator) bit changes.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnRingIndicator( boolean enable );

    /**
     * Expresses interest in receiving notification when the CD (Carrier Detect) bit changes.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnCarrierDetect( boolean enable );

    /**
     * Expresses interest in receiving notification when there is an overrun error.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnOverrunError( boolean enable );

    /**
     * Expresses interest in receiving notification when there is a parity error.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnParityError( boolean enable );

    /**
     * Expresses interest in receiving notification when there is a framing error.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnFramingError( boolean enable );

    /**
     * Expresses interest in receiving notification when there is a break interrupt on the line.
     *
     * This notification is hardware dependent and may not be supported by all implementations.
     *
     * @param enable <ul>
     *               <li>true: enable notification</li>
     *               <li>false: disable notification</li>
     *               </ul>
     */
    public abstract void notifyOnBreakInterrupt( boolean enable );
}
