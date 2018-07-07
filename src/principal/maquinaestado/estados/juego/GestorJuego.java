/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.maquinaestado.estados.juego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import principal.herramientas.CargadorRecursos;
import principal.maquinaestado.EstadoJuego;
import principal.sprites.HojaSprites;

/**
 *
 * @author fredy
 */
public class GestorJuego implements EstadoJuego {

    private GestorMapa gestorMapa;

    String texto = CargadorRecursos.leerArchivoTexto("/texto/prueba");

    @Override
    public void actualizar() {
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.white);
        g.drawString(texto, 10, 10);

    }

}
