package pbl2redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Receiver extends Thread {
    
    private final InetAddress grupo;
    
    private final MulticastSocket socket;
    
    private final FilaCarros fila;
    
    private final Sender threadSender;
    
    private final int idRua;
    
    private boolean todasAsRuasEstaoNoGrupo = false;
    
    private final ArrayList<Integer> ruas;
    
    public Receiver (Sender threadSender, int idRua, Interface i) throws UnknownHostException, IOException {
        //System.setProperty("java.net.preferIPv4Stack", "true");
        this.grupo = InetAddress.getByName("224.0.0.0");
        this.socket = new MulticastSocket(4000);
        this.socket.joinGroup(this.grupo);
        this.threadSender = threadSender;
        this.fila = new FilaCarros(threadSender, i);
        this.idRua = idRua;
        ruas = new ArrayList<>();
    }
    
    @Override
    public void run () {
        System.out.println("Aguardando todos os carros entrarem no grupo...");
        while (!this.todasAsRuasEstaoNoGrupo) {
            verificaSeTodasAsRuasEstaoNoGrupo();
        }
        threadSender.definePodeEnviar(true);
        execucaoPadrao();
    }
    
    private void execucaoPadrao () {        
        try {
            while(true) {
                byte[] buffer = new byte[3];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);
                String msg = new String(buffer);
                if (msg.equals("ola")){
                    continue;
                } else if (msg.charAt(0) == 'C') {
                    System.out.println("Recebido: " + msg + " <==");
                    this.fila.finalizarLaco();
                    this.threadSender.ignoraChegarCruzamento();
                    continue;
                }
                System.out.println("Recebido: " + msg + " <==");
                this.fila.adicionar(msg);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void verificaSeTodasAsRuasEstaoNoGrupo() {
        try {
            while(true) {
                byte[] buffer = new byte[5];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);
                String msg = new String(buffer);
                if (!msg.contains("ola")) {
                    System.out.println("RECEBIDA MENSAGEM INESPERADA");
                    System.exit(0);
                }
                int rua = msg.charAt(4)-'0';
                adicionarRuaNoGrupo(rua);
                if (ruas.contains(0) && ruas.contains(1) && ruas.contains(2) && ruas.contains(3)) {
                    this.todasAsRuasEstaoNoGrupo = true;
                    System.out.println("[!] Todas as ruas estão no grupo [!]");
                    this.threadSender.enviarOi();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void adicionarRuaNoGrupo(int rua) {
        if (!ruas.contains(rua)) {
            ruas.add(rua);
            System.out.println("[!] Rua " + rua + " entrou no grupo. [!]");
        }
    }
    
}
