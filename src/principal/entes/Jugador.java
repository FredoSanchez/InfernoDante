/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import principal.Constantes;
import principal.control.GestorControles;
import principal.mapas.Mapa;
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

    private double velocidad = 1;

    private boolean enMovimiento;

    private HojaSprites hs;

    // Se crea esta variable para poder almacenar la imagen
    private BufferedImage imagenActual;

    private final int ANCHO_JUGADOR = 16;
    private final int ALTO_JUGADOR = 16;

    private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2, Constantes.CENTRO_VENTANA_Y, ANCHO_JUGADOR, 1);
    private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2, Constantes.CENTRO_VENTANA_Y + ALTO_JUGADOR, ANCHO_JUGADOR, 1);
    private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2, Constantes.CENTRO_VENTANA_Y, 1, ALTO_JUGADOR);
    private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_JUGADOR / 2, Constantes.CENTRO_VENTANA_Y, 1, ALTO_JUGADOR);

    private int animacion;
    private int estado;

    private Mapa mapa;

    public Jugador(double posicionX, double posicionY, Mapa mapa) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;

        enMovimiento = false;

        estadoAnimacion = 0;

        direccion = 0;

        hs = new HojaSprites(Constantes.RUTA_PERSONAJE, Constantes.LADO_SPRITE, false);

        imagenActual = hs.getSprites(0).getImagen();

        animacion = 0;
        estado = 0;

        this.mapa = mapa;

    }

    public void actualizar() {
        cambiarAnimacionEstado();
        enMovimiento = false;
        determinarDireccion();
        animar();

    }

    private void cambiarAnimacionEstado() {
        if (animacion < 30) {
            animacion++;
        } else {
            animacion = 0;
        }

        if (animacion < 15) {
            estado = 1;
        } else {
            estado = 2;
        }

    }

    private void determinarDireccion() {
        final int velocidadX = velocidadX();
        final int velocidadY = velocidadY();

        if (velocidadX == 0 && velocidadY == 0) {
            return;
        }

        //Esto para que el personaje no se pueda mover en diagonal 
        if ((velocidadX != 0 && velocidadY == 0) || ((velocidadX == 0 && velocidadY != 0))) {
            mover(velocidadX, velocidadY);
        } else {
            // izquierda y arriba
            if (velocidadX == -1 && velocidadY == -1) {
                if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }
            //izquierda y abajo
            if (velocidadX == -1 && velocidadY == 1) {
                if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }
            // derecha y arriba
            if (velocidadX == 1 && velocidadY == -1) {
                if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }
            //derecha y abajo 
            if (velocidadX == 1 && velocidadY == -1) {
                if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velocidadX, 0);
                } else {
                    mover(0, velocidadY);
                }
            }

        }

    }

    private int velocidadX() {
        int velocidadX = 0;

        if (GestorControles.teclado.izquierda.isPulsada() && !GestorControles.teclado.derecha.isPulsada()) {
            velocidadX = -1;
        } else if (GestorControles.teclado.derecha.isPulsada() && !GestorControles.teclado.izquierda.isPulsada()) {
            velocidadX = 1;
        }

        return velocidadX;

    }

    private int velocidadY() {
        int velocidadY = 0;

        if (GestorControles.teclado.arriba.isPulsada() && !GestorControles.teclado.abajo.isPulsada()) {
            velocidadY = -1;
        } else if (GestorControles.teclado.abajo.isPulsada() && !GestorControles.teclado.arriba.isPulsada()) {
            velocidadY = 1;
        }

        return velocidadY;
    }

    private void mover(int velocidadX, int velocidadY) {
        enMovimiento = true;

        cambiarDireccion(velocidadX, velocidadY);

        if (!fueraMapa(velocidadX, velocidadY)) {
            posicionX += velocidadX * velocidad;
            posicionY += velocidadY * velocidad;

        }

    }

    //Al poner (int) al frente de una variable double se llama conversion explicita
    private boolean fueraMapa(final int velocidadX, final int velocidadY) {

        int posicionFuturaX = (int) posicionX + velocidadX * (int) velocidad;
        int posicionFuturaY = (int) posicionY + velocidadY * (int) velocidad;

        final Rectangle bordesMapa = mapa.getBordes(posicionFuturaX, posicionFuturaY, ANCHO_JUGADOR, ALTO_JUGADOR);

        final boolean fuera;

        if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa) || LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)) {
            fuera = false;
        } else {
            fuera = true;
        }

        return fuera;
    }

    private void cambiarDireccion(int velocidadX, int velocidadY) {
        //velocidadX == -1 quiere decir que nos estamos moviendo hacia la izquieda
        if (velocidadX == -1) {
            direccion = 3;
        } else if (velocidadX == 1) { // velocidadX = 1 quiere decir que nos estamos moviendo hacia derecha  
            direccion = 2;
        }
        //velocidadY = -1 quiere decir que nos estamos moviendo hacia la arriba
        if (velocidadY == -1) {
            direccion = 1;
        } else if (velocidadY == 1) {  //velocidadX = 1 quiere decir que nos estamos moviendo hacia la abajo
            direccion = 0;
        }
    }

    private void animar() {
        if (!enMovimiento) {
            estado = 0;
            animacion = 0;
        }

        imagenActual = hs.getSprites(direccion, estado).getImagen();
    }

    public void dibujar(Graphics g) {
        final int centroX = Constantes.ANCHO_VENTANA / 2 - Constantes.LADO_SPRITE / 2;
        final int centroY = Constantes.ALTO_VENTANA / 2 - Constantes.LADO_SPRITE / 2;

        g.setColor(Color.green);

        g.drawImage(imagenActual, centroX, centroY, null);
        g.drawRect(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height);
        g.drawRect(LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height);
        g.drawRect(LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height);
        g.drawRect(LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height);

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
