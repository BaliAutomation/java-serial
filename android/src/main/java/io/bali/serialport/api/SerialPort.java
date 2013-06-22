/*
 * Copyright 2009 Cedric Priscal
 * Copyright 2013 Niclas Hedhman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package io.bali.serialport.api;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort
{
    /*
     * Do not remove or rename the field mFd: it is used by native method close();
     */
    private FileDescriptor fileDescriptor;      // Accessed from JNI. Don't change name.

    private FileInputStream inputStream;
    private FileOutputStream outputStream;

    /**
     * <table border="1">
     * <tr><th>threshold</th><th>receiveTimeout</th><th>Behavior</th></tr>
     *
     * <tr>
     * <td>0</td>
     * <td>0</td>
     * <td>
     * This is a completely non-blocking read - the call is satisfied immediately directly from the driver's input
     * queue. If data are available, it's transferred to the caller's buffer up to nbytes and returned. Otherwise zero
     * is immediately returned to indicate "no data". We'll note that this is "polling" of the serial port, and it's
     * almost always a bad idea. If done repeatedly, it can consume enormous amounts of processor time and is highly
     * inefficient. Don't use this mode unless you really, really know what you're doing.
     * </td>
     * </tr>
     *
     * <tr>
     * <td>0</td>
     * <td> >0</td>
     * <td>
     * This is a pure timed read. If data are available in the input queue, it's transferred to the caller's buffer
     * up to a maximum of nbytes, and returned immediately to the caller. Otherwise the driver blocks until data
     * arrives, or when {@code receiveTimeout} milliseconds (granularity is only 1/10 of a second) expire from the
     * start of the call. If the timer expires
     * without data, zero is returned. A single byte is sufficient to satisfy this read call, but if more is available
     * in the input queue, it's returned to the caller. Note that this is an overall timer, not an intercharacter one.
     * </td>
     * </tr>
     *
     * <tr>
     * <td> >0</td>
     * <td> >0</td>
     * <td>
     * A read() is satisfied when either {@code threshold} characters have been transferred to the caller's
     * buffer, or when {@code receiveTimeout} milliseconds (granularity is only 1/10 of a second) expire between
     * characters. Since this timer is not started
     * until the first character arrives, this call can block indefinitely if the serial line is idle. This is the
     * most common mode of operation, and we consider {@code receiveTimeout} to be an intercharacter timeout,
     * not an overall one. This call should never return zero bytes read.
     * </td>
     * </tr>
     *
     * <tr>
     * <td> >0</td>
     * <td>0</td>
     * <td>
     * This is a counted read that is satisfied only when at least {@code threshold} characters have been
     * transferred to the caller's buffer - there is no timing component involved. This read can be satisfied from
     * the driver's input queue (where the call could return immediately), or by waiting for new data to arrive: in
     * this respect the call could block indefinitely. We believe that it's undefined behavior if nbytes is less than
     * {@code threshold}.
     * </td>
     * </tr>
     * </table>
     *
     * @param device         The device file to open as a serial port. Typically /dev/ttyS1 or similar.
     * @param bitrate        The bitrate for the port, such as 9600
     * @param receiveTimeout The timeout in the read. See table above.
     * @param threshold      The minimum number of characters in the read. See table above.
     *
     * @throws SecurityException
     * @throws IOException
     */
    public SerialPort( File device, int bitrate, int receiveTimeout, int threshold )
        throws IOException
    {
        if( !device.canRead() || !device.canWrite() )
        {
            throw new IOException("No read/write access to device.");
        }

        fileDescriptor = open( device.getAbsolutePath(), bitrate );
        setTimeoutThreshold( receiveTimeout, threshold );
        if( fileDescriptor == null )
        {
            throw new IOException("native open returns null");
        }
        inputStream = new FileInputStream( fileDescriptor );
        outputStream = new FileOutputStream( fileDescriptor );
    }

    // Getters and setters
    public InputStream getInputStream()
    {
        return inputStream;
    }

    public OutputStream getOutputStream()
    {
        return outputStream;
    }

    // JNI
    private native static FileDescriptor open( String path, int baudrate );

    public native void close();

    private native void setTimeoutThreshold( int timeout, int threshold );

    public static void loadLibrary()
    {
        System.loadLibrary( "serialport" );
    }
}
