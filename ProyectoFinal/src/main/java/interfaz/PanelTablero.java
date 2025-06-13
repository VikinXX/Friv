package interfaz;

import modelo.Jugador;
import modelo.Tablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Daniel Rios Fraga
 * Clase que representa la interfaz gráfica del juego Cuatro en Raya.
 * Maneja la visualización del tablero, los controles y la interacción del usuario.
 * Utiliza un diseño con CardLayout para cambiar entre diferentes pantallas del juego.
 */
public class PanelTablero extends JFrame {
    private static final int FILAS_VISTA = 7; // 1 fila de botones y 6 filas de juego
    private static final int COLUMNAS = 7;

    private JButton[][] botones;
    private Tablero tablero;
    private Jugador jugador2;
    private Jugador jugadorActual;
    private CardLayout cardLayout;
    private JPanel panelPrincipal;
    private PanelJugador panelJugador;
    private PanelResultado panelResultado;
    private String usuario;
    private String j2;
    /**
     * Constructor principal que inicializa la ventana del juego.
     */
    public PanelTablero(String usuario) {
        super("Cuatro en raya");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        this.usuario = usuario;
        
        tablero = new Tablero(6, 7); 
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        inicializarPaneles();
        getContentPane().add(panelPrincipal);
        cardLayout.show(panelPrincipal, "modoJuego");
    }
    
    public PanelTablero(String usuario, String j2) {
        super("Cuatro en raya");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        this.usuario = usuario;
        this.j2 = j2;
        jugador2 = new Jugador(j2 != null ? j2 : "Jugador 2", Color.GREEN);
        
        tablero = new Tablero(6, 7); 
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        inicializarPanel();
        getContentPane().add(panelPrincipal);
        if(j2==null){
            cardLayout.show(panelPrincipal, "modoJuego");
        }
    }

    /**
     * Inicializa los diferentes paneles que componen la interfaz:
     */
    private void inicializarPaneles() {
    	JPanel panelTablero = crearPanelTablero();
        panelPrincipal.add(panelTablero, "tablero");
    	
        panelJugador = new PanelJugador(tablero, this, cardLayout, panelPrincipal, this.usuario);
        panelPrincipal.add(panelJugador, "modoJuego");

        panelResultado = new PanelResultado(tablero, this, cardLayout, panelPrincipal, this.usuario);
        panelPrincipal.add(panelResultado, "resultado");
    }
    
    /**
     * Inicializa los diferentes paneles que componen la interfaz, para los dos jugadores: 
     */
    private void inicializarPanel() {
        JPanel panelTablero = crearPanelTablero();
        panelPrincipal.add(panelTablero, "tablero");
        
    	panelJugador = new PanelJugador(tablero, this, cardLayout, panelPrincipal, this.usuario, this.j2);
        panelPrincipal.add(panelJugador, "modoJuego");
        
        panelResultado = new PanelResultado(tablero, this, cardLayout, panelPrincipal, this.usuario, this.j2);
        panelPrincipal.add(panelResultado, "resultado");
    }
    
    public void quitarTablero() {
    	this.setVisible(false);
    }

    /**
     * Crea y configura el panel que contiene el tablero de juego visual.
     * @return JPanel configurado con el diseño del tablero
     */
    private JPanel crearPanelTablero() {
        JPanel panel = new JPanel(new GridLayout(FILAS_VISTA, COLUMNAS));
        botones = new JButton[FILAS_VISTA][COLUMNAS];

        for (int i = 0; i < FILAS_VISTA; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                botones[i][j] = new JButton();
                if (i == 0) {
                    configurarBotonControl(botones[i][j], j);
                } else {
                    configurarCasillaJuego(botones[i][j]);
                }
                panel.add(botones[i][j]);
            }
        }
        return panel;
    }

    /**
     * Configura los botones de control (en la línea 0) para seleccionar columnas
     * @param boton Botón a configurar
     * @param columna Índice de la columna que representa el botón
     */
    private void configurarBotonControl(JButton boton, int columna) {
        boton.setBackground(new Color(23, 32, 110));
        boton.setText("Columna " + (columna + 1));
        boton.setForeground(Color.WHITE);
        boton.setActionCommand(String.valueOf(columna));
        boton.addActionListener(this::manejarClickColumna);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Configura las casillas de juego (visualización del tablero)
     * @param casilla Botón que representa una casilla del tablero
     */
    private void configurarCasillaJuego(JButton casilla) {
        casilla.setEnabled(false);
        casilla.setBackground(Color.WHITE);
        casilla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        casilla.setOpaque(true);
    }

    /**
     * Maneja el evento de clic en los botones de cada columna
     * @param e Evento de acción que contiene información sobre el botón presionado
     */
    private void manejarClickColumna(ActionEvent e) {
        int columna;
        
        try {
            columna = Integer.parseInt(e.getActionCommand());
        } catch (NumberFormatException ex) {
            return;
        }
        
        if (tablero.columnaLlena(columna)) {
            JOptionPane.showMessageDialog(this, "¡Columna llena! Elige otra.", 
                                        "Movimiento inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        jugadorActual = tablero.getJugadorActual();
        
        if (tablero.hacerMovimiento(columna)) {
            actualizarVista();
            
            if (tablero.hayGanador()) {
            	if(j2 == null) {
            		panelResultado.mostrarGanador(jugadorActual.getNombre());
                    cardLayout.show(panelPrincipal, "resultado");
                    return;
            	}else {
            		panelResultado.mostrarGanadores(jugadorActual.getNombre());
                    cardLayout.show(panelPrincipal, "resultado");
                    return;
            	}
                
            }
            
            if (tablero.hayEmpate()) {
                panelResultado.mostrarEmpate();
                cardLayout.show(panelPrincipal, "resultado");
                return;
            }
            
            tablero.cambiarTurno();
            jugadorActual = tablero.getJugadorActual();
            
            if (jugadorActual.getNombre().equals("BOT")) {
                new Timer(800, ev -> {
                    ((Timer)ev.getSource()).stop();
                    
                    tablero.hacerMovimientoBot();
                    actualizarVista();
                    
                    if (tablero.hayGanador()) {
                        panelResultado.mostrarGanador(jugadorActual.getNombre());
                        cardLayout.show(panelPrincipal, "resultado");
                    } 
                    else if (tablero.hayEmpate()) {
                        panelResultado.mostrarEmpate();
                        cardLayout.show(panelPrincipal, "resultado");
                    }
                    else {
                        tablero.cambiarTurno();
                        jugadorActual = tablero.getJugadorActual();
                    }
                }).start();
            }
        }
    }

    /**
     * Actualiza la vista del tablero según el estado actual del modelo
     */
    private void actualizarVista() {
        Color[][] estado = tablero.getCasillas();
        for (int filaVista = 1; filaVista < FILAS_VISTA; filaVista++) {
            for (int col = 0; col < COLUMNAS; col++) {
                botones[filaVista][col].setBackground(estado[filaVista-1][col]);
            }
        }
    }

    /**
     * Reinicia la vista del tablero a su estado inicial (todas las casillas blancas)
     */
    private void reiniciarVista() {
        for (int i = 1; i < FILAS_VISTA; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                botones[i][j].setBackground(Color.WHITE);
            }
        }
    }

    /**
     * Reinicia completamente el juego, incluyendo el modelo y la vista
     * Vuelve a mostrar el panel de selección de jugadores
     */
    public void reiniciarJuego() {
        tablero.reiniciar();
        reiniciarVista();
    }
    
    public Jugador getJugador() {
    	return jugador2;
    }
    
}