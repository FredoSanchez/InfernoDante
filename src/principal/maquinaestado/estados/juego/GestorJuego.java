/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.maquinaestado.estados.juego;

import principal.entes.Jugador;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.control.GestorControles;
import principal.herramientas.CargadorRecursos;
import principal.interfezusuario.HUD;
import principal.mapas.Mapa;
import principal.maquinaestado.EstadoJuego;
import principal.sprites.HojaSprites;

/**
 *
 * @author fredy
 */
public class GestorJuego implements EstadoJuego {

   // String texto = CargadorRecursos.leerArchivoTexto("/texto/prueba.ad");

    
    Mapa mapa;
    Jugador jugador;

    public GestorJuego() {
        iniciarMapa(Constantes.RUTA_MAPA);
        iniciarJugador();
        
    }
    
    private void recargarJuego(){
        final String ruta = "/mapas/"+mapa.obtenerSiguienteMapa();
        
        iniciarMapa(ruta);
        iniciarJugador();
    }
    
    private void iniciarMapa(final String ruta){
        mapa = new Mapa(ruta);
    }
    
    private void iniciarJugador(){
        jugador = new Jugador(mapa);
    }

    @Override
    public void actualizar() {
        if(jugador.obtener_LIMITE_ARRIBA().intersects(mapa.obtenerZonaSalida())){
            recargarJuego();
        }
        
        jugador.actualizar();
        mapa.actualizar((int)jugador.obtenerPosicionX(),(int)jugador.obtenerPosicionY());
    }

    @Override
    public void dibujar(Graphics g) {
        mapa.dibujar(g,(int)jugador.obtenerPosicionX(), (int)jugador.obtenerPosicionY());
        jugador.dibujar(g);
        
        g.setColor(Color.red);
        g.drawString("X = " + jugador.obtenerPosicionX(), 20, 20);
        g.drawString("Y = " + jugador.obtenerPosicionY(), 20, 30);
        
        g.fillRect(mapa.obtenerZonaSalida().x,mapa.obtenerZonaSalida().y,mapa.obtenerZonaSalida().width,mapa.obtenerZonaSalida().height);
        
        g.drawString("Siguienta mapa:  " + mapa.obtenerSiguienteMapa(),20, 130);
        g.drawString("Coordenadas salida X: " + mapa.obtenerPuntoSalida().getX() + "  Y: " + mapa.obtenerPuntoSalida().getY(), 20, 140);
        
        
        HUD.dibujarBarraResistencia(g, jugador.resistencia);
    }

}
