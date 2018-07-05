/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import principal.control.Teclado;
import principal.maquinaestado.GestorEstados;

/**
 *
 * @author fredy
 */
public class SuperficieDibujo extends Canvas{
    //public static final long serialVersionUID = 1L;
    private int ancho;
    private int alto;
    
    private Teclado teclado;

    public SuperficieDibujo(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        teclado = new Teclado();
        
        setIgnoreRepaint(true);
        setPreferredSize(new Dimension(ancho, alto));
        addKeyListener(teclado);
        setFocusable(true);
        requestFocus();
        
    }
    
    public void dibujar(GestorEstados ge){
        BufferStrategy buffer = getBufferStrategy();
        if (buffer==null){
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = buffer.getDrawGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, ancho, alto);
        
        ge.dibujar(g);
        
        Toolkit.getDefaultToolkit().sync();
        
        g.dispose();
        
        buffer.show();
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public Teclado getTeclado() {
        return teclado;
    }
    
    
    
}
