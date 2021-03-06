/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.mapas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

/**
 *
 * @author alex
 */
public class Mapa {

    private final String[] partes;

    private final int ancho;
    private final int alto;

    private final Point posicionInicial;
    private final Point puntoSalida;

    private Rectangle zonaSalida;

    private String siguienteMapa;

    private final Sprite[] paleta;

    public final boolean[] colisiones;

    public ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();

    private final int[] sprites;

    private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
    private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;

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

        String posicion = partes[6];
        String[] posiciones = posicion.split("-");

        posicionInicial = new Point();

        posicionInicial.x = Integer.parseInt(posiciones[0]) * Constantes.LADO_SPRITE;
        posicionInicial.y = Integer.parseInt(posiciones[1]) * Constantes.LADO_SPRITE;

        String salida = partes[7];
        String[] datosSalida = salida.split("-");

        puntoSalida = new Point();
        puntoSalida.x = Integer.parseInt(datosSalida[0]);
        puntoSalida.y = Integer.parseInt(datosSalida[1]);
        siguienteMapa = datosSalida[2];

        zonaSalida = new Rectangle();

    }

    private Sprite[] asignarSprites(final String[] partesPaleta, final String[] hojasSeparadas) {

        Sprite[] paleta = new Sprite[partesPaleta.length];

        HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + hojasSeparadas[0] + ".png", 32, false);

        for (int i = 0; i < partesPaleta.length; i++) {
            String spriteTemporal = partesPaleta[i];

            String[] partesSprite = spriteTemporal.split("-");

            int indicePaleta = Integer.parseInt(partesSprite[0]);

            int indiceSpriteHoja = Integer.parseInt(partesSprite[2]);
            System.out.println(paleta.length);

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

    public void actualizar(final int posicionX, final int posicionY) {
        actualizarAreasColision(posicionX, posicionY);
        actualizarZonaSalida(posicionX, posicionY);
    }

    private void actualizarAreasColision(final int posicionX, final int posicionY) {
        if (!areasColision.isEmpty()) {
            areasColision.clear();
        }

        for (int y = 0; y < this.alto; y++) {
            for (int x = 0; x < this.ancho; x++) {
                int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
                int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

                if (colisiones[x + y * this.ancho]) {
                    final Rectangle r = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
                    areasColision.add(r);
                }
            }
        }
    }

    private void actualizarZonaSalida(final int posicionX, final int posicionY) {
        int puntoX = ((int) puntoSalida.getX()) * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
        int puntoY = ((int) puntoSalida.getY()) * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

        zonaSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
        
    }

    public void dibujar(Graphics g, final int posicionX, final int posicionY) {
        /* int anchoSprite = Constantes.LADO_SPRITE;
        int altoSprite = anchoSprite;*/

        for (int y = 0; y < this.alto; y++) {

            for (int x = 0; x < this.ancho; x++) {
                BufferedImage imagen = paleta[sprites[x + y * this.ancho]].getImagen();

                //Esto es para poder poner el personaje en el punto(0,0)
                int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
                int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

                g.drawImage(imagen, puntoX, puntoY, null);

                //g.setColor(Color.green);
                /* Esto es para ver los cuadros de colision
                for(int r = 0; r < areasColision.size(); r++ ){
                    Rectangle area = areasColision.get(r);
                    g.drawRect(area.x, area.y, area.width, area.height);
                }*/
            }
        }
    }

    public Point obtenerPosicionInicial() {
        return posicionInicial;
    }

    public Point obtenerPuntoSalida() {
        return puntoSalida;
    }

    public String obtenerSiguienteMapa() {
        return siguienteMapa;
    }
    public Rectangle obtenerZonaSalida() {
        return zonaSalida;
    }

    //Es para colicionar con el borde del mapa 
    public Rectangle getBordes(final int posicionX, final int posicionY, final int anchoJugador, final int altoJugador) {
        int x = MARGEN_X - posicionX + anchoJugador;
        int y = MARGEN_Y - posicionY + altoJugador;
        int ancho = this.ancho * Constantes.LADO_SPRITE - anchoJugador * 2;
        int alto = this.alto * Constantes.LADO_SPRITE - altoJugador * 2;

        return new Rectangle(x, y, ancho, alto);

    }

}
