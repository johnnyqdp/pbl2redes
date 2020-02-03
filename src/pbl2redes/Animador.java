package pbl2redes;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Animador extends Thread {
    
    private final Interface inter;
    
    private final JLabel background;
    
    private final ArrayList<JLabel> ruasCarros;
    
    private ArrayList<ArrayList> execucao = new ArrayList<>();
    private ArrayList<String> filaInicial = new ArrayList<>();
    
    private final int[] coordenadasChegadaX = {256, 450, 210, 10};
    private final int[] coordenadasChegadaY = {450, 200, 10, 238};
    private final int[] coordenadasCruzamentoX = {256, 300 , 210, 170};
    private final int[] coordenadasCruzamentoY = {270, 200, 160 , 238};
    private final int[] coordenadasSaindoX = {256};
    private final int[] coordenadasSaindoY = {400};
    
    public Animador (Interface i) {
        this.inter = i;
        this.background = i.getThisBackground();
        ruasCarros = new ArrayList<>();
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("red.jpg"))));
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("blue.jpg"))));
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("green.jpg"))));
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("yellow.jpg"))));
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                if (filaInicial.size() > 0) {
                    System.out.println("\t\t\tPRIMEIRO IF");
                    executarPosicionamento();
                    filaInicial = new ArrayList<>();
                } else if (execucao.size() > 0) {
                    System.out.println("\t\t\tSEGUNDO IF");
                    fazerTudoSumir();
                    executarAnimacao();   
                    execucao = new ArrayList<>();
                    Thread.sleep(3000);
                    fazerTudoSumir();
                }           
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private void executarPosicionamento() {
        posicionar(coordenadasChegadaX, coordenadasChegadaY);
    }
    
    private void colocarTodosNoCruzamento() {
        posicionar(coordenadasCruzamentoX, coordenadasCruzamentoY);
    }

    private void executarAnimacao() {
        colocarTodosNoCruzamento();
        boolean flagJatem[] = {false,false,false,false};
    }
    
    private void fazerTudoSumir() {
        for (int i =0; i<ruasCarros.size(); i++) {
            background.remove(ruasCarros.get(i));
        }
        inter.repaint();
    }
    
    
    public void setExecucao(ArrayList<ArrayList> execucao) {
        this.execucao = execucao;
    }

    public void setFilaInicial(ArrayList<String> filaInicial) {
        this.filaInicial = filaInicial;
    }

    private void posicionar(int[] coordenadasX, int[] coordenadasY) {
        boolean flagJatem[] = {false,false,false,false};
        
        for (int i=0; i<filaInicial.size(); i++) {
            
            String item = filaInicial.get(i);
            int a = item.charAt(0)-'0';
            
            JLabel carro;
            if (!flagJatem[a]) {
                flagJatem[a] = true;
                carro = ruasCarros.get(i);
                carro.setBounds(coordenadasX[a], coordenadasY[a], 30, 30);
            } else {
                carro = new JLabel(new ImageIcon(getClass().getResource("purple.jpg")));
                carro.setBounds(coordenadasX[a]-5, coordenadasY[a]-5, 30, 30);
                ruasCarros.add(carro);
            }
            carro.setVisible(true);
            background.add(carro);
            inter.repaint();
        }
    }
    
}
