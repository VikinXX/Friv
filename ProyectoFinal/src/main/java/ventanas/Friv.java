package ventanas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaz.PanelTablero;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Alvaro Ortiz Villarejo
 */

public class Friv extends JFrame {

	private JPanel contentPane;
	private JButton btn4Raya;
	private JButton btnHundir;
	private String usuario;
	private JButton btnAtras;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Friv frame = new Friv("VikinXX");
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
	public Friv(String usuario) {
		crearGUI();
		eventos();
		setResizable(false);
		this.usuario = usuario;
	}
	
	/**
	 * Creates the event to enter where the user want.
	 */
	
	public void eventos() {
		btnAtras.addActionListener((e)->{
			SignInSignUp s = new SignInSignUp();
			s.setVisible(true);
			this.setVisible(false);
		});
		btn4Raya.addActionListener((e)->{
			PanelTablero pt = new PanelTablero(this.usuario);
			pt.setVisible(true);
			this.setVisible(false);
		});
		btnHundir.addActionListener((e)->{
			HundirLaFlota hf = new HundirLaFlota(this.usuario);
			hf.setVisible(true);
			this.setVisible(false);
		});
	}
	
	/**
	 * Creates the graphic interface
	 */
	
	public void crearGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 700);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFriv = new JLabel("Friv");
		lblFriv.setFont(new Font("Dialog", Font.BOLD, 20));
		lblFriv.setBounds(190, 27, 53, 24);
		contentPane.add(lblFriv);
		
//		BufferedImage raya4 = ImageIO.read(getClass().getResource("/image/4raya.png"));
//		BufferedImage hundir = ImageIO.read(getClass().getResource("/image/imagen_chat.png"));
		ImageIcon raya4 = new ImageIcon("images/4raya.png");
		ImageIcon hundir = new ImageIcon("images/imagen_chat.png");
		
		btn4Raya = new JButton(raya4);
		btn4Raya.setFont(new Font("Dialog", Font.BOLD, 15));
		btn4Raya.setBounds(12, 57, 416, 254);
		contentPane.add(btn4Raya);
		
		btnHundir = new JButton(hundir);
		btnHundir.setFont(new Font("Dialog", Font.BOLD, 15));
		btnHundir.setBounds(12, 323, 416, 254);
		contentPane.add(btnHundir);
		
//		BufferedImage flecha = ImageIO.read(getClass().getResource("/image/4raya.png"));
		ImageIcon flecha = new ImageIcon("images/flecha.png");
		
		btnAtras = new JButton(flecha);
		btnAtras.setBounds(12, 6, 44, 40);
		contentPane.add(btnAtras);
	}
}
