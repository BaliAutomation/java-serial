package javax.comm;

/**
 * Thrown when a driver can't find the specified port.
 */
public class NoSuchPortException extends Exception
{
    public NoSuchPortException( String portName )
    {
        super( portName );
    }
}

