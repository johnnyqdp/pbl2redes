package pbl2redes;
import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Johnny
 */
public class Sender {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        
        try {
            InetAddress group = InetAddress.getByName("224.0.0.0");
            MulticastSocket socket = new MulticastSocket();
            String message = "Ola Mundo";
            
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, 4004);
            socket.send(packet);
            socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
