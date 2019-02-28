package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import clases.Usuarios;
import mysql.Consultas;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class viLogin extends JInternalFrame implements ActionListener, KeyListener {
	private JTextField txtUsuario;
	private JPasswordField txtPass;
	private JButton btnIngresar;
	private JLabel label;
	private JLabel lblBanner;
	private JLabel lblUser;
	private JLabel lblPass;


	vPrincipal vp = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viLogin frame = new viLogin(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public viLogin(vPrincipal temp) {
		vp = temp;
		
		setTitle("LOGIN");
		setBounds(100, 100, 612, 401);
		getContentPane().setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(this);
		txtUsuario.setForeground(Color.BLACK);
		txtUsuario.setFont(new Font("Berlin Sans FB Demi", Font.BOLD | Font.ITALIC, 22));
		txtUsuario.setColumns(10);
		txtUsuario.setBackground(SystemColor.menu);
		txtUsuario.setBounds(150, 175, 378, 41);
		getContentPane().add(txtUsuario);
		
		txtPass = new JPasswordField();
		txtPass.addKeyListener(this);
		txtPass.setForeground(Color.BLACK);
		txtPass.setFont(new Font("USAngel", Font.ITALIC, 18));
		txtPass.setBackground(SystemColor.menu);
		txtPass.setBounds(150, 244, 378, 41);
		getContentPane().add(txtPass);
		
		btnIngresar = new JButton("INGRESAR");
		btnIngresar.addActionListener(this);
		btnIngresar.setForeground(Color.BLACK);
		btnIngresar.setFont(new Font("USAngel", Font.BOLD, 18));
		btnIngresar.setBackground(Color.LIGHT_GRAY);
		btnIngresar.setBounds(150, 311, 378, 49);
		getContentPane().add(btnIngresar);
		
		lblBanner = new JLabel("");
		lblBanner.setHorizontalAlignment(SwingConstants.CENTER);
		Image img0 = new ImageIcon(this.getClass().getResource("/bannerbxb.png")).getImage();
		lblBanner.setIcon(new ImageIcon(img0));
		lblBanner.setBounds(0, 0, 600, 160);
		getContentPane().add(lblBanner);
		
		lblUser = new JLabel("");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		Image img2 = new ImageIcon(this.getClass().getResource("/user.jpg")).getImage();
		lblUser.setIcon(new ImageIcon(img2));
		lblUser.setBounds(86, 175, 41, 41);
		getContentPane().add(lblUser);
		
		lblPass = new JLabel("");
		lblPass.setHorizontalAlignment(SwingConstants.CENTER);
		Image img3 = new ImageIcon(this.getClass().getResource("/pass.jpg")).getImage();
		lblPass.setIcon(new ImageIcon(img3));
		lblPass.setBounds(86, 244, 41, 41);
		getContentPane().add(lblPass);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtUsuario, txtPass, btnIngresar}));

	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnIngresar) {
			actionPerformedBtnIngresar(arg0);
		}
	}
	
	public void keyPressed(KeyEvent arg0) {
	}
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {
		if (arg0.getSource() == txtPass) {
			keyTypedTxtPass(arg0);
		}
		if (arg0.getSource() == txtUsuario) {
			keyTypedTxtUsuario(arg0);
		}
	}
	protected void keyTypedTxtUsuario(KeyEvent e) {
		char c = e.getKeyChar();
		if (c == (char)KeyEvent.VK_ENTER){
			actionPerformedBtnIngresar(null);
		}		
	}
	protected void keyTypedTxtPass(KeyEvent e) {
		char c = e.getKeyChar();
		if (c == (char)KeyEvent.VK_ENTER){
			actionPerformedBtnIngresar(null);
		}
	}
	public void cursor(){// poner cursor en txtUsuario
		txtUsuario.requestFocus();
	}
	
	protected void actionPerformedBtnIngresar(ActionEvent arg0) {
		String usuIngre = txtUsuario.getText();
		String passIngre = String.valueOf(txtPass.getPassword());
		Consultas consulta = new Consultas();
		Usuarios ingresante = new Usuarios();
		ingresante.setUsuario(usuIngre);
		ingresante.setPassword(passIngre);
		ingresante.setNombre(null);
		ingresante.setTipo(-1); // no existe usuario
		ingresante = consulta.obtenerUsuario(ingresante);
		if(ingresante.getTipo() == -1)
			JOptionPane.showMessageDialog(null, "Usuario no econtrado o datos incorrectos");		
		else{
			JOptionPane.showMessageDialog(null, "Bienvenido: " + ingresante.getNombre());
			txtUsuario.setText(null);
			txtPass.setText(null);
			
			if(ingresante.getTipo() == 0){
				vp.esconderVentanas();
				vp.activarMenu(0);//admin
			}
			if(ingresante.getTipo() == 1){
				vp.esconderVentanas();
				vp.activarMenu(1);//personal
			}
		}	
	}
	
}