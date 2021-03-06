package pbl2redes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Random;

public class Sender extends Thread {
    
    private final int idRua;
    
    private final InetAddress grupo;
    
    private final MulticastSocket socket;
    
    private boolean flagPodeEnviar = false;
    private boolean aindaNaoComecou = true;
    private boolean flagIgnorarCruzamento = false;
    
    public Sender (int idRua) throws UnknownHostException, IOException {
        this.idRua = idRua;
        //System.setProperty("java.net.preferIPv4Stack", "true");
        this.grupo = InetAddress.getByName("224.0.0.0");
        this.socket = new MulticastSocket();
        this.socket.joinGroup(this.grupo);
    }
    
    public void definePodeEnviar (boolean podeEnviar) {
        this.flagPodeEnviar = podeEnviar;
        this.aindaNaoComecou = false;
    }
    
    @Override
    public void run () {
        try {
            while (true) {
                if (this.flagPodeEnviar) {
                    this.enviarCarro();
                } else if (this.aindaNaoComecou){
                    this.enviarOi();
                    Thread.sleep(300);
                }       
            }
        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Gera uma quantidade de segundos entre 3 e 7, espera esse tempo e informa que o carro apareceu na rua
     */    
    private void enviarCarro () throws IOException, InterruptedException {
        Random random = new Random();
        int tempoPraChegar = (random.nextInt(5) + 3);
        Thread.sleep(tempoPraChegar*1000);
        String mensagem = this.criarMensagem();
        DatagramPacket packet = new DatagramPacket(
                mensagem.getBytes(), 
                mensagem.length(), 
                this.grupo, 
                4000
        );
        System.out.println("==> Enviando: " + mensagem);
        
        enviarMensagem (packet, mensagem);        
        chegarNoCruzamento(mensagem);
    }
    
    /**
     * Gera uma quantidade de segundos entre 0 e 1, espera esse tempo e informa que o carro chegou ao cruzamento
     */
    private void chegarNoCruzamento(String msg) throws InterruptedException, IOException {
        Random random = new Random();
        int tempoPraChegar = (random.nextInt(1000));
        Thread.sleep(tempoPraChegar);
        String mensagem = "C" + msg;
        DatagramPacket packet = new DatagramPacket(
                mensagem.getBytes(), 
                mensagem.length(), 
                this.grupo, 
                4000
        );
        if (!this.flagIgnorarCruzamento){
            System.out.println("===> Enviando: " + mensagem);
            this.socket.send(packet); 
        } else {
            this.flagIgnorarCruzamento = false;
        }
    }

    private String criarMensagem() {
        Random random = new Random();
        //Gerando um numero aleatorio entre 0 e 3, diferente do numero da rua atual:
        int numeroAleatorio = this.idRua;
        while (numeroAleatorio == this.idRua) {
            numeroAleatorio = random.nextInt(4);
        }
        return "" + this.idRua + numeroAleatorio;
    }

    public void enviarOi() throws IOException {
        String mensagem = "ola " + this.idRua;
        DatagramPacket packet = new DatagramPacket(
                mensagem.getBytes(), 
                mensagem.length(), 
                this.grupo, 
                4000
        );
        this.socket.send(packet);
    }

    public void ignoraChegarCruzamento() {
        this.flagIgnorarCruzamento = true;
    }

    private void enviarMensagem(DatagramPacket packet, String mensagem) throws IOException, InterruptedException {
        Confirmador confirmador = new Confirmador(mensagem);
        confirmador.start();
        this.socket.send(packet);
        Thread.sleep(1500);
        if (!confirmador.isConfirmadoPorTodos()) {
            System.exit(0);
        } else {
            confirmador.interrupt();
        }
    }
    
    public void enviarConfirmacao (String msg) throws IOException {
        String mensagem = "K" + this.idRua + msg;
        DatagramPacket packet = new DatagramPacket(
                mensagem.getBytes(), 
                mensagem.length(), 
                this.grupo, 
                4000
        );
        for (int i=0; i<5; i++) {
            this.socket.send(packet);
        }
    }
    
}
