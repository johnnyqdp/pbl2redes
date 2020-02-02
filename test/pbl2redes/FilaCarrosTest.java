/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl2redes;

import org.junit.Test;
import static org.junit.Assert.*;


public class FilaCarrosTest {
    
    public FilaCarrosTest() {
    }

    @Test
    public void testCompara() {
        FilaCarros f = new FilaCarros(null);
        assertEquals(true, f.compara(0,3,1));
    }
    
    @Test
    public void testIniciarExecucao() {
        FilaCarros f = new FilaCarros(null);
        f.adicionar("01");
        f.adicionar("13");
        f.adicionar("20");
        f.adicionar("31");
        f.iniciarExecucao();
    }
    
}
