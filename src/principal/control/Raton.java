/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.control;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;

/**
 *
 * @author fredy
 */
public class Raton extends MouseAdapter{
    private final Cursor cursor;
    private Point posicion;
    
    public Raton(final SuperficieDibujo sd) {
        
		Toolkit configuracion = Toolkit.getDefaultToolkit();

		final BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucida("/imagenes/iconos/iconoCursor.png");
                
                Point punta = new Point(0, 0);
                
		this.cursor = configuracion.createCustomCursor(icono, punta, "Cursor por defecto");

		


		posicion = new Point();
		actualizarPosicion(sd);

	}
        
        public void actualizar(final SuperficieDibujo sd) {
		actualizarPosicion(sd);

	}
        
        public void dibujar(final Graphics g) {
                g.setColor(Color.red);
                
                g.drawString("RX: " + posicion.getX(), 10, 200);
                g.drawString("RY: " + posicion.getY(), 10, 210);
                
	}
    
        public Cursor obtenerCursor() {
		return this.cursor;
	}
        
        private void actualizarPosicion(final SuperficieDibujo sd) {
		final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();

		SwingUtilities.convertPointFromScreen(posicionInicial, sd);

		posicion.setLocation(posicionInicial.getX(), posicionInicial.getY());
	}
}
