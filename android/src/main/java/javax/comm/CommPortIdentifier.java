package javax.comm;

import java.io.FileDescriptor;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Communications port management.
 *
 * CommPortIdentifier is the central class for controlling access to communications ports. It includes methods for:
 *
 * <ol>
 * <li>Determining the communications ports made available by the driver.</li>
 * <li>Opening communications ports for I/O operations.</li>
 * <li>Determining port ownership.</li>
 * <li>Resolving port ownership contention.</li>
 * <li>Managing events that indicate changes in port ownership status.</li>
 * </ol>
 *
 * An application first uses methods in CommPortIdentifier to negotiate with the driver to discover which
 * communication ports are available and then select a port for opening. It then uses methods in other
 * classes like CommPort, ParallelPort and SerialPort to communicate through the port.
 *
 * @see CommPort, CommPortOwnershipListener, ParallelPort, SerialPort
 */
public class CommPortIdentifier
{
    public static final int PORT_SERIAL = 1;
    public static final int PORT_PARALLEL = 2;

    private static ConcurrentHashMap<String,CommPortIdentifier> ports = new ConcurrentHashMap<String, CommPortIdentifier>();

    private String name;
    private final int portType;
    private final CommDriver driver;

    public CommPortIdentifier( String portName, int portType, CommDriver driver )
    {
        this.name = portName;
        this.portType = portType;
        this.driver = driver;
    }

    /**
     * Adds portName to the list of ports.
     *
     * @param portName - The name of the port being added
     * @param portType - The type of the port being added
     * @param driver   - The driver representing the port being added
     */
    public static void addPortName( String portName, int portType, CommDriver driver )
    {
        CommPortIdentifier cpi = new CommPortIdentifier(portName, portType, driver);
        ports.put(portName, cpi);
    }

    /**
     * Returns the name of the port.
     *
     * @return the name of the port
     */
    public String getName()
    {
        return name;
    }

    /**
     * Obtains a CommPortIdentifier object by using a port name.
     * The port name may have been stored in persistent storage by the application.
     *
     * @param portName - name of the port to open
     *
     * @return CommPortIdentifier object
     *
     * @throws NoSuchPortException - if the port does not exist
     */
    public static CommPortIdentifier getPortIdentifier( String portName )
        throws NoSuchPortException
    {
        return ports.get( portName );
    }

    /**
     * Obtains the CommPortIdentifier object corresponding to a port that has already been opened by the application.
     *
     * @param port - a CommPort object obtained from a previous open
     *
     * @return CommPortIdentifier object
     *
     * @throws NoSuchPortException - if the port object is invalid
     */
    public static CommPortIdentifier getPortIdentifier( CommPort port )
        throws NoSuchPortException
    {
        return ports.get( port.getName() );
    }

    /**
     * Obtains an enumeration object that contains a CommPortIdentifier object for each port in the system.
     *
     * @return Enumeration that can be used to enumerate all the ports known to the system
     */
    public static Enumeration getPortIdentifiers()
    {
        final Iterator<CommPortIdentifier> it = ports.values().iterator();
        return new Enumeration()
        {
            @Override
            public boolean hasMoreElements()
            {
                return it.hasNext();
            }

            @Override
            public Object nextElement()
            {
                return it.next();
            }
        };
    }

    /**
     * Returns the port type.
     *
     * @return portType - PORT_SERIAL or PORT_PARALLEL
     */
    public int getPortType()
    {
        return portType;
    }

    /**
     * Registers an interested application so that it can receive notification of changes in port ownership.
     *
     * This includes notification of the following events:
     * <ol>
     * <li>PORT_OWNED: Port became owned</li>
     * <li>PORT_UNOWNED: Port became unowned</li>
     * <li>PORT_OWNERSHIP_REQUESTED If the application owns this port and is willing to give up ownership,
     * then it should call close now.</li>
     * </ol>
     * The ownershipChange method of the listener registered using addPortOwnershipListener will be called with one
     * of the above events.
     *
     * @param listener CommPortOwnershipListener callback object
     */
    public void addPortOwnershipListener( CommPortOwnershipListener listener )
    {
    }

    /**
     * Deregisters a CommPortOwnershipListener registered using addPortOwnershipListener
     *
     * @param listener The CommPortOwnershipListener object that was previously registered using addPortOwnershipListener
     */
    public void removePortOwnershipListener( CommPortOwnershipListener listener )
    {
    }

    /**
     * Returns the owner of the port.
     *
     * @return current owner of the port.
     */
    public String getCurrentOwner()
    {
        return "<unknown>";
    }

    /**
     * Checks whether the port is owned.
     *
     * @return true if port is owned by an application, false if port is not owned.
     */
    public synchronized boolean isCurrentlyOwned()
    {
        return false;
    }

    /**
     * Opens the communications port using a FileDescriptor object on platforms that support this technique.
     *
     * @param fd - The FileDescriptor associated with this CommPort.
     *
     * @return CommPort object.
     *
     * @throws UnsupportedCommOperationException
     *          - on platforms which do not support this functionality.
     */
    public synchronized CommPort open( FileDescriptor fd )
        throws UnsupportedCommOperationException
    {
        throw new UnsupportedCommOperationException();
    }

    /**
     * Opens the communications port.
     *
     * open obtains exclusive ownership of the port.
     * If the port is owned by some other application, a PORT_OWNERSHIP_REQUESTED event is propagated using
     * the CommPortOwnershipListener event mechanism. If the application that owns the port calls close during
     * the event processing, then this open will succeed. There is one InputStream and one OutputStream associated
     * with each port. After a port is opened with open, then all calls to getInputStream will return the same
     * stream object until close is called.
     *
     * @param appname Name of application making this call. This name will become the owner of the port. Useful
     *                when resolving ownership contention.
     * @param timeout Time in milliseconds to block waiting for port open.
     *
     * @return CommPort object
     *
     * @throws PortInUseException - if the port is in use by some other application that is not willing to relinquish ownership
     */
    public CommPort open( String appname, int timeout )
        throws PortInUseException
    {
        CommPort port = driver.getCommPort( name, portType );
        return port;
    }
}

