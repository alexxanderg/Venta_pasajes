package gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import clases.Conductor;
import clases.Vehiculo;
import mysql.Consultas;

import java.awt.Color;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class viDatos1 extends JInternalFrame implements ActionListener {
	private JLabel lblNewLabel;
	private JLabel lblVehiculo;
	private JButton btnContinuar;
	private JComboBox cbEmpresa;
	private JComboBox <Vehiculo> cbVehiculo;
	
	vPrincipal  vp = null;
	ResultSet rs;
	private JButton btnCancelar;
	private JLabel lblPrecioDePasaje;
	private JTextField txtPrePasaje;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viDatos1 frame = new viDatos1(null);
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
	public viDatos1(vPrincipal temp) {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setTitle("SELECCIONE");
		vp = temp;
		setBounds(100, 100, 667, 307);
		getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("Empresa:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
		lblNewLabel.setBounds(35, 49, 132, 20);
		getContentPane().add(lblNewLabel);
		
		lblVehiculo = new JLabel("Vehiculo:");
		lblVehiculo.setHorizontalAlignment(SwingConstants.LEFT);
		lblVehiculo.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
		lblVehiculo.setBounds(35, 112, 132, 20);
		getContentPane().add(lblVehiculo);
		
		btnContinuar = new JButton("Continuar");
		btnContinuar.setForeground(Color.WHITE);
		btnContinuar.setBackground(Color.DARK_GRAY);
		btnContinuar.setFont(new Font("USAngel", Font.PLAIN, 20));
		btnContinuar.addActionListener(this);
		btnContinuar.setBounds(412, 235, 229, 31);
		getContentPane().add(btnContinuar);
		
		cbEmpresa = new JComboBox();
		cbEmpresa.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		cbEmpresa.setModel(new DefaultComboBoxModel(new String[] {"MERMA HERMANOS S.R.L", "ZIGUEL E.I.R.L"}));
		cbEmpresa.setBounds(162, 49, 479, 23);
		getContentPane().add(cbEmpresa);
		
		cbVehiculo = new JComboBox();
		cbVehiculo.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		cbVehiculo.setBounds(162, 109, 479, 23);
		getContentPane().add(cbVehiculo);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("USAngel", Font.PLAIN, 20));
		btnCancelar.setBackground(Color.DARK_GRAY);
		btnCancelar.setBounds(160, 235, 221, 31);
		getContentPane().add(btnCancelar);
		
		lblPrecioDePasaje = new JLabel("Precio de pasaje:");
		lblPrecioDePasaje.setHorizontalAlignment(SwingConstants.LEFT);
		lblPrecioDePasaje.setFont(new Font("EngraversGothic BT", Font.PLAIN, 25));
		lblPrecioDePasaje.setBounds(35, 166, 229, 20);
		getContentPane().add(lblPrecioDePasaje);
		
		txtPrePasaje = new JTextField();
		txtPrePasaje.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 18));
		txtPrePasaje.setColumns(10);
		txtPrePasaje.setBounds(250, 164, 131, 25);
		getContentPane().add(txtPrePasaje);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cbEmpresa, cbVehiculo, txtPrePasaje, btnContinuar, btnCancelar}));
		cargar();
	}
	
	public void cargar(){
		Vehiculo vehiculo = new Vehiculo();
		vehiculo.cargarVehiculo(cbVehiculo);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancelar) {
			actionPerformedBtnCancelar(e);
		}
		if (e.getSource() == btnContinuar) {
			actionPerformedBtnContinuar(e);
		}
	}
	
	protected void actionPerformedBtnCancelar(ActionEvent e){		
		this.setVisible(false);
		vp.esconderVentanas();
		vp.cerrarVentanas();
	}
	
	protected void actionPerformedBtnContinuar(ActionEvent e) {
		if(txtPrePasaje.getText().length() <=0)
			JOptionPane.showMessageDialog(null, "Ingrese el precio del pasaje");
		else{
			vp.esconderVentanas();
			vp.cerrarVentanas();
			int empresa = 0;
			if(cbEmpresa.getSelectedIndex() == 0)
				empresa = 1; //MERMA
			if(cbEmpresa.getSelectedIndex() == 1)
				empresa = 2; //SIGUEL
			int dniconductor = cbVehiculo.getItemAt(cbVehiculo.getSelectedIndex()).getDniconductor();
			String placa = cbVehiculo.getItemAt(cbVehiculo.getSelectedIndex()).getPlaca();
			int modelovh = cbVehiculo.getItemAt(cbVehiculo.getSelectedIndex()).getIdmodelo();
			float prepasaje = Float.parseFloat(txtPrePasaje.getText());
			
			Consultas consulta = new Consultas();
			consulta.actualizarVentaTemporal01(1, empresa, dniconductor, placa, modelovh, prepasaje);
			vp.mntmCrearNuevaSalida.setEnabled(false);
			vp.mntmContinuarPreparacion.setEnabled(true);
			vp.mntmCancelarSalida.setEnabled(true);
			
			switch(modelovh){
			case 1:
				vp.sa1 = new viSeleccionAsientos1(vp);
				vp.desktopPane.add(vp.sa1);
				vp.sa1.show();
				try{
					vp.sa1.setMaximum(true);
				}catch(Exception f){}
				break;
			case 2:
				vp.sa2 = new viSeleccionAsientos2(vp);
				vp.desktopPane.add(vp.sa2);
				vp.sa2.show();
				try{
					vp.sa2.setMaximum(true);
				}catch(Exception f){}
				break;
			case 3:
				vp.sa3 = new viSeleccionAsientos3(vp);
				vp.desktopPane.add(vp.sa3);
				vp.sa3.show();
				try{
					vp.sa3.setMaximum(true);
				}catch(Exception f){}
				break;
			case 4:
				vp.sa4 = new viSeleccionAsientos4(vp);
				vp.desktopPane.add(vp.sa4);
				vp.sa4.show();
				try{
					vp.sa4.setMaximum(true);
				}catch(Exception f){}
				break;
			}
		}
	}
}
