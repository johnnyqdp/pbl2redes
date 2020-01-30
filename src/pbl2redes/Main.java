package pbl2redes;

import java.io.IOException;

/**
 *
 * @author Johnny
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        Receiver threadReceiver = new Receiver();
        threadReceiver.start();
        
        Sender threadSender = new Sender(0);
        threadSender.definePodeEnviar();
        threadSender.start();
        
    }
    
}
