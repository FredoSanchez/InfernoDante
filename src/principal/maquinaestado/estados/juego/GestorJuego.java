/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.maquinaestado.estados.juego;

import entes.Jugador;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import principal.control.GestorControles;
import principal.herramientas.CargadorRecursos;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;
import principal.sprites.HojaSprites;

/**
 *
 * @author fredy
 */
public class GestorJuego implements EstadoJuego {

   // String texto = CargadorRecursos.leerArchivoTexto("/texto/prueba.ad");

    Jugador jugador = new Jugador(0,0);
    Mapa mapa = new Mapa("/texto/prueba.ad");

    @Override
    public void actualizar() {
        if(GestorControles.teclado.isArriba()){
            jugador.establecerPosicionY(jugador.obtenerPosicionY() - 0.5);
            System.out.println("Arriba");
        }
        
        if(GestorControles.teclado.isAbajo()){
            jugador.establecerPosicionY(jugador.obtenerPosicionY() + 0.5);
        }
        
        if(GestorControles.teclado.isIzquierda()){
            jugador.establecerPosicionX(jugador.obtenerPosicionX() - 0.5);
        }
        
        if(GestorControles.teclado.isDerecha()){
            jugador.establecerPosicionX(jugador.obtenerPosicionX() + 0.5);
        }
    }

    @Override
    public void dibujar(Graphics g) {
        mapa.dibujar(g,(int)jugador.obtenerPosicionX(), (int)jugador.obtenerPosicionY());
        jugador.dibujar(g);
        
        g.setColor(Color.red);
        g.drawString("X = " + jugador.obtenerPosicionX(), 20, 20);
        g.drawString("Y = " + jugador.obtenerPosicionY(), 20, 30);
    }

}
