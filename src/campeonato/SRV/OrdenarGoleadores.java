/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.SRV.MODELOS.Goleador;
import java.util.Comparator;

/**
 *
 * @author lara
 */
public class OrdenarGoleadores implements Comparator<Goleador>{

    @Override
    public int compare(Goleador t, Goleador t1) {
        int valor = 0;
        if(t.getGoles() > t1.getGoles()){
            valor = -1;
        }else if(t.getGoles() < t1.getGoles()){
            valor = 1;
        }else{
            valor = 0;
        }
        return valor;
    }


    
}
