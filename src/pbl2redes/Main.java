package pbl2redes;

import java.io.IOException;

/**
 *
 * @author Johnny
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        Sender threadSender = new Sender(0);
        threadSender.definePodeEnviar();
        threadSender.start();
        
        Receiver threadReceiver = new Receiver(threadSender);
        threadReceiver.start();
        
    }
    
}
