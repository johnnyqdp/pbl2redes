package pbl2redes;

import javax.swing.JLabel;

public class Animador extends Thread {
    
    private final Interface i;
    
    private final JLabel background;
    
    public Animador (Interface i) {
        this.i = i;
        this.background = i.getThisBackground();
    }
    
    @Override
    public void run() {
    
    }
    
    
    
}
