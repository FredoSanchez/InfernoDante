/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.mapas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import principal.herramientas.CargadorRecursos;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

/**
 *
 * @author alex
 */
public class Mapa {

    private String[] partes;

    private final int ancho;
    private final int alto;

    private final Sprite[] paleta;

    public final boolean[] colisiones;

    private final int[] sprites;

    public Mapa(final String ruta) {

        String contenido = CargadorRecursos.leerArchivoTexto(ruta);

        partes = contenido.split("\\*");

        ancho = Integer.parseInt(partes[0]);

        alto = Integer.parseInt(partes[1]);

        String hojasUtilizadas = partes[2];
        String[] hojasSeparadas = hojasUtilizadas.split(",");

        //leer paleta de Sprites
        String paletaEntera = partes[3];
        String[] partesPaleta = paletaEntera.split("#");

        paleta = asignarSprites(partesPaleta, hojasSeparadas);

        String colisionesEnteras = partes[4];
        colisiones = extraerColisiones(colisionesEnteras);

        String spritesEnteros = partes[5];

        String[] cadenasSprites = spritesEnteros.split(" ");

        sprites = extraerSprites(cadenasSprites);

    }

    private Sprite[] asignarSprites(final String[] partesPaleta, final String[] hojasSeparadas) {

        Sprite[] paleta = new Sprite[partesPaleta.length];

        HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + hojasSeparadas[0] + ".png", 32, false);

        for (int i = 0; i < partesPaleta.length; i++) {
            String spriteTemporal = partesPaleta[i];

            String[] partesSprite = spriteTemporal.split("\\-");

            int indicePaleta = Integer.parseInt(partesSprite[0]);
            int indiceSpriteHoja = Integer.parseInt(partesSprite[1]);

            paleta[indicePaleta] = hoja.getSprites(indiceSpriteHoja);
        }
        return paleta;
    }

    private boolean[] extraerColisiones(final String cadenacolisiones) {
        boolean[] colisiones = new boolean[cadenacolisiones.length()];

        for (int i = 0; i < cadenacolisiones.length(); i++) {
            if (cadenacolisiones.charAt(i) == '0') {
                colisiones[i] = false;
            } else {
                colisiones[i] = true;
            }
        }
        return colisiones;
    }

    private int[] extraerSprites(final String[] cadenasSprites) {
        ArrayList<Integer> sprites = new ArrayList<Integer>();

        for (int i = 0; i < cadenasSprites.length; i++) {
            if (cadenasSprites[i].length() == 2) {
                sprites.add(Integer.parseInt(cadenasSprites[i]));
            } else {
                String uno = "";
                String dos = "";

                String error = cadenasSprites[i];

                uno += error.charAt(0);
                uno += error.charAt(1);
                dos += error.charAt(2);
                dos += error.charAt(3);

                sprites.add(Integer.parseInt(uno));
                sprites.add(Integer.parseInt(dos));
            }
        }

        int[] vectorSprites = new int[sprites.size()];

        for (int i = 0; i < sprites.size(); i++) {
            vectorSprites[i] = sprites.get(i);
        }

        return vectorSprites;
    }

    public void dibujar(Graphics g) {
        int anchoSprite = this.paleta[0].getAncho();
        int altoSprite = this.paleta[0].getAlto();
        
        for (int y = 0; y < this.alto; y++) {

            for (int x = 0; x < this.ancho; x++) {
                BufferedImage imagen= paleta[sprites[x+y*this.ancho]].getImagen();
                
                g.drawImage(imagen, x*anchoSprite, y*altoSprite,null);

            }
        }
    }

}
