package io.bali.serialport.api;

import javax.comm.CommDriver;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;

public class SerialCommDriver
    implements CommDriver
{
    @Override
    public CommPort getCommPort( String portName, int portType )
    {
        if( portType != CommPortIdentifier.PORT_SERIAL)
            throw new IllegalArgumentException( "Only SerialPort type is allowed." );
        return new SerialCommPort(portName);
    }

    /**
     * initialize() will be called by the CommPortIdentifier's static initializer.
     * The responsibility of this method is:
     * <ol>
     * <li>Ensure that that the hardware is present.</li>
     * <li>Load any required native libraries.</li>
     * <li>Register the port names with the CommPortIdentifier.</li>
     * </ol>
     */
    @Override
    public void initialize()
    {
        SerialPort.loadLibrary();
        CommPortIdentifier.addPortName( "/dev/ttyS0", CommPortIdentifier.PORT_SERIAL, this );
        CommPortIdentifier.addPortName( "/dev/ttyS1", CommPortIdentifier.PORT_SERIAL, this );
        CommPortIdentifier.addPortName( "/dev/ttyS2", CommPortIdentifier.PORT_SERIAL, this );
        CommPortIdentifier.addPortName( "/dev/ttyS3", CommPortIdentifier.PORT_SERIAL, this );
    }
}
