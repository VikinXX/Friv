package modelo;

import java.awt.Color;

/**
 * @author Daniel Rios Fraga
 * Representa al jugador con un nombre y un color
 * Contiene un nombre y un color
 */
public class Jugador {
    private String nombre;
    private Color color;
    

    /**
     * Crea un jugador con un nombre y un color
     * 
     * @param nombre nombre asignado al jugador
     * @param color color asignado al jugador
     */
    public Jugador(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
    }
    
    /**
     * Devuelve el nombre del jugador
     * 
     * @return nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Devuelve el color del jugador
     * 
     * @return color del jugador
     */
    public Color getColor() {
        return color;
    }
}
