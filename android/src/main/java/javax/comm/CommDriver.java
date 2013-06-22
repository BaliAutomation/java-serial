package javax.comm;

/**
 * Part of the loadable device driver interface. CommDriver should not be used by application-level programs.
 */
public interface CommDriver
{
    /**
     * getCommPort() will be called by CommPortIdentifier.open() portName is a string that was registered earlier
     * using the CommPortIdentifier.addPortName() method. getCommPort() returns an object that extends either
     * SerialPort or ParallelPort.
     *
     * @param portName
     * @param portType
     *
     * @return
     */
    CommPort getCommPort( String portName, int portType );

    /**
     * initialize() will be called by the CommPortIdentifier's static initializer.
     * The responsibility of this method is:
     * <ol>
     * <li>Ensure that that the hardware is present.</li>
     * <li>Load any required native libraries.</li>
     * <li>Register the port names with the CommPortIdentifier.</li>
     * </ol>
     */
    void initialize();
}

