package ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hundirFlota.*;
import json.Reader;
import json.Writer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Canvas;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class HundirLaFlota extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton[][] tableroJuego;
	private JButton[][] tablero;
	private Comprobador yo = new Comprobador();
	private Comprobador ia = new Comprobador();
	private Tablero t = new Tablero();
	private JLabel resultado;
	private JLabel lblNewLabel_1;
	private JLabel resultadoIA;
	private JLabel lblBarcos;
	private JButton btnGirar;
	private JButton btnPerfect;
	private int [][] tab = new int[10][10];
	private Canvas [] canvas;
	private int iCanva = 0;
	private String actionCommand;
	private boolean vertical = true;
	private JButton btnAtras;
	private JButton btnGame;
	private JLabel lblPuntuacion;
	private int puntuacion;
	private JLabel lblPPartida;
	private JLabel lblPText;
	private JLabel lblExcepcion;
	private String usuario;
	private JLabel lblVictoria;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HundirLaFlota frame = new HundirLaFlota("VikinXX");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public HundirLaFlota(String usuario) {
		setTitle("Hundir la Flota");
		crearGUI();
		setResizable(false);
		eventos();
		quitarTableroJuego();
		this.usuario=usuario;
	}
	
	/*
	 * Creates the events
	 */

	public void eventos() {
		btnPerfect.addActionListener((e)->{
			String cad = actionCommand;
			int fila = Integer.parseInt(cad.substring(1, 2));
			int columna = Integer.parseInt(cad.substring(2));
			// Set the ships and continue asking the rest or directly proceed to the game.
			try {
				setTab(fila, columna);
				if (iCanva == 3) {
					vertical = true;
					if (Tablero.comprobar(tab)) {
						t.setTablero(tab);
						canvas[iCanva].setVisible(false);
						btnGirar.setVisible(false);
						btnPerfect.setVisible(false);
						lblBarcos.setText("");
						quitarTablero();
						ponerTableroJuego();
					}else {
						iCanva = 0;
						setTabVacio();
						canvas[3].setVisible(false);
						canvas[0].setVisible(true);
					}
				}else {
					vertical = true;
					canvas[iCanva].setVisible(false);
					iCanva++;
					canvas[iCanva].setVisible(true);
					lblBarcos.setText("Selecciona donde quieres poner el barco de "+(iCanva+1)+"x"+1);
				}
				lblExcepcion.setText("");
			}catch (ArrayIndexOutOfBoundsException excp) {
				lblExcepcion.setText("¡El barco no tiene espacio para navegar!");
				lblExcepcion.setForeground(Color.red);
			}
		});
		btnGirar.addActionListener((e)->{
			// Turn the ship
			if (vertical) {
				canvas[iCanva].setBounds(600, 551, canvas[iCanva].getHeight(), canvas[iCanva].getWidth());
				vertical = false;
			}else {
				canvas[iCanva].setBounds(600, 551, canvas[iCanva].getWidth(), canvas[iCanva].getHeight());
				vertical = true;
			}
		});
		btnAtras.addActionListener((e)->{
			// Go back to the friv frame.
			Friv friv = new Friv(this.usuario);
			friv.setVisible(true);
			this.setVisible(false);
		});
		btnGame.addActionListener((e)->{
			// Rematch.
			HundirLaFlota v = new HundirLaFlota(this.usuario);
			v.setVisible(true);
			this.setVisible(false);
		});
	}

	/**
	 * Creates the frame
	 */
	
	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 900);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		resultado = new JLabel("");
		resultado.setBounds(300, 622, 150, 15);
		contentPane.add(resultado);

		JLabel lblNewLabel = new JLabel("Tablero enemigo");
		lblNewLabel.setBounds(225, 11, 150, 23);
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Tú tablero");
		lblNewLabel_1.setBounds(1150, 15, 126, 14);
		contentPane.add(lblNewLabel_1);

		resultadoIA = new JLabel("");
		resultadoIA.setBounds(1150, 622, 109, 14);
		contentPane.add(resultadoIA);

		lblBarcos = new JLabel("Selecciona donde quieres poner el barco de 1x1");
		lblBarcos.setBounds(583, 697, 385, 14);
		contentPane.add(lblBarcos);

		btnGirar = new JButton("Girar");
		btnGirar.setBounds(600, 722, 120, 23);
		contentPane.add(btnGirar);

		btnPerfect = new JButton("Perfecto!");
		btnPerfect.setBounds(826, 722, 120, 23);
		contentPane.add(btnPerfect);

		// Array to create the ships with different sizes.
		canvas = new Canvas[4];

		canvas[0] = new Canvas();
		canvas[0].setBackground(new Color(204, 0, 0));
		canvas[0].setBounds(600, 450, 50, 50);
		contentPane.add(canvas[0]);

		canvas[1] = new Canvas();
		canvas[1].setBackground(new Color(204, 0, 0));
		canvas[1].setBounds(600, 450, 50, 100);
		contentPane.add(canvas[1]);
		canvas[1].setVisible(false);

		canvas[2] = new Canvas();
		canvas[2].setBackground(new Color(204, 0, 0));
		canvas[2].setBounds(600, 450, 50, 150);
		contentPane.add(canvas[2]);
		canvas[2].setVisible(false);

		canvas[3] = new Canvas();
		canvas[3].setBackground(new Color(204, 0, 0));
		canvas[3].setBounds(600, 450, 50, 200);
		contentPane.add(canvas[3]);
		canvas[3].setVisible(false);

		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(600, 787, 120, 23);
		contentPane.add(btnAtras);
		btnAtras.setVisible(false);

		btnGame = new JButton("Otra vez!");
		btnGame.setBounds(826, 787, 120, 23);
		contentPane.add(btnGame);
		btnGame.setVisible(false);

		lblPuntuacion = new JLabel("");
		lblPuntuacion.setBounds(600, 622, 315, 163);
		contentPane.add(lblPuntuacion);
		lblPuntuacion.setVisible(false);

		lblPText = new JLabel("Puntuación");
		lblPText.setFont(new Font("Dialog", Font.BOLD, 17));
		lblPText.setBounds(698, 148, 109, 15);
		contentPane.add(lblPText);
		lblPText.setVisible(false);

		lblPPartida = new JLabel("");
		lblPPartida.setFont(new Font("Dialog", Font.BOLD, 15));
		lblPPartida.setBounds(698, 198, 109, 23);
		contentPane.add(lblPPartida);

		lblExcepcion = new JLabel("");
		lblExcepcion.setBounds(1038, 681, 357, 30);
		contentPane.add(lblExcepcion);
		
		lblVictoria = new JLabel("");
		lblVictoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblVictoria.setFont(new Font("Dialog", Font.BOLD, 20));
		lblVictoria.setBounds(635, 50, 232, 86);
		contentPane.add(lblVictoria);
		lblPPartida.setVisible(true);

		// Makes both boards. Player's board and bots's board. 
		this.tableroJuego = new JButton[10][10];
		this.tablero = new JButton[10][10];
		int posXJ = 55;
		int posYJ = 45;
		int posX = 900;
		int posY = 45;
		for (int i = 0; i < tableroJuego.length; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				tableroJuego[i][j] = new JButton("");
				tablero[i][j] = new JButton("");
				tableroJuego[i][j].setBounds(posXJ, posYJ, 50, 50);
				tablero[i][j].setBounds(posX, posY, 50, 50);
				tableroJuego[i][j].setActionCommand(i+""+j);
				tablero[i][j].setActionCommand("T"+i+""+j);
				tableroJuego[i][j].addActionListener(this);
				tablero[i][j].addActionListener(this);
				posXJ += 52;
				posX += 52;
				contentPane.add(tableroJuego[i][j]);
				contentPane.add(tablero[i][j]);
			}
			posXJ = 55;
			posX = 900;
			posYJ += 52;
			posY += 52;
		}

	}
	
	/**
	 * Set the enemy's board to be clickable
	 */

	public void ponerTablero() {
		for (int i = 0; i < tableroJuego.length; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				tablero[i][j].setEnabled(true);
			}
		}
	}
	
	/**
	 * Set the enemy's board to be UNclickable
	 */

	public void quitarTablero() {
		for (int i = 0; i < tableroJuego.length; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				tablero[i][j].setEnabled(false);
			}
		}
	}
	
	/**
	 * Set the user's board to be clickable
	 */

	public void ponerTableroJuego() {
		for (int i = 0; i < tableroJuego.length; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				tableroJuego[i][j].setEnabled(true);
			}
		}
	}
	
	/**
	 * Set the user's board to be UNclickable
	 */

	public void quitarTableroJuego() {
		for (int i = 0; i < tableroJuego.length; i++) {
			for (int j = 0; j < tableroJuego[i].length; j++) {
				tableroJuego[i][j].setEnabled(false);
			}
		}
	}
	
	/**
	 * Set's the board into a matrix. To check it later sending it to the Tablero Class.
	 * @param f, row
	 * @param c, column
	 */

	public void setTab(int f, int c) {

		if (vertical) {
			for (int i = 0, cont = f; i <= iCanva; i++, cont++) {
				tab[cont][c] = iCanva + 1;
			}
		}else {
			for (int i = 0, cont = c; i <= iCanva; i++, cont++) {
				tab[f][cont] = iCanva + 1;
			}
		}
	}
	
	/**
	 * Reset the player's board
	 */

	public void setTabVacio() {
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				tab[i][j] = 0;
				tablero[i][j].setBackground(null);
			}
		}
	}
	
	/**
	 * Paints the ships in the board. IT DOES NOT SET IT.
	 * @param f, row
	 * @param c, column
	 * @throws ArrayIndexOutOfBoundsException
	 */

	public void colorear(int f, int c) throws ArrayIndexOutOfBoundsException{

		if (vertical) {
			for (int i = 0, cont = f; i <= iCanva; i++, cont++) {
				tablero[cont][c].setBackground(Color.RED);
			}
		}else {
			for (int i = 0, cont = c; i <= iCanva; i++, cont++) {
				tablero[f][cont].setBackground(Color.RED);
			}
		}
	}
	
	/**
	 * Controls exceptions of getting out the matrix
	 * @param f
	 * @param c
	 * @return True, if does not send any exception. False, if does send a exception.
	 * @throws ArrayIndexOutOfBoundsException
	 */
	
	public boolean comprobar(int f, int c) throws ArrayIndexOutOfBoundsException{

		if (vertical) {
			if(f+(iCanva) > 9) {
				return false;
			}
			return true;
		}else {
			if (c+(iCanva) > 9) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * Listener that is continuously hearing on both boards
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Checks if the user press his board or the enemy's board
		if (e.getActionCommand().substring(0, 1).equals("T")) {
			// If the user press his board, is for preparing it to the game.
			for (int i = 0; i < tab.length; i++) {
				for (int j = 0; j < tab[i].length; j++) {
					if (tab[i][j] == 0) {
						tablero[i][j].setBackground(null);
					}
				}
			}
			actionCommand=e.getActionCommand();
			int fila = Integer.parseInt(actionCommand.substring(1, 2));
			int columna = Integer.parseInt(actionCommand.substring(2));
			/**
			 * Calls comprobar to control if the user is not bearing in mind the OUTOFBOUNDS exception
			 */
			if(comprobar(fila, columna)) {
				colorear(fila, columna);
				lblExcepcion.setText("");
			}else {
				lblExcepcion.setText("¡El barco no tiene espacio para navegar!");
				lblExcepcion.setForeground(Color.red);
			}
		}else {
			// Comes here if the user press the enemy's board
			int fila=Integer.parseInt(e.getActionCommand().substring(0, 1));
			int columna=Integer.parseInt(e.getActionCommand().substring(1));
			// Call Comprobador class and check what the bomb did.
			int result=yo.comprobado(fila, columna, t.getTableroEnemigo());
			if (result == 0) {
				// Set the box on X if it is water.
				tableroJuego[fila][columna].setText("X");
				resultado.setText("Agua");
				puntuacion -= 15;
				tableroJuego[fila][columna].setBackground(Color.cyan);
			} else if (result == 1) { 
				// Set the box on O if it hit a ship.
				tableroJuego[fila][columna].setText("O");
				resultado.setText("Tocado");
				tableroJuego[fila][columna].setBackground(Color.GREEN);
				puntuacion += 100;
			} else if(result == 2){
				// Set O and send the message that the ship is destroyed
				tableroJuego[fila][columna].setText("O");
				resultado.setText("Tocado y hundido");
				tableroJuego[fila][columna].setBackground(Color.GREEN);
				puntuacion += 100;
			}else {
				// Say that the place is repeated
				resultado.setText("Lugar repetido");
			}
			// Check if the place is repeated to see if the bot should place a bomb
			if (result != -1) {
				int resultIA = ia.bombaIA(t.getTablero());
				int filaIA=ia.getFila();
				int columnaIA=ia.getColum();

				if(resultIA == 0) {
					// Set the box on X if it is water.
					tablero[filaIA][columnaIA].setText("X");
					resultadoIA.setText("Agua");
				}else if(resultIA == 1) {
					// Set the box on O if it hit a ship.
					tablero[filaIA][columnaIA].setText("O");
					resultadoIA.setText("Tocado");
				}else {
					// Set O and send the message that the ship is destroyed
					tablero[filaIA][columnaIA].setText("O");
					resultadoIA.setText("Tocado y hundido");
				}
				// Check if the game is finished, 
				if (ia.Victoria() || yo.Victoria()) {
					if (yo.Victoria()) {
						lblVictoria.setText("¡Has ganado!");
						lblVictoria.setForeground(Color.green);
					}else {
						lblVictoria.setText("Has perdido");
						lblVictoria.setForeground(Color.red);
					}
					btnAtras.setVisible(true);
					btnGame.setVisible(true);
					lblPuntuacion.setVisible(true);
					lblPText.setVisible(true);
					lblPPartida.setVisible(true);
					lblPPartida.setText(puntuacion + " pts.");
					Writer.escribirPuntuacion(this.usuario, puntuacion, "Hundir la Flota");
					lblPuntuacion.setText(Reader.lectorUsuarioPuntuacion("Hundir la Flota", this.usuario));
					quitarTableroJuego();

				}
			}

		} 
	}
}