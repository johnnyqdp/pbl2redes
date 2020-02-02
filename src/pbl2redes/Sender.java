package pbl2redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Random;

public class Sender extends Thread {
    
    private final int idCarro;
    
    private final InetAddress grupo;
    
    private final MulticastSocket socket;
    
    private boolean flagPodeEnviar;
    
    public Sender (int idCarro) throws UnknownHostException, IOException {
        this.idCarro = idCarro;
        //System.setProperty("java.net.preferIPv4Stack", "true");
        this.grupo = InetAddress.getByName("224.0.0.0");
        this.socket = new MulticastSocket();
        this.socket.joinGroup(this.grupo);
    }
    
    public void definePodeEnviar () {
        this.flagPodeEnviar = true;
    }
    
    @Override
    public void run () {
        try {
            while (true) {
                Thread.sleep(5000);
                if (this.flagPodeEnviar) {
                    this.enviarCarro();
                }            
            }
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void enviarCarro () throws IOException {
        String mensagem = this.criarMensagem();
        DatagramPacket packet = new DatagramPacket(
                mensagem.getBytes(), 
                mensagem.length(), 
                this.grupo, 
                4000
        );
        System.out.println("==> Enviando: " + mensagem);
        this.socket.send(packet);
    }

    private String criarMensagem() {
        Random random = new Random();
        //Gerando um numero aleatorio entre 0 e 3:
        int numeroAleatorio = random.nextInt(4);
        return "" + this.idCarro + numeroAleatorio;
    }
    
}
