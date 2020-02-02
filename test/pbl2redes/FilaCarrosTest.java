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
        f.adicionar("03");
        f.adicionar("23");
        f.adicionar("13");
        f.adicionar("32");
        f.adicionar("02");
        System.out.println(f.carrosReserva);
        System.out.println(f.filaInicial);
        f.iniciarExecucao();
    }
    
}
