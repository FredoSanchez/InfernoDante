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

    //Quiere decir que nos movemos a un pixel por segundo
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

    //resistencia = 600 esta igualado es el numero de actualizacion que puede corre
    // recuperacinon = 100 esta igualado es el numero de actualizaciones que tiene que pasar
    public int resistencia = 600;
    private int recuperacion = 0;
    private int RECUPERACION_MAXIMA = 200;
    private boolean recuperado = true;

    private Mapa mapa;

    public Jugador(double posicionX, double posicionY, Mapa mapa) {
        this.posicionX = posicionX + 140;
        this.posicionY = posicionY + 286;

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
        gestionarVelocidadResitencia();
        cambiarAnimacionEstado();
        enMovimiento = false;
        determinarDireccion();
        animar();

    }

    private void gestionarVelocidadResitencia() {
        //Quiere decir que el personaje puede correr por que todavia tiene resistencia
        if (GestorControles.teclado.corriendo && resistencia > 0) {
            velocidad = 2;
            recuperado = false;
            recuperacion = 0;
        } else {
            velocidad = 1;
            if (!recuperado && recuperacion < RECUPERACION_MAXIMA) {
                //Si la barra de recuperacion es menor a 100 se aumentara
                recuperacion++;
            }
            if (recuperacion == RECUPERACION_MAXIMA && resistencia < 600) {
                resistencia++;
            }

        }
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
            if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
                posicionX += velocidadX * velocidad;
                restarResistencia();

            }

            if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
                posicionX += velocidadX * velocidad;
                restarResistencia();
            }

            if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
                posicionY += velocidadY * velocidad;
                restarResistencia();
            }

            if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
                posicionY += velocidadY * velocidad;
                restarResistencia();

            }

        }

    }

    private void restarResistencia() {
        if (GestorControles.teclado.corriendo && resistencia > 0) {
            resistencia--;
        }
    }

    private boolean enColisionArriba(int velocidadY) {
        for (int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);

            int origenX = area.x;

            int origenY = area.y + velocidadY * (int) velocidad + 3 * (int) velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (LIMITE_ARRIBA.intersects(areaFutura)) {
                return true;
            }
        }

        return false;
    }

    private boolean enColisionAbajo(int velocidadY) {
        for (int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);

            int origenX = area.x;

            int origenY = area.y + velocidadY * (int) velocidad - 3 * (int) velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (LIMITE_ABAJO.intersects(areaFutura)) {
                return true;
            }
        }

        return false;
    }

    private boolean enColisionIzquierda(int velocidadX) {
        for (int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);

            int origenX = area.x + velocidadX * (int) velocidad + 3 * (int) velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
                return true;
            }
        }

        return false;
    }

    private boolean enColisionDerecha(int velocidadX) {
        for (int r = 0; r < mapa.areasColision.size(); r++) {
            final Rectangle area = mapa.areasColision.get(r);

            int origenX = area.x + velocidadX * (int) velocidad - 3 * (int) velocidad;;

            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (LIMITE_DERECHA.intersects(areaFutura)) {
                return true;
            }
        }

        return false;
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
        g.drawString("Resistencia: " + resistencia, 20, 40);

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
