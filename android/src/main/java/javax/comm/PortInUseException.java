package javax.comm;

/**
 * Thrown when the specified port is in use.
 */
public class PortInUseException extends Exception
{
    /**
     * Describes the current owner of the communications port.
     */
    public String currentOwner;

    /**
     * create a instance of the Exception and store the current owner
     *
     * @param owner detailed information about the current owner
     */
    public PortInUseException( String owner )
    {
        super( owner );
        currentOwner = owner;
    }
}

