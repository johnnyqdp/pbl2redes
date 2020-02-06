
package pbl2redes;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Johnny
 */
public class Interface extends JFrame {
    
    ImageIcon iconBackground = new ImageIcon(getClass().getResource("background.jpg"));
    ImageIcon[] iconCarros = {
        new ImageIcon(getClass().getResource("blue.jpg")),
        new ImageIcon(getClass().getResource("red.jpg")),
        new ImageIcon(getClass().getResource("yellow.jpg")),
        new ImageIcon(getClass().getResource("green.jpg")),
        new ImageIcon(getClass().getResource("blue.jpg")),
        new ImageIcon(getClass().getResource("red.jpg")),
        new ImageIcon(getClass().getResource("yellow.jpg")),
        new ImageIcon(getClass().getResource("green.jpg")),
        new ImageIcon(getClass().getResource("blue.jpg")),
        new ImageIcon(getClass().getResource("red.jpg")),
        new ImageIcon(getClass().getResource("yellow.jpg")),
        new ImageIcon(getClass().getResource("green.jpg")),
    };
    
    JLabel lBackground = new JLabel(iconBackground);
    JLabel[] lCarros = {
        new JLabel(iconCarros[0]),
        new JLabel(iconCarros[1]),
        new JLabel(iconCarros[2]),
        new JLabel(iconCarros[3]),
        new JLabel(iconCarros[4]),
        new JLabel(iconCarros[5]),
        new JLabel(iconCarros[6]),
        new JLabel(iconCarros[7]),
        new JLabel(iconCarros[8]),
        new JLabel(iconCarros[9]),
        new JLabel(iconCarros[10]),
        new JLabel(iconCarros[11]),
    };
    
    JLabel[] lCarrosSaida = {
        new JLabel(iconCarros[0]),
        new JLabel(iconCarros[1]),
        new JLabel(iconCarros[2]),
        new JLabel(iconCarros[3]),
        new JLabel(iconCarros[4]),
        new JLabel(iconCarros[5]),
        new JLabel(iconCarros[6]),
        new JLabel(iconCarros[7]),
        new JLabel(iconCarros[8]),
        new JLabel(iconCarros[9]),
        new JLabel(iconCarros[10]),
        new JLabel(iconCarros[11]),
    };
    
    private final int[] coordenadasCruzamentoX = {256, 300, 210, 170, 256, 350 , 210, 120, 256, 400 , 210, 70};
    private final int[] coordenadasCruzamentoY = {270, 200, 160 , 238, 320, 200, 110 , 238, 370, 200, 60 , 238};
    private final int[] coordenadasSaindoX = {206, 300, 260, 170, 206, 350 , 260, 120, 206, 400 , 260, 70};
    private final int[] coordenadasSaindoY = {270, 240, 160 , 198, 320, 240, 110 , 198, 370, 240, 60 , 198};
    
    public Interface() {
        ajustarJanela();
        ajustarComponentes();
    }
    
    private void ajustarJanela () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(516,539);
        setTitle("PBL 2 - Concorrencia e Conectividade");
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(null);
        for (int i=0; i<12; i++) {
            add(lCarros[i]);
            add(lCarrosSaida[i]);
        }
        add(lBackground);        
    }
    
    private void ajustarComponentes () {
        lBackground.setBounds(0,0, 500, 500);
        resetarCarros();
    }
    
    private void resetarCarros () {
        for (int i=0; i<12; i++) {
            lCarros[i].setIcon(iconCarros[i]);
            lCarros[i].setBounds(coordenadasCruzamentoX[i], coordenadasCruzamentoY[i], 30, 30);
            lCarros[i].setVisible(false);
            lCarrosSaida[i].setIcon(iconCarros[i]);
            lCarrosSaida[i].setBounds(coordenadasSaindoX[i], coordenadasSaindoY[i], 30, 30);
            lCarrosSaida[i].setVisible(false);
        }
    }

    public void setExecucao(ArrayList<ArrayList> filasGeradas, ArrayList<String> filaInicial) {
        Animador a = new Animador (this, filasGeradas, filaInicial);
        a.start();        
    }
    
    class Animador extends Thread {
        
        Interface i;
        ArrayList<ArrayList> filasGeradas; 
        ArrayList<String> filaInicial;
        
        public Animador (Interface i, ArrayList<ArrayList> filasGeradas, ArrayList<String> filaInicial) {
            this.i = i;
            this.filasGeradas = filasGeradas;
            this.filaInicial = filaInicial;
        }
        
        @Override
        public void run() {
            try{
                int[] somas = {0, 0, 0, 0};
                for (int i=0; i<filaInicial.size(); i++) {
                    String item = filaInicial.get(i);
                    int a = item.charAt(0)-'0';
                    lCarros[a + somas[a]].setVisible(true);
                    somas[a] += 4;
                }
                Thread.sleep(1000);
                int[] somas2 = {0, 0, 0, 0};
                for (int i=0; i<filasGeradas.size(); i++) {
                    ArrayList<String> execucao = filasGeradas.get(i);
                    for (int j=0; j<execucao.size(); j++) {
                        String item = execucao.get(j);
                        int a = item.charAt(0)-'0';
                        int b = item.charAt(1)-'0';
                        lCarros[a + somas2[a]].setVisible(false);
                        lCarrosSaida[b].setIcon(iconCarros[a]);
                        lCarrosSaida[b].setVisible(true);
                        somas2[a] += 4;
                    }
                    Thread.sleep(1000);
                }
                i.resetarCarros();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
