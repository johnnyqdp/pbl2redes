package pbl2redes;

import java.util.ArrayList;

public class FilaCarros extends Thread {

    private final ArrayList<String> filaInicial;
    
    private final ArrayList<String> filaX;
    private final ArrayList<String> filaY;
    private final ArrayList<String> filaZ;
    
    private final ArrayList<String> carrosReserva;
    
    private boolean flagIsFinalizado;
    
    private final Sender threadSender;

    public FilaCarros(Sender threadSender) {
        filaInicial = new ArrayList<>();
        filaX = new ArrayList<>();
        filaY = new ArrayList<>();
        filaZ = new ArrayList<>();
        carrosReserva = new ArrayList<>();
        this.threadSender = threadSender;
    }
            
    public void adicionar(String msg) {
        if (!flagIsFinalizado) {
            filaInicial.add(msg);
            if (filaInicial.size() >= 4) {
                flagIsFinalizado = true;
                iniciarExecucao();
            }
        } else {
            carrosReserva.add(msg);
        }
    }

    private void iniciarExecucao() {
        int loop = 0;
        while (!filaInicial.isEmpty()) {
            filaInicial.forEach((String item) -> {
                int a = item.charAt(0)-'0';
                int b = item.charAt(1)-'0';
                if (compara(b, a, 1)) {

                }
            });
        }
    }
//        for (int i = 0; i < filaInicial.size(); i++) {
//            String item = filaInicial.get(i);
//            int a = item.charAt(0)-'0';
//            int b = item.charAt(1)-'0';
//            
//            if (b == a+1) {
//                continue;
//            }
//            
//            if (b == a+2) {
//                continue;
//            }
//            
//            if (b == a + 3) {
//                continue;
//            }
//            
//        }
///    }

    /**
     * Verifica se b == a + i nas ruas.
     * Exemplo (3, 0, 1) retorna true, pois 3 + 1 = 0 pois 3 é a ultima rua, se somar 1, volta pra primeira. 
     * @param b
     * @param a
     * @param i
     * @return 
     */
    private boolean compara(int b, int a, int i) {
        if ( (a == 2 && i == 2) || (a == 3 && i== 1)) {
            return b == 0;
        } else if (a == 3 && i == 2) {
            return b == 1;
        }
        return b == a + i;
    }
    
}
