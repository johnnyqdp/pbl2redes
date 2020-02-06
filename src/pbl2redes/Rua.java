package pbl2redes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Johnny
 */
public class Rua {
    
    public static void main(String[] args) throws IOException {
        Interface i = new Interface();
        
        
//        ArrayList<String> filaInicial = new ArrayList<>();
//        filaInicial.add("03");
//        filaInicial.add("32");
//        filaInicial.add("30");
//        filaInicial.add("30");
//        filaInicial.add("12");
//        filaInicial.add("10");
//        filaInicial.add("10");
//        filaInicial.add("22");
//        filaInicial.add("20");
//        filaInicial.add("20");
//         filaInicial.add("01");
//         filaInicial.add("01");
//        
//        ArrayList<ArrayList> filasGeradas = new ArrayList<>();
//        i.setExecucao(filasGeradas, filaInicial);
        
        
        
        Scanner in = new Scanner(System.in);
        System.out.println("Insira o codigo da rua: ");
        int codigoRua = in.nextLine().charAt(0)-'0';
        
        Sender threadSender = new Sender(codigoRua);
        threadSender.start();
        
        Receiver threadReceiver = new Receiver(threadSender, codigoRua, i);
        threadReceiver.start();
        
    }
    
}
