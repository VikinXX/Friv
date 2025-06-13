package interfaz;

import modelo.Jugador;
import modelo.Tablero;
import ventanas.SignInSignUp;

import javax.swing.*;
import java.awt.*;

/**
 * @author Daniel Rios Fraga
 * Panel para seleccionar el modo de juego entres dos jugadores o un jugador y un bot programado
 */
public class PanelJugador extends JPanel {
    private static final long serialVersionUID = 1L;

    private JButton btnUnJugador;
    private JButton btnDosJugadores;
    private String j2 = null;

    /**
     * Constructor que crea el panel de selección de jugadores.
     * @param tablero Referencia al modelo del tablero de juego
     * @param panelTablero Referencia al panel principal del juego
     * @param layout CardLayout para manejar la navegación entre paneles
     * @param mainPanel Panel contenedor principal
     */
    public PanelJugador(Tablero tablero, PanelTablero panelTablero, CardLayout layout, JPanel mainPanel, String usuario) {
        setLayout(new GridLayout(2, 1));
        btnUnJugador = new JButton("Un Jugador");
        btnDosJugadores = new JButton("Dos Jugadores");
        
        add(btnUnJugador);
        add(btnDosJugadores);

        /**
         * Configura el evento para modo un jugador
         */
        btnUnJugador.addActionListener(e -> {
            Jugador jugador1 = new Jugador(usuario != null ? usuario : "Jugador 1", Color.RED);
            Jugador jugador2 = new Jugador("BOT", Color.GREEN);
            tablero.reiniciar();
            panelTablero.reiniciarJuego();
            tablero.setJugadores(jugador1, jugador2);
            layout.show(mainPanel, "tablero");
        });

        /**
         * Configura el evento para modo dos jugadores
         */
        btnDosJugadores.addActionListener(e -> {
        	
        	if(this.j2 == null) {
        		SignInSignUp sg = new SignInSignUp(usuario);
        		panelTablero.quitarTablero();
        	}else {
        		Jugador jugador1 = new Jugador(usuario != null ? usuario : "Jugador 1", Color.RED);
                tablero.reiniciar();
                panelTablero.reiniciarJuego();
                tablero.setJugadores(jugador1, panelTablero.getJugador());
                layout.show(mainPanel, "tablero");
        	}
        });
    }
    
    public PanelJugador(Tablero tablero, PanelTablero panelTablero, CardLayout layout, JPanel mainPanel, String usuario, String j2) {
        setLayout(new GridLayout(2, 1));
        btnUnJugador = new JButton("Un Jugador");
        btnDosJugadores = new JButton("Dos Jugadores");
        this.j2 = j2;
        Jugador jugador1 = new Jugador(usuario != null ? usuario : "Jugador 1", Color.RED);
        Jugador jugador2 = new Jugador(j2 != null ? j2 : "Jugador 2", Color.GREEN);
        tablero.setJugadores(jugador1, jugador2);
        panelTablero.reiniciarJuego();
        
        add(btnUnJugador);
        add(btnDosJugadores);

        /**
         * Configura el evento para modo un jugador
         */
        btnUnJugador.addActionListener(e -> {
        	Jugador jugador11 = new Jugador(usuario != null ? usuario : "Jugador 1", Color.RED);
        	Jugador jugador22 = new Jugador("BOT", Color.GREEN);
            tablero.reiniciar();
            panelTablero.reiniciarJuego();
            tablero.setJugadores(jugador11, jugador22);
            layout.show(mainPanel, "tablero");
        });

        /**
         * Configura el evento para modo dos jugadores
         */
        btnDosJugadores.addActionListener(e -> {
        	if(this.j2 == null) {
        		SignInSignUp sg = new SignInSignUp(usuario);
        		panelTablero.setVisible(false);
        	}else {
        		Jugador jugador11 = new Jugador(usuario != null ? usuario : "Jugador 1", Color.RED);
                Jugador jugador22 = new Jugador(j2 != null ? j2 : "Jugador 2", Color.GREEN);
                tablero.reiniciar();
                panelTablero.reiniciarJuego();
                tablero.setJugadores(jugador11, jugador22);
                layout.show(mainPanel, "tablero");
        	}
        });
    }
}