/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.graficos;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import principal.herramientas.CargadorRecursos;

/**
 *
 * @author fredy
 */
public class Ventana extends JFrame{
    //private static final long serialVersionUID = 1L;
    
    private String titulo;
    private final ImageIcon icono;

    public Ventana(final String titulo, final SuperficieDibujo sd) {
        this.titulo = titulo;
        
        BufferedImage imagen = CargadorRecursos.cargarImagenCompatibleTranslucida("/imagenes/iconos/iconoVentana.png");
	this.icono = new ImageIcon(imagen);
        
        configurarVentana(sd);
    }

    private void configurarVentana(final SuperficieDibujo sd) {
        setTitle(titulo);
        setIconImage(icono.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        //setIconImage
        setLayout(new BorderLayout());
        add(sd, BorderLayout.CENTER);
        //setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
}

