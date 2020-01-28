package pbl2redes;
import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Johnny
 */
public class Receiver {
    
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        
        try {        
            InetAddress group = InetAddress.getByName("224.0.0.0");
            MulticastSocket socket = new MulticastSocket(4004);
            socket.joinGroup(group);
            
            int i = 0;
            while(i < 10) {
                byte[] buffer = new byte[100];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                System.out.println(new String(buffer));
                i++;
            }
            socket.close();
            System.out.println("Closed socket");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
