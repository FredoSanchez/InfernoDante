/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.sprites;

import java.awt.image.BufferedImage;
import principal.herramientas.CargadorRecursos;

/**
 *
 * @author fredy
 */
public class HojaSprites {
    
    private final int anchoHojaEnPixeles;
    private final int altoHojaEnPixeles;
    
    private final int anchoHojaEnSprites;
    private final int altoHojaEnSprites;
    
    private final int anchoSprites;
    private final int altoSprites;
  
    private final Sprite[] sprites;

    public HojaSprites(final String ruta, final int tamañoSprites, final boolean hojaOpaca) {
        BufferedImage imagen;
        
        if(hojaOpaca){
            imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
        }else{
            imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
        }
        
        anchoHojaEnPixeles = imagen.getWidth();
        altoHojaEnPixeles = imagen.getHeight();
        
        anchoHojaEnSprites = anchoHojaEnPixeles / tamañoSprites;
        altoHojaEnSprites = altoHojaEnPixeles / tamañoSprites;
        
        anchoSprites = tamañoSprites;
        altoSprites = tamañoSprites;
        
        sprites = new Sprite[anchoHojaEnSprites * altoHojaEnSprites];
        
        rellenarSpritesDesdeImagen(imagen);
        
    }
    
    public HojaSprites(final String ruta, final int anchoSprites,final int altoSprites, final boolean hojaOpaca) {
        final BufferedImage imagen;
        
        if(hojaOpaca){
            imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
        }else{
            imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
        }
        
        anchoHojaEnPixeles = imagen.getWidth();
        altoHojaEnPixeles = imagen.getHeight();
        
        anchoHojaEnSprites = anchoHojaEnPixeles / anchoSprites;
        altoHojaEnSprites = altoHojaEnPixeles / altoSprites;
        
        this.anchoSprites = anchoSprites;
        this.altoSprites = altoSprites;
        
        sprites = new Sprite[anchoHojaEnSprites * altoHojaEnSprites];
        
        rellenarSpritesDesdeImagen(imagen);
        
    }
    
    private void rellenarSpritesDesdeImagen(final BufferedImage imagen){
        for(int y = 0; y < altoHojaEnSprites; y++){
            for(int x = 0; x < anchoHojaEnSprites; x++){                
                final int posicionX = x * anchoSprites;
                final int posicionY = y * altoSprites;
                
                sprites[x + y * anchoHojaEnSprites] = new Sprite(imagen.getSubimage(posicionX, posicionY, anchoSprites, altoSprites));
            }
        }
    }

    public Sprite getSprites(final int indice) {
        return sprites[indice];
    }

    public Sprite getSprites(final int x, final int y) {
        return sprites[x + y * anchoHojaEnSprites];
    }
    
    
    
}
