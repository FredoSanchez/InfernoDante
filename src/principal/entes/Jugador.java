/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.control.GestorControles;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

/**
 *
 * @author ferna
 */
public class Jugador {

    private double posicionX;
    private double posicionY;

    private int estadoAnimacion;

    //Se ocupa char por que se utiliza menos memoria
    private int direccion;

    private boolean enMovimiento;

    private HojaSprites hs;

    // Se crea esta variable para poder almacenar la imagen
    private BufferedImage imagenActual;

    public Jugador(double posicionX, double posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;

        enMovimiento = false;

        estadoAnimacion = 0;

        direccion = 0;

        hs = new HojaSprites("/imagenes/hojasTexturas/personaje.png", Constantes.LADO_SPRITE, true);

        imagenActual = hs.getSprites(0).getImagen();

    }

    public void actualizar() {
        if (GestorControles.teclado.isArriba()) {
            direccion = 1;
            posicionY -= 0.5;
            enMovimiento = true;
            animar(direccion);

        }

        if (GestorControles.teclado.isAbajo()) {
            direccion = 0;
            posicionY += 0.5;
            enMovimiento = true;
            animar(direccion);
        }

        if (GestorControles.teclado.isIzquierda()) {
            direccion = 3;
            posicionX -= 0.5;
            enMovimiento = true;
            animar(direccion);
        }

        if (GestorControles.teclado.isDerecha()) {
            direccion = 2;
            posicionX += 0.5;
            enMovimiento = true; 
            animar(direccion);
        }

        
        enMovimiento = false;

    }

    private void animar(int direccion) {
        int frecuenciaAnimacion = 10; // Frecuencia en la que quiero se actualice la animacion 60 / 15 = 4 a 4 se actualizara la animacion del personaje 
        int limiteEstado = 4;
        if (enMovimiento) {
            if (Constantes.APS % frecuenciaAnimacion == 0) {
                estadoAnimacion++;
                if (estadoAnimacion >= limiteEstado) {
                    estadoAnimacion = 0;
                }

                switch (estadoAnimacion) {
                    case 0:
                        imagenActual = hs.getSprites(direccion, 1).getImagen();
                        break;
                    case 1:
                        imagenActual = hs.getSprites(direccion, 0).getImagen();
                        break;
                    case 2:
                        imagenActual = hs.getSprites(direccion, 2).getImagen();
                        break;
                    case 3:
                        imagenActual = hs.getSprites(direccion, 0).getImagen();
                        break;
                }
            }
        } else{
            imagenActual = hs.getSprites(direccion,0).getImagen();
        }

    }

    public void dibujar(Graphics g) {
        final int centroX = Constantes.ANCHO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;
        final int centroY = Constantes.ALTO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;

        g.setColor(Color.green);

        g.drawImage(imagenActual, centroX, centroY, null);
        g.drawRect(centroX + 5, centroY, 20, 32);

    }

    public void establecerPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public void establecerPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    public double obtenerPosicionX() {
        return posicionX;
    }

    public double obtenerPosicionY() {
        return posicionY;
    }

}
