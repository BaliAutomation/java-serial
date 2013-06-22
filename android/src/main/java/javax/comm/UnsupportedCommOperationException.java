package javax.comm;

/**
 * Thrown when a driver doesn't allow the specified operation.
 */

public class UnsupportedCommOperationException extends Exception
{
    /**
     * create an instances with no message about why the Exception was thrown.
     */
    public UnsupportedCommOperationException()
    {
        super();
    }

    /**
     * create an instance with a message about why the Exception was thrown.
     *
     * @param message A detailed message explaining the reason for the Exception.
     */
    public UnsupportedCommOperationException( String message )
    {
        super( message );
    }
}
