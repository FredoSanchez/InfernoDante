/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

/**
 *
 * @author ferna
 */
public class Constantes {

    public static final int LADO_SPRITE = 32;

    public static int ANCHO_JUEGO = 640; //640
    public static int ALTO_JUEGO = 360;  //360

    public static int ANCHO_PANTALLA_COMPLETA = 1220;
    public static int ALTO_PANTALLA_COMPLETA = 720;

    public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA / (double) ANCHO_JUEGO;
    public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA / (double) ALTO_JUEGO;

    public static int CENTRO_VENTANA_X = ANCHO_JUEGO / 2;
    public static int CENTRO_VENTANA_Y = ALTO_JUEGO / 2;

    public static String RUTA_MAPA = "/texto/prueba.ad";
    public static String RUTA_ICONO_RATON = "";
    public static String RUTA_PERSONAJE = "/imagenes/hojasTexturas/personaje.png";
}
