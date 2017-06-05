/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campeonato.SRV;

import campeonato.SRV.MODELOS.TablaPosiciones;
import java.util.Comparator;

/**
 *
 * @author lara
 */
public class OrdenarTablaPosiciones implements Comparator<TablaPosiciones>{

    @Override
    public int compare(TablaPosiciones t, TablaPosiciones t1) {
        int valor = 0;
        if(t.getPuntos() > t1.getPuntos()){
            valor = -1;
        }else if(t.getPuntos() < t1.getPuntos()){
            valor = 1;
        }else{
            if(t.getGolesDiferencia() > t1.getGolesDiferencia()){
                valor = -1;
            }else if(t.getGolesDiferencia() < t1.getGolesDiferencia()){
                valor = 1;
            }else{
                if(t.getGolesFavor() >= t1.getGolesFavor()){
                    valor = -1;
                }else{
                    valor = 1;
                }
            }
        }
        return valor;
    }
    
}
