package interfaz;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import json.Reader;
import json.Writer;
import modelo.Tablero;
import ventanas.Friv;

/**
 * @author Daniel Rios Fraga
 * Panel que muestra los resultados del juego (si hay ganador o si hay empate).
 * Contiene dos botones, uno para reiniciar el juego y otro para salir de la
 * aplicación.
 */
public class PanelResultado extends JPanel {
	private JLabel lblMensaje;
	private JButton btnReiniciar;
	private JButton btnSalir;
	private Tablero tablero;
	private PanelTablero panelTablero;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private String usua;
	private String j2;

	/**
	 * Constructor que inicializa el panel de resultados.
	 * 
	 * @param tablero      Referencia al modelo del tablero de juego
	 * @param panelTablero Referencia al panel principal del tablero
	 * @param cardLayout   Layout manager para cambiar entre paneles
	 * @param mainPanel    Panel principal que contiene todos los subpaneles
	 */

	public PanelResultado(Tablero tablero, PanelTablero panelTablero, CardLayout cardLayout, JPanel mainPanel,
			String usu) {
		this.tablero = tablero;
		this.panelTablero = panelTablero;
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.usua = usu;
		
		configInterfaz();
		configEventos();
	}

	public PanelResultado(Tablero tablero, PanelTablero panelTablero, CardLayout cardLayout, JPanel mainPanel,
			String usu, String j2) {
		this.tablero = tablero;
		this.panelTablero = panelTablero;
		this.cardLayout = cardLayout;
		this.mainPanel = mainPanel;
		this.usua = usu;
		this.j2 = j2;
		
		configInterfaz();
		configEventos();
	}

	/**
	 * Configura los componentes visuales del panel.
	 */
	private void configInterfaz() {
		setLayout(new BorderLayout());

		lblMensaje = new JLabel("", JLabel.CENTER);
		lblMensaje.setFont(lblMensaje.getFont().deriveFont(20f));
		add(lblMensaje, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		btnReiniciar = new JButton("Jugar de nuevo");
		btnSalir = new JButton("Salir");

		panelBotones.add(btnReiniciar);
		panelBotones.add(btnSalir);
		add(panelBotones, BorderLayout.SOUTH);
	}

	/**
	 * Configura los eventos de los botones.
	 */
	private void configEventos() {
		btnReiniciar.addActionListener(e -> {
			reiniciarJuego();
			cardLayout.show(mainPanel, "tablero");
		});
		btnSalir.addActionListener(e -> {
			Friv f = new Friv(usua);
			f.setVisible(true);
			this.panelTablero.quitarTablero();
		});
	}

	/**
	 * Maneja la acción de reinicio del juego.
	 */
	private void reiniciarJuego() {
		tablero.reiniciar();
		panelTablero.reiniciarJuego();
	}

	/**
	 * Muestra el mensaje de ganador en el panel.
	 * 
	 * @param mensaje Nombre del jugador ganador
	 */
	public void mostrarGanadores(String mensaje) {
		if (mensaje.equals(usua)) {
			Writer.ganador4Raya(mensaje, "4 en raya", usua, j2);
			lblMensaje.setText("<html>El ganador es: " + mensaje + "\n" + Reader.lectorVictorias(usua, "4 en raya")
					+ "<br>El perdedor es: " + j2 + Reader.lectorVictorias(j2, "4 en raya") + "</html>");
		} else {
			Writer.ganador4Raya(j2, "4 en raya", j2, usua);
			lblMensaje.setText("<html>El ganador es: " + j2 + Reader.lectorVictorias(j2, "4 en raya") + "<br>El perdedor es: "
					+ usua + Reader.lectorVictorias(usua, "4 en raya") + "</html>");
		}
	}

	public void mostrarGanador(String mensaje) {
		if (mensaje.equals("BOT")) {
			Writer.ganador4RayaJ1(mensaje, "4 en raya", false);
			lblMensaje.setText("<html>Has perdido: " + usua + "\n" + Reader.lectorVictorias(usua, "4 en raya") + "</html>");
		}else {
			Writer.ganador4RayaJ1(mensaje, "4 en raya", true);
			lblMensaje.setText("<html>El ganador es: " + usua + "\n" + Reader.lectorVictorias(usua, "4 en raya") + "</html>");
		}
	}

	/**
	 * Muestra el mensaje de empate en el panel.
	 */
	public void mostrarEmpate() {
		lblMensaje.setText("¡Empate!");
	}
}