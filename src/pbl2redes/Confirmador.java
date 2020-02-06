
package pbl2redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Confirmador extends Thread {
    
    private final String msg;
    private int quantConfirmacoes = 0;
    private final MulticastSocket socket; 
    private final InetAddress grupo;
    private boolean[] confirmou = {false, false, false, false};
    
    public Confirmador (String msg) throws UnknownHostException, IOException {
        this.msg = msg;
        this.grupo = InetAddress.getByName("224.0.0.0");
        this.socket = new MulticastSocket(4000);
        this.socket.joinGroup(this.grupo);
    }
    
    @Override
    public void run () {
        try {
            while (true) {
                byte[] buffer = new byte[5];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);
                String mensagem = new String(buffer);
                if (mensagem.charAt(0) == 'K' && (mensagem.charAt(2) == msg.charAt(0)) && (mensagem.charAt(3) == msg.charAt(1)) ) {
                    confirmou[mensagem.charAt(1)-'0'] = true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean isConfirmadoPorTodos() {
        for (int i=0; i< 4; i++) {
            if (!confirmou[i]) {
                return false;
            }
        }
        return true;
    }
    
}
