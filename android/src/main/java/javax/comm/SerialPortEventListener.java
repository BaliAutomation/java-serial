package javax.comm;

import java.util.EventListener;

/**
 * Listener callback interface for serial port events.
 */
public interface SerialPortEventListener extends EventListener
{
    /**
     * @param event the event that has occurred.
     */
    void serialEvent( SerialPortEvent event );
}
