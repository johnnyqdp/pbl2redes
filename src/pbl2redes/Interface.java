
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
    
    private final JLabel background;
    
    private Animador animador;
    
    public Interface() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(516,539);
        setTitle("PBL 2 - Concorrencia e Conectividade");
        setLocationRelativeTo(null);
        setLayout(null);
        background = new JLabel(new ImageIcon(getClass().getResource("background.jpg")));
        background.setBounds(0,0, 500, 500);
        add(background);
        setVisible(true);
        animador = new Animador(this);
        animador.start();
    }

    public JLabel getThisBackground() {
        return background;
    }

    public void setFilaInicial(ArrayList<String> filaInicial) {
        animador.setFilaInicial(filaInicial);
    }

    public void setExecucao(ArrayList<ArrayList> filasGeradas) {
        animador.setExecucao(filasGeradas);
    }
    
}
