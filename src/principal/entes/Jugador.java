/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import principal.Constantes;
import principal.sprites.Sprite;

/**
 *
 * @author ferna
 */
public class Jugador {

    private double posicionX;
    private double posicionY;

    private Sprite sprite;

    public Jugador(double posicionX, double posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public void dibujar(Graphics g) {
        final int centroX = Constantes.ANCHO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;
        final int centroY = Constantes.ALTO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;

        g.setColor(Color.white);
        g.fillRect(centroX, centroY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

    }
    
    public void establecerPosicionX(double posicionX){
        this.posicionX = posicionX;
    }
    
    public void establecerPosicionY(double posicionY){
        this.posicionY = posicionY;
    }
    
    public double obtenerPosicionX(){
        return posicionX;
    }
    
    public double obtenerPosicionY(){
        return posicionY;
    }

}
