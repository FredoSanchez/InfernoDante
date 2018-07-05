/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.graficos;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author fredy
 */
public class Ventana extends JFrame{
    //private static final long serialVersionUID = 1L;
    
    private String titulo;

    public Ventana(final String titulo, final SuperficieDibujo sd) {
        this.titulo = titulo;
        
        configurarVentana(sd);
    }

    private void configurarVentana(final SuperficieDibujo sd) {
        setTitle(titulo);
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

