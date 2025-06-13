package ventanas;

import java.awt.Color;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaz.PanelTablero;
import json.Reader;
import json.Writer;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class VentanaCCuenta extends JFrame {
	
	/**
	 * @author Alvaro Ortiz Villarejo
	 */

	private JPanel contentPane;
	private JTextField usuario;
	private JButton btnAtrs;
	private JButton btnSubmit;
	private JPasswordField password;
	private JLabel incorrect;
	private String user;
	private SignInSignUp s;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					VentanaCCuenta frame = new VentanaCCuenta();
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
	public VentanaCCuenta() {
		crearGUI();
		eventos();
		setResizable(false);
	}
	public VentanaCCuenta(String user) {
		crearGUI();
		eventos();
		setResizable(false);
		this.user = user;
	}
	
	public VentanaCCuenta(SignInSignUp s) {
		crearGUI();
		eventos();
		setResizable(false);
		this.s = s;
	}
	
	/**
	 * Create the event to go back or to Sign Up
	 */
	
	public void eventos() {
		btnAtrs.addActionListener((e)->{
			SignInSignUp s = new SignInSignUp();
			s.setVisible(true);
			this.setVisible(false);
		});
		btnSubmit.addActionListener((e)->{
			if (this.s == null) {
				if (Reader.lectorUsuarioCrearCuenta(usuario.getText())) {
					incorrect.setForeground(Color.RED);
					incorrect.setText("El nombre de usuario ya existe");
				}else {
					if(usuario.getText().isBlank() || password.getText().isBlank()) {
						incorrect.setForeground(Color.RED);
						incorrect.setText("Tienes que introducir un usuario y contraseña");
					}else if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,}$", password.getText())) {
						incorrect.setForeground(Color.RED);
						incorrect.setText("<html>La contraseña tiene que contener al menos 5 caracteres,<br> una mayúscula, una minúscula y un número.</html>");
					}else{
						String cifrado = Base64.getEncoder().encodeToString(password.getText().getBytes());
						Writer.crearCuentaUsuario(usuario.getText(), cifrado);
						Friv f = new Friv(usuario.getText());
						this.setVisible(false);
						f.setVisible(true);
					}
				}
			}else {
				if (Reader.lectorUsuarioCrearCuenta(usuario.getText())) {
					incorrect.setForeground(Color.RED);
					incorrect.setText("El nombre de usuario ya existe");
				}else {
					if(usuario.getText().isBlank() || password.getText().isBlank()) {
						incorrect.setForeground(Color.RED);
						incorrect.setText("Tienes que introducir un usuario y contraseña");
					}else if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{5,}$", password.getText())) {
						incorrect.setForeground(Color.RED);
						incorrect.setText("La contraseña tiene que contener al menos 5 caracteres, una mayúscula, una minúscula y un número.");
					}else {
						String cifrado = Base64.getEncoder().encodeToString(password.getText().getBytes());
						Writer.crearCuentaUsuario(usuario.getText(), cifrado);
						PanelTablero pt = new PanelTablero(s.getUsuario(), usuario.getText());
						this.setVisible(false);
						pt.setVisible(true);
					}
				}
			}
		});
	}
	
	/**
	 * Creates the graphic interface.
	 */
	
	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNuevoUsuario = new JLabel("Nuevo Usuario");
		lblNuevoUsuario.setBounds(166, 12, 126, 15);
		contentPane.add(lblNuevoUsuario);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(32, 74, 126, 15);
		contentPane.add(lblNombre);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setBounds(32, 151, 126, 15);
		contentPane.add(lblContrasea);
		
		usuario = new JTextField();
		usuario.setBounds(259, 72, 114, 19);
		contentPane.add(usuario);
		usuario.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(239, 219, 117, 25);
		contentPane.add(btnSubmit);
		
		btnAtrs = new JButton("Atrás");
		btnAtrs.setBounds(78, 219, 117, 25);
		contentPane.add(btnAtrs);
		
		password = new JPasswordField();
		password.setBounds(259, 149, 114, 19);
		contentPane.add(password);
		
		incorrect = new JLabel("");
		incorrect.setBounds(55, 177, 346, 31);
		contentPane.add(incorrect);
	}

}
