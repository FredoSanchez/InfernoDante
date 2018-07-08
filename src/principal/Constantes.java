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
    
    public static int CENTRO_VENTANA_X = ANCHO_JUEGO/2;
    public static int CENTRO_VENTANA_Y = ALTO_JUEGO/2;
    
    public static int ANCHO_PANTALLA_COMPLETA = 1366;
    public static int ALTO_PANTALLA_COMPLETA = 768;
    
    public static double FACTOR_ESCALADO_X = ANCHO_PANTALLA_COMPLETA / ANCHO_JUEGO;
    public static double FACTOR_ESCALADO_Y = ALTO_PANTALLA_COMPLETA / ALTO_JUEGO ;
  
    
    public static String RUTA_MAPA = "/texto/prueba.ad";
    public static String RUTA_PERSONAJE = "/imagenes/hojasTexturas/personaje.png";
}
