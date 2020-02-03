package pbl2redes;

import java.util.ArrayList;

public class FilaCarros extends Thread {

    public final ArrayList<String> filaInicial;
    
    public final ArrayList<String> carrosReserva;
    
    public ArrayList<ArrayList> filasGeradas;
    
    private boolean flagIsFinalizado;
    
    private final Sender threadSender;
    
    private final Interface i;

    public FilaCarros(Sender threadSender) {
        filaInicial = new ArrayList<>();
        carrosReserva = new ArrayList<>();
        filasGeradas = new ArrayList<>();
        this.threadSender = threadSender;
        this.i = new Interface();
    }
            
    public void adicionar(String msg) {
        if (!flagIsFinalizado && filaInicial.size() < 4) {
            pegarCarrosReserva();
            if (filaInicial.size() < 4) {
                i.addCarro(msg);
                filaInicial.add(msg);
            } else {
                carrosReserva.add(msg);
                System.out.println("Adicionando " + msg + " na fila reserva.");
                flagIsFinalizado = true;
                iniciarExecucao();
            }
        } else {
            carrosReserva.add(msg);
            System.out.println("Adicionando " + msg + " na fila reserva.");
        }
    }
    
    public void finalizarLaco() {
        if (!flagIsFinalizado) {
            flagIsFinalizado = true;
            System.out.println("[Laço de filaCarros finalizado]");
            iniciarExecucao();
        }
    }
    

    public void iniciarExecucao () {

        for (int i=0; i<filaInicial.size(); i++) {
            String item = filaInicial.get(i);
            if (!item.equals("R")){
                int a = item.charAt(0)-'0';
                int b = item.charAt(1)-'0';                    
                if (compara(b, a, 1)) {
                    ArrayList<String> filaNova = new ArrayList<>();
                    filaNova.add(item);
                    filaInicial.set(filaInicial.indexOf(item), "R");
                    for (int j=0; j<filaInicial.size(); j++) {
                        String item2 = filaInicial.get(j);
                        if (!item2.equals("R")) {
                            int c = item2.charAt(0)-'0';
                            int d = item2.charAt(1)-'0';
                            if ((d == b)) {
                                filaNova.add(item2);
                                filaInicial.set(filaInicial.indexOf(item2), "R");
                            }
                        }
                    }
                    filasGeradas.add(filaNova);
                }
            }
        }
        
        for (int i=0; i<filaInicial.size(); i++) {
            String item = filaInicial.get(i);                
            if (!item.equals("R")){
                int a = item.charAt(0)-'0';
                int b = item.charAt(1)-'0';                    
                if (compara(b, a, 2)) {
                    ArrayList<String> filaNova = new ArrayList<>();
                    filaNova.add(item);
                    filaInicial.set(filaInicial.indexOf(item), "R");
                    for (int j=0; j<filaInicial.size(); j++) {
                        String item2 = filaInicial.get(j);
                        if (!item2.equals("R")) {
                            int c = item2.charAt(0)-'0';
                            int d = item2.charAt(1)-'0';
                            if ( (d == b && compara(c, b, 1)) || (d == b && compara(c, b, -1)) || (compara(d, b, 1) && compara(c, b, 1)) || (compara (d, b, 1) && compara(c, b, -1)) || (compara(c, b, -1) && d==a) || (b==c && compara(d,a,1))) {
                                filaNova.add(item2);
                                filaInicial.set(filaInicial.indexOf(item2), "R");
                            }
                        }
                    }
                    filasGeradas.add(filaNova);
                }
            }
        }

        for (int i=0; i<filaInicial.size(); i++) {
            String item = filaInicial.get(i);
            if (!item.equals("R")){
                int a = item.charAt(0)-'0';
                int b = item.charAt(1)-'0';                    
                if (compara(b, a, 3)) {                 
                    ArrayList<String> filaNova = new ArrayList<>();
                    filaNova.add(item);
                    filaInicial.set(filaInicial.indexOf(item), "R");
                    for (int j=0; j<filaInicial.size(); j++) {
                        String item2 = filaInicial.get(j);
                        if (!item2.equals("R")) {
                            int c = item2.charAt(0)-'0';
                            int d = item2.charAt(1)-'0';
                            if ( (c == b) || (d == a && compara(c, b, -1)) || (compara(c,b,-1) && compara(d,a,1)) || (d == b && compara(c, a, 1)) ) {
                                filaNova.add(item2);
                                filaInicial.set(filaInicial.indexOf(item2), "R");
                            }
                        }
                    }
                    filasGeradas.add(filaNova);
                }
            }
        }
        
        System.out.println("======== FILAS DE EXECUCAO SIMULTANEAS ========");

        for (int i=0; i<filasGeradas.size(); i++){
            System.out.println("Fila " + (i+1) + ": " + filasGeradas.get(i));
        }
        
        System.out.println("===============================================");
        System.out.println("FILA RESERVA: " + carrosReserva);
        filasGeradas = new ArrayList<>();
        limparFilaInicial();
        flagIsFinalizado = false;
    }

    /**
     * Verifica se b == a + i nas ruas.
     * Exemplo (3, 0, 1) retorna true, pois 3 + 1 = 0 pois 3 é a ultima rua, se somar 1, volta pra primeira. 
     * @param b
     * @param a
     * @param i
     * @return 
     */
    public boolean compara(int b, int a, int i) {
        int comparativo = a + i;
        if ( (a == 2 && i == 2) || (a == 3 && i== 1) || (a == 1 && i == 3)) {
            comparativo = 0;
        } else if (a == 3 && i == 2 || (a == 2 && i == 3)) {
            comparativo = 1;
        } else if (a == 0 && i == -1) {
            comparativo = 3;
        } else if (a == 3 && i == 3) {
            comparativo = 2;
        }
        
        if (comparativo > 3 || comparativo < 0) {
            System.out.println("ERRO NA FUNCAO COMPARA, recebidos (b, a, i):" + b + a + i);
            System.exit(0);
        }
        
        return b == comparativo;
    }

    private void limparFilaInicial() {
        while (filaInicial.contains("R")) {
            for (int i=0; i< filaInicial.size(); i++) {
                if (filaInicial.get(i).equals("R")) {
                    filaInicial.remove(i);
                    break;
                }
            }
        }
    }

    private void pegarCarrosReserva() {
        while (carrosReserva.size() > 0 && filaInicial.size() < 4 && !flagIsFinalizado) {
            filaInicial.add(carrosReserva.get(0));
            i.addCarro(carrosReserva.get(0));
            carrosReserva.remove(0);
            if (filaInicial.size() >= 4) {
                flagIsFinalizado = true;
                iniciarExecucao();
                break;
            }
        }
    }
    
}
