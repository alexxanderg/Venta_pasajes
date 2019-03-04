package guiSecundarios;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import clases.Conductor;
import gui.vPrincipal;
import gui.viVehiculos;
import mysql.Consultas;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class vdVehiculoNuevo extends JDialog implements ActionListener {
	private JTextField txtAgregarVehiculo;
	private JTextField txtPlaca;
	private JTextField txtDetalles;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JLabel lblPlaca;
	private JComboBox cbModelo;
	public JComboBox <Conductor> cbConductor;
	private JButton btnNewConductor;
	private JLabel lblDniConductor;
	private JTextField txtDni;
	
	vPrincipal vp = null;
	viVehiculos vnvh = null;
	ResultSet rs;
	
	public static void main(String[] args) {
		try {
			vdVehiculoNuevo dialog = new vdVehiculoNuevo(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public vdVehiculoNuevo(vPrincipal temp, viVehiculos temp2) {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		vp = temp;
		vnvh = temp2;
		
		setUndecorated(true);
		setBounds(100, 100, 559, 325);
		getContentPane().setLayout(null);
		{
			txtAgregarVehiculo = new JTextField();
			txtAgregarVehiculo.setEditable(false);
			txtAgregarVehiculo.setText("AGREGAR VEHICULO");
			txtAgregarVehiculo.setHorizontalAlignment(SwingConstants.CENTER);
			txtAgregarVehiculo.setForeground(Color.WHITE);
			txtAgregarVehiculo.setFont(new Font("USAngel", Font.PLAIN, 20));
			txtAgregarVehiculo.setColumns(10);
			txtAgregarVehiculo.setBackground(new Color(30, 144, 255));
			txtAgregarVehiculo.setBounds(0, 0, 559, 46);
			getContentPane().add(txtAgregarVehiculo);
		}
		{
			lblPlaca = new JLabel("Placa:");
			lblPlaca.setHorizontalAlignment(SwingConstants.LEFT);
			lblPlaca.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
			lblPlaca.setBounds(30, 71, 132, 20);
			getContentPane().add(lblPlaca);
		}
		{
			txtPlaca = new JTextField();
			txtPlaca.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
			txtPlaca.setColumns(10);
			txtPlaca.setBounds(188, 69, 335, 25);
			getContentPane().add(txtPlaca);
		}
		{
			JLabel lblModelo = new JLabel("Modelo:");
			lblModelo.setHorizontalAlignment(SwingConstants.LEFT);
			lblModelo.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
			lblModelo.setBounds(30, 114, 132, 20);
			getContentPane().add(lblModelo);
		}
		{
			cbModelo = new JComboBox();
			cbModelo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
			cbModelo.setBounds(188, 109, 335, 25);
			getContentPane().add(cbModelo);
		}
		{
			JLabel lblConductor = new JLabel("Conductor:");
			lblConductor.setHorizontalAlignment(SwingConstants.LEFT);
			lblConductor.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
			lblConductor.setBounds(30, 221, 132, 20);
			getContentPane().add(lblConductor);
		}
		{
			cbConductor = new JComboBox();
			cbConductor.addActionListener(this);
			cbConductor.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
			cbConductor.setBounds(188, 220, 273, 23);
			getContentPane().add(cbConductor);
		}
		{
			btnNewConductor = new JButton("+");
			btnNewConductor.addActionListener(this);
			btnNewConductor.setForeground(Color.WHITE);
			btnNewConductor.setFont(new Font("Segoe UI", Font.BOLD, 15));
			btnNewConductor.setBackground(new Color(50, 205, 50));
			btnNewConductor.setBounds(471, 220, 52, 23);
			getContentPane().add(btnNewConductor);
		}
		{
			txtDetalles = new JTextField();
			txtDetalles.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
			txtDetalles.setColumns(10);
			txtDetalles.setBounds(188, 150, 335, 25);
			getContentPane().add(txtDetalles);
		}
		{
			JLabel lblDetalles = new JLabel("Detalles:");
			lblDetalles.setHorizontalAlignment(SwingConstants.LEFT);
			lblDetalles.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
			lblDetalles.setBounds(30, 155, 132, 20);
			getContentPane().add(lblDetalles);
		}
		{
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(this);
			btnCancelar.setForeground(Color.WHITE);
			btnCancelar.setFont(new Font("USAngel", Font.PLAIN, 20));
			btnCancelar.setBackground(Color.DARK_GRAY);
			btnCancelar.setBounds(30, 258, 220, 53);
			getContentPane().add(btnCancelar);
		}
		{
			btnGuardar = new JButton("<html>Guardar</html>");
			btnGuardar.addActionListener(this);
			btnGuardar.setForeground(Color.WHITE);
			btnGuardar.setFont(new Font("USAngel", Font.PLAIN, 20));
			btnGuardar.setBackground(Color.DARK_GRAY);
			btnGuardar.setBounds(303, 261, 220, 53);
			getContentPane().add(btnGuardar);
		}
		{
			lblDniConductor = new JLabel("DNI Conductor:");
			lblDniConductor.setHorizontalAlignment(SwingConstants.LEFT);
			lblDniConductor.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
			lblDniConductor.setBounds(30, 191, 220, 20);
			getContentPane().add(lblDniConductor);
		}
		{
			txtDni = new JTextField();
			txtDni.setEditable(false);
			txtDni.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
			txtDni.setColumns(10);
			txtDni.setBounds(244, 186, 279, 25);
			getContentPane().add(txtDni);
		}
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPlaca, cbModelo, txtDetalles, cbConductor, btnNewConductor, btnCancelar, btnGuardar}));
		cargar();
	}
	
	
	public void cargar(){
		setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		
		Consultas consult = new Consultas();
		rs = consult.cargarModelosVehiculos();
		try {
			while(rs.next())
				cbModelo.addItem(rs.getString("modelo"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		
		Conductor conductor = new Conductor();
		conductor.cargarConductores(cbConductor);
	}
	

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewConductor) {
			actionPerformedBtnNewConductor(e);
		}
		if (e.getSource() == cbConductor) {
			actionPerformedCbConductor(e);
		}
		if (e.getSource() == btnCancelar) {
			actionPerformedBtnCancelar(e);
		}
		if (e.getSource() == btnGuardar) {
			actionPerformedBtnGuardar(e);
		}
	}
	protected void actionPerformedBtnCancelar(ActionEvent e) {
		vp.enable(true);
		this.dispose();
	}
	protected void actionPerformedBtnGuardar(ActionEvent e) {
		try {
			String placa = txtPlaca.getText();
			int modelo = cbModelo.getSelectedIndex()+1;
			String detalle = txtDetalles.getText();
			int dni = Integer.parseInt(txtDni.getText());
			if(txtPlaca.getText().length() == 0){
				this.setAlwaysOnTop(false);
				JOptionPane.showMessageDialog(null, "Por favor, coloque el n�mero de placa");
				txtPlaca.requestFocus();
				this.setAlwaysOnTop(true);
			}
			else{
				Consultas.crearVehiculo(placa, modelo, detalle, dni);
				this.setAlwaysOnTop(false);
				vnvh.cargar();
				selecionarVehiculo();
				JOptionPane.showMessageDialog(null, "Creado correctamente");
				vp.enable(true);
				this.dispose();
			}
		}
		catch(Exception ex){
		}
	}
	
	public void selecionarVehiculo(){
		String placa = txtPlaca.getText();
		int cantVehiculos = vnvh.tbVehiculos.getRowCount();
		for(int i = 0; i<cantVehiculos; i++){
			if(placa.equals(vnvh.tbVehiculos.getValueAt(i, 0))){
				vnvh.tbVehiculos.setRowSelectionInterval(i,i);
				break;
			}
		}
	}
	
	public void mouseClicked(MouseEvent arg0) {
	}
	public void mouseEntered(MouseEvent arg0) {
	}
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	public void mouseReleased(MouseEvent arg0) {
	}
	protected void actionPerformedCbConductor(ActionEvent e) {
	//	int dni = cbConductor.getItemAt(cbConductor.getSelectedIndex()).getDni();
		txtDni.setText("" + cbConductor.getItemAt(cbConductor.getSelectedIndex()).getDni());
	}
	protected void actionPerformedBtnNewConductor(ActionEvent e) {
		vdConductorNuevo vncon = new vdConductorNuevo(this, null, null);
		vncon.setVisible(true);
		this.setVisible(false);
		this.setAlwaysOnTop(false);
	}
}




























