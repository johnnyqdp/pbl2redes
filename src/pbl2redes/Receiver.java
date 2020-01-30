package pbl2redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Receiver extends Thread {
    
    private final InetAddress grupo;
    
    private final MulticastSocket socket;
    
    public Receiver () throws UnknownHostException, IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        this.grupo = InetAddress.getByName("224.0.0.0");
        this.socket = new MulticastSocket(4000);
        this.socket.joinGroup(this.grupo);
    }
    
    @Override
    public void run () {        
        try {
            while(true) {
                byte[] buffer = new byte[1000];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);
                System.out.println("RECEBIDO: " + new String(buffer));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
