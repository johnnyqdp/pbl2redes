package pbl2redes;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Johnny
 */
public class Rua {
    
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Insira o codigo da rua: ");
        int codigoRua = in.nextLine().charAt(0)-'0';
        
        Sender threadSender = new Sender(codigoRua);
        threadSender.start();
        
        Receiver threadReceiver = new Receiver(threadSender, codigoRua);
        threadReceiver.start();
        
    }
    
}
