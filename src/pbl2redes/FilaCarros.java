package pbl2redes;

import java.util.ArrayList;

public class FilaCarros extends Thread {

    private final ArrayList<String> filaInicial;
    
    private ArrayList<String> filaX;
    private ArrayList<String> filaY;
    private ArrayList<String> filaZ;
    
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
        if (!flagIsFinalizado && filaInicial.size() <= 4) {
            pegarCarrosReserva();
            filaInicial.add(msg);
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
    

    private void iniciarExecucao () {
        int loop = 0;
        while (!filaInicial.isEmpty()) {
            filaInicial.forEach((String item) -> {
                if (!item.equals("R")){
                    int a = item.charAt(0)-'0';
                    int b = item.charAt(1)-'0';
                    
                    if (compara(b, a, 1)) {
                        filaX.add(item);
                        filaInicial.set(filaInicial.indexOf(item), "R");
                        filaInicial.forEach((String item2) -> {
                            if (!item2.equals("R")) {
                                int c = item2.charAt(0)-'0';
                                int d = item2.charAt(1)-'0';
                                if ((d == b && compara(c, b, 2)) || (d == b && compara(c, b, 1))) {
                                    filaX.add(item2);
                                    filaInicial.set(filaInicial.indexOf(item2), "R");
                                }
                            }
                        });
                        
                    } else if (compara(b, a, 2)) {
                        filaY.add(item);
                        filaInicial.set(filaInicial.indexOf(item), "R");
                        filaInicial.forEach((String item2) -> {
                            if (!item2.equals("R")) {
                                int c = item2.charAt(0)-'0';
                                int d = item2.charAt(1)-'0';
                                if ( (d == b && compara(c, b, 1)) || (d == b && compara(c, b, -1)) || (compara(d, b, 1) && compara(c, b, 1)) || (compara (d, b, 1) && compara(c, b, -1)) || (compara(c, b, -1) && d==a) ) {
                                    filaY.add(item2);
                                    filaInicial.set(filaInicial.indexOf(item2), "R");
                                }
                            }
                        });
                        
                    } else if (compara(b, a, 3)) {                 
                        filaZ.add(item);
                        filaInicial.set(filaInicial.indexOf(item), "R");
                        filaInicial.forEach((String item2) -> {
                            if (!item2.equals("R")) {
                                int c = item2.charAt(0)-'0';
                                int d = item2.charAt(1)-'0';
                                if ( (c == b) || (d == a && compara(c, b, -1)) || (compara(c,b,-1) && compara(d,a,1)) || (d == b && compara(c, a, 1))  || (d==a && !compara(c, a, 2))) {
                                    filaZ.add(item2);
                                    filaInicial.set(filaInicial.indexOf(item2), "R");
                                }
                            }
                        });
                    }
                }
            });
            
            limparFilaInicial();
        }
        System.out.println("==============================");
        System.out.println("FILA X:");
        System.out.println(filaX);
        System.out.println("FILA Y:");
        System.out.println(filaY);
        System.out.println("FILA Z:");
        System.out.println(filaZ);
        System.out.println("==============================");
        filaX = new ArrayList<>();
        filaY = new ArrayList<>();
        filaZ = new ArrayList<>();
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
    private boolean compara(int b, int a, int i) {
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
            carrosReserva.remove(0);
        }
            
        if (filaInicial.size() >= 4 && !flagIsFinalizado) {
            finalizarLaco();
        }
    }
    
}
