package ventanas;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaz.PanelTablero;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.util.Base64;
import json.*;
import javax.swing.JPasswordField;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class VentanaInicioSesion extends JFrame {

	private JPanel contentPane;
	private JTextField usuario;
	private JButton btnSubmit;
	private JButton btnAtras;
	private JPasswordField password;
	private JLabel incorrect;
	private String user = null;
	private SignInSignUp s;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaInicioSesion frame = new VentanaInicioSesion();
//					frame.setVisible(true);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VentanaInicioSesion() {
		setTitle("Inicio Sesión");
		crearGUI();
		eventos();
		setResizable(false);
	}
	
	
	
	public VentanaInicioSesion(SignInSignUp s) {
		setTitle("Inicio Sesión");
		crearGUI();
		eventos();
		setResizable(false);
		this.s = s;
	}
	
	/**
	 * Create the event to go back or to Sign In
	 */
	
	public void eventos() {
		btnAtras.addActionListener((e)->{
			SignInSignUp s = new SignInSignUp();
			s.setVisible(true);
			this.setVisible(false);
		});
		btnSubmit.addActionListener((e)->{
			if (this.s == null) {
				String cifrado = Base64.getEncoder().encodeToString(password.getText().getBytes());
				if (Reader.lectorUsuarios(usuario.getText(), cifrado)) {
					Friv f = new Friv(usuario.getText());
					this.setVisible(false);
					f.setVisible(true);
				}else {
					incorrect.setForeground(Color.RED);
					incorrect.setText("Usuario o contraseña incorrectos.");
				}
			}else {
				String cifrado = Base64.getEncoder().encodeToString(password.getText().getBytes());
				if (Reader.lectorUsuarios(usuario.getText(), cifrado)) {
					PanelTablero pt = new PanelTablero(s.getUsuario(), usuario.getText());
					this.setVisible(false);
					pt.setVisible(true);
				}else {
					incorrect.setForeground(Color.RED);
					incorrect.setText("Usuario o contraseña incorrectos.");
				}
			}
			
		});
	}
	
	/**
	 * Creates the graphic interface
	 */
	
	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInicioDeSesin = new JLabel("Inicio de sesión");
		lblInicioDeSesin.setBounds(161, 30, 115, 15);
		contentPane.add(lblInicioDeSesin);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario:");
		lblNombreDeUsuario.setBounds(24, 70, 140, 15);
		contentPane.add(lblNombreDeUsuario);
		
		usuario = new JTextField();
		usuario.setBounds(255, 68, 114, 19);
		contentPane.add(usuario);
		usuario.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(24, 143, 105, 15);
		contentPane.add(lblContrasea);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(229, 218, 117, 25);
		contentPane.add(btnSubmit);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(81, 218, 117, 25);
		contentPane.add(btnAtras);
		
		password = new JPasswordField();
		password.setBounds(255, 141, 114, 19);
		contentPane.add(password);
		
		incorrect = new JLabel("");
		incorrect.setBounds(91, 184, 255, 15);
		contentPane.add(incorrect);
	}
}
