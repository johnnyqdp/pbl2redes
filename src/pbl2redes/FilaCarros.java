package pbl2redes;

import java.util.ArrayList;
import java.util.Collections;

public class FilaCarros extends Thread {

    public final ArrayList<String> filaInicial;
    
    public final ArrayList<String> carrosReserva;
    
    public ArrayList<ArrayList> filasGeradas;
    
    private boolean flagIsFinalizado;
    
    private final Sender threadSender;
    
    private final Interface i;

    public FilaCarros(Sender threadSender, Interface i) {
        filaInicial = new ArrayList<>();
        carrosReserva = new ArrayList<>();
        filasGeradas = new ArrayList<>();
        this.threadSender = threadSender;
        this.i = i;
    }
            
    public void adicionar(String msg) {
        if (!flagIsFinalizado && filaInicial.size() < 4) {
            pegarCarrosReserva();
            if (filaInicial.size() < 4) {
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
        
        Collections.sort(filaInicial);
        ArrayList<String> filaInicialCopia = new ArrayList<>(filaInicial);
        
        gerarFilas();
        
        i.setExecucao(filasGeradas, filaInicialCopia);
        
        System.out.println("======== FILAS DE EXECUCAO SIMULTANEAS ========");
        for (int i=0; i<filasGeradas.size(); i++){
            System.out.println("Execucao " + (i+1) + ": " + filasGeradas.get(i));
        }        
        System.out.println("===============================================");
        System.out.println("FILA RESERVA: " + carrosReserva);
        
        filasGeradas = new ArrayList<>();
        limparFilaInicial();
        flagIsFinalizado = false;
    }
    
    private void gerarFilas() {
        
        for (int i=0; i<filaInicial.size(); i++) {
            String item = filaInicial.get(i);
            if (item.equals("R")) {
                continue;
            }
            ArrayList<String> novaFila = new ArrayList<>();
            novaFila.add(item);
            filaInicial.set(i, "R");
            adicionaOsQueNaoBatem(novaFila);
            filasGeradas.add(novaFila);            
        }        
    }
    
    private void adicionaOsQueNaoBatem(ArrayList<String> novaFila) {
        for (int i=0; i<filaInicial.size(); i++) {
            String item = filaInicial.get(i);
            if (item.equals("R")) {
                continue;
            }
            boolean flagBateComAlgum = false;
            for (int j=0; j<novaFila.size(); j++) {
                String item2 = novaFila.get(j);
                flagBateComAlgum = colapso(item, item2);
            }
            if (!flagBateComAlgum) {
                novaFila.add(item);
                filaInicial.set(i, "R");
            }
        }
    }
    
    private boolean colapso(String item, String item2) {
        int a = item.charAt(0)-'0';
        int b = item.charAt(1)-'0';
        int c = item2.charAt(0)-'0';
        int d = item2.charAt(1)-'0';     
        
        //Se ambos estão saindo da mesma rua, um tá atrás do outro. Portanto não tem colapso
        if (a == c) {
            return false;
        }        
        
        //Caso ambos estejam indo pra mesma rua, o colapso é garantido
        if (b == d){
            return true;
        }        
        
        //Caso ambos estejam seguindo reto, porém não estão em sentidos opostos, há colapso
        if ( (!compara(a,c,2) ) && compara(b,a,2) && compara(d,c,2)) {
            return true;
        }      
        
        //Caso o primeiro esteja seguindo reto e o segundo esteja na sua rua à direita:
        if ( (compara(b,a,2)) &&  compara(c,a,1)) {
            return true;
        }
        
        //Caso o segundo esteja seguindo reto e o primeiro esteja na sua rua à direita:
        if ( (compara(d,c,2)) && compara(a,c,1) ) {
            return true;
        }
        
        //Caso o primeiro esteja virando a esquerda, e o segundo esteja na rua da sua direita ou na rua a sua frente
        if ( (compara(b,a,3)) && c!=b ) {
            return true;
        }        
        
        //Caso o segundo esteja virando a esquerda, e o primeiro esteja na rua da sua direita ou na rua a sua frente
        if ( (compara(d,c,3)) && a!=d) {
            return true;
        }
        
        //Caso o primeiro esteja virando a esquerda, e o segundo esteja na rua destino indo pra qualquer direção diferente de direita:
        if (  (compara(b,a,3)) && c==b && (!compara(d,c,1)) ) {
            return true;
        }
        
        //Caso o segundo esteja virando a esquerda, e o primeiro esteja na rua destino indo pra qualquer direção diferente de direita:
        if ( (compara(d,c,3)) && a==d && (!compara(b,a,1)) ) {
            return true;
        }
        
        return false;
        
    }

    /**
     * Verifica se b == a + i nas ruas.
     * Exemplo (0, 3, 1) retorna true, pois 3 + 1 = 0 pois 3 é a ultima rua, se somar 1, volta pra primeira. 
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
            carrosReserva.remove(0);
            if (filaInicial.size() >= 4) {
                flagIsFinalizado = true;
                iniciarExecucao();
                break;
            }
        }
    }
    
}
