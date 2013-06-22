package io.bali.serialport.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

public class SerialCommPort extends javax.comm.SerialPort
{
    private boolean closed = false;
    private volatile SerialPort physPort;
    private int bitrate;
    private int receiveTimeout;
    private int threshold;

    public SerialCommPort( String portName )
    {
        if( !portName.startsWith( "/dev/" ))
            portName = "/dev/" + portName;
        this.name = portName;
    }

    @Override
    public InputStream getInputStream()
        throws IOException
    {
        if( physPort == null )
            obtainPort();
        return physPort.getInputStream();
    }

    private void obtainPort()
        throws IOException
    {
        File file = new File( name );
        physPort = new SerialPort( file, bitrate, receiveTimeout, threshold );
    }

    @Override
    public OutputStream getOutputStream()
        throws IOException
    {
        if( physPort == null )
            obtainPort();
        return physPort.getOutputStream();
    }

    @Override
    public void close()
    {
        if( closed )
            return;
        closed = true;
        if( physPort == null )
            return;
        physPort.close();
    }

    @Override
    public void enableReceiveFraming( int framingByte )
        throws UnsupportedCommOperationException
    {
    }

    @Override
    public void disableReceiveFraming()
    {
    }

    @Override
    public boolean isReceiveFramingEnabled()
    {
        return false;
    }

    @Override
    public int getReceiveFramingByte()
    {
        return 0;
    }

    @Override
    public void disableReceiveTimeout()
    {
        receiveTimeout = -1;
    }

    @Override
    public void enableReceiveTimeout( int time )
        throws UnsupportedCommOperationException
    {
        if( time == receiveTimeout)
            return;
        receiveTimeout = time;
        physPort = null;
    }

    @Override
    public boolean isReceiveTimeoutEnabled()
    {
        return receiveTimeout != -1;
    }

    @Override
    public int getReceiveTimeout()
    {
        return receiveTimeout;
    }

    @Override
    public void enableReceiveThreshold( int thresh )
        throws UnsupportedCommOperationException
    {
        if( thresh == threshold)
            return;
        threshold = thresh;
        physPort = null;
    }

    @Override
    public void disableReceiveThreshold()
    {
        threshold = -1;
    }

    @Override
    public int getReceiveThreshold()
    {
        return threshold;
    }

    @Override
    public boolean isReceiveThresholdEnabled()
    {
        return threshold != -1;
    }

    @Override
    public void setInputBufferSize( int size )
    {
    }

    @Override
    public int getInputBufferSize()
    {
        return 0;
    }

    @Override
    public void setOutputBufferSize( int size )
    {
    }

    @Override
    public int getOutputBufferSize()
    {
        return 0;
    }

    @Override
    public void setSerialPortParams( int bitrate, int datasize, int stopbits, int parity )
        throws UnsupportedCommOperationException
    {
        if( this.bitrate == bitrate )
            return;
        this.bitrate = bitrate;
        physPort = null;
    }

    @Override
    public int getBaudRate()
    {
        return 0;
    }

    @Override
    public int getDataBits()
    {
        return 0;
    }

    @Override
    public int getStopBits()
    {
        return 0;
    }

    @Override
    public int getParity()
    {
        return 0;
    }

    @Override
    public void setFlowControlMode( int flowcontrol )
        throws UnsupportedCommOperationException
    {
    }

    @Override
    public int getFlowControlMode()
    {
        return 0;
    }

    @Override
    public void setDTR( boolean state )
    {
    }

    @Override
    public void setRTS( boolean state )
    {
    }

    @Override
    public boolean isRTS()
    {
        return false;
    }

    @Override
    public boolean isCTS()
    {
        return false;
    }

    @Override
    public boolean isDTR()
    {
        return false;
    }

    @Override
    public boolean isDSR()
    {
        return false;
    }

    @Override
    public boolean isCD()
    {
        return false;
    }

    @Override
    public boolean isRI()
    {
        return false;
    }

    @Override
    public void sendBreak( int duration )
    {
    }

    @Override
    public void addEventListener( SerialPortEventListener listener )
        throws TooManyListenersException
    {
    }

    @Override
    public void removeEventListener()
    {
    }

    @Override
    public void notifyOnDataAvailable( boolean enable )
    {
    }

    @Override
    public void notifyOnOutputEmpty( boolean enable )
    {
    }

    @Override
    public void notifyOnCTS( boolean enable )
    {
    }

    @Override
    public void notifyOnDSR( boolean enable )
    {
    }

    @Override
    public void notifyOnRingIndicator( boolean enable )
    {
    }

    @Override
    public void notifyOnCarrierDetect( boolean enable )
    {
    }

    @Override
    public void notifyOnOverrunError( boolean enable )
    {
    }

    @Override
    public void notifyOnParityError( boolean enable )
    {
    }

    @Override
    public void notifyOnFramingError( boolean enable )
    {
    }

    @Override
    public void notifyOnBreakInterrupt( boolean enable )
    {
    }
}
