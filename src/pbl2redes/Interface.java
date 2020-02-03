
package pbl2redes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Johnny
 */
public class Interface extends JFrame {
    
    private final JLabel background;
    
    private final ArrayList<JLabel> ruasCarros;
    
    private final int[] coordenadasChegadaX = {256, 450, 210, 10};
    private final int[] coordenadasChegadaY = {450, 200, 10, 238};
    private final int[] coordenadasCruzamentoX = {256, 300 , 210, 170};
    private final int[] coordenadasCruzamentoY = {270, 200, 160 , 238};
    private final int[] coordenadasSaindoX = {256};
    private final int[] coordenadasSaindoY = {400};
    
    public Interface() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(516,539);
        setTitle("PBL 2 - Concorrencia e Conectividade");
        setLocationRelativeTo(null);
        setLayout(null);
        background = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        background.setBounds(0,0, 500, 500);
        ruasCarros = new ArrayList<>();
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("red.jpg"))));
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("blue.jpg"))));
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("green.jpg"))));
        ruasCarros.add(new JLabel(new ImageIcon(getClass().getResource("yellow.jpg"))));
        add(background);
    }
    
    public void addCarro(String msg) {
        int a = msg.charAt(0)-'0';
        ruasCarros.get(a).setBounds(coordenadasChegadaX[a], coordenadasChegadaY[a], 30, 30);
        background.add(ruasCarros.get(a));
    }
    
    public static void main(String[] args) {
        Interface aInterface = new Interface();
    }

    public JLabel getThisBackground() {
        return background;
    }
    
}
