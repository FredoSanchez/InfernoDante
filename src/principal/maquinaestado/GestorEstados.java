/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.maquinaestado;

import java.awt.Graphics;
import principal.maquinaestado.estados.juego.GestorJuego;

/**
 *
 * @author fredy
 */
public class GestorEstados {
    
    private EstadoJuego[] estados;
    private EstadoJuego estadoActual;
    
    public GestorEstados(){
        iniciarEstados();
        iniciarEstadoActual();
    }

    private void iniciarEstados() {
        estados = new EstadoJuego[1];
        estados[0] = new GestorJuego();
        
        //Añadir e iniciar demas estados de juego a medida que los creemos
    }

    private void iniciarEstadoActual() {
        estadoActual = estados[0];
        
    }
    
    public void actualizar(){
        estadoActual.actualizar();
    }
    
    public void dibujar(final Graphics g){
        estadoActual.dibujar(g);
    }
    
    public void cambiarEstadoActual(final int nuevoEstado){
        estadoActual = estados[nuevoEstado];
    }
    
    public EstadoJuego obtenerEstadoActual(){
        return estadoActual;
    }
}
