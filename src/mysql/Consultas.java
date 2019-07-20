package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import clases.Usuarios;
import mysql.MySQLConexion;

public class Consultas {
	
	public Usuarios obtenerUsuario(Usuarios ingresante){
		Connection con = null;	
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			con = MySQLConexion.getConection();
			String sql = "select * from tb_usuario where usuario = ? COLLATE utf8_bin and pass = ? COLLATE utf8_bin ";
			pst = con.prepareStatement(sql);
			pst.setString(1, ingresante.getUsuario());
			pst.setString(2, ingresante.getPassword());
			rs = pst.executeQuery();
			while (rs.next()) {
				ingresante.setNombre(rs.getString(3));
				ingresante.setTipo(rs.getInt(4));
			}			
		} catch (Exception e) {
			System.out.println("Error en obtener usuario");
		}
		return ingresante;
	}
	
	public ResultSet cargarVehiculosConductores(){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select vh.placa, mvh.idmodelo, mvh.modelo, vh.mtc, vh.detalle, co.dniconductor, co.conductor from tb_vehiculo vh inner join tb_modelo_vehiculo mvh  inner join tb_conductor co on vh.idmodelo = mvh.idmodelo and vh.dniconductor = co.dniconductor order by mvh.modelo");		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet buscarModeloVehiculo(int idmodelovh){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		try {
			String sql = "select * from tb_modelo_vehiculo where idmodelo = ?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, idmodelovh);
			//prepareStmt.execute();
			rs = prepareStmt.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;		
	}
	
	public ResultSet cargarModelosVehiculos(){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from tb_modelo_vehiculo");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet cargarConductores(){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from tb_conductor order by conductor");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet buscarConductor(int dniconductor){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			st = con.createStatement();
			String sql = "select * from tb_conductor where dniconductor = ?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, dniconductor);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet cargarEmpresas(){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from tb_empresa order by idempresa");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public static void crearVehiculo(String placa, int modelo, String detalle, String mtc){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			String sql = "insert into tb_vehiculo (placa, idmodelo, detalle, mtc)" + " values (?, ?, ?, ?)";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, placa);
			prepareStmt.setInt(2, modelo);
			prepareStmt.setString(3, detalle);
			prepareStmt.setString(4, mtc);
			prepareStmt.execute();
			con.close();
			//JOptionPane.showMessageDialog(null, "Conductor creado correctamente");			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public static void modificarVehiculo(String placa, int modelo, String detalle, String mtc){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_vehiculo set idmodelo=?, detalle=?, mtc=? where placa=?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, modelo);
			prepareStmt.setString(2, detalle);
			prepareStmt.setString(3, mtc);
			prepareStmt.setString(4, placa);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public static void crearConductor(int dni, String nlicencia, String conductor){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "insert into tb_conductor (dniconductor, licencia, conductor)" + " values (?, ?, ?)";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, dni);
			prepareStmt.setString(2, nlicencia);
			prepareStmt.setString(3, conductor);
			prepareStmt.execute();
			con.close();
			//JOptionPane.showMessageDialog(null, "Conductor creado correctamente");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR" + e);
		}
	}
	
	public static void modificarConductor(int dni, String nlicencia, String conductor){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_conductor set licencia=?, conductor=? where dniconductor=?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, nlicencia);
			prepareStmt.setString(2, conductor);
			prepareStmt.setInt(3, dni);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public static void modificarConductor(String placa, String detalles, String mtc, int dni){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_vehiculo set detalle=?, mtc=?, dniconductor=? where placa=?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, detalles);
			prepareStmt.setString(2, mtc);
			prepareStmt.setInt(3, dni);
			prepareStmt.setString(4, placa);
			prepareStmt.execute();
			//JOptionPane.showMessageDialog(null, " VEHICULO MODIFICADO CORRECTAMENTE ");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public static void eliminarVehiculo(String placa){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		try {
			String sql = "delete from tb_vehiculo where placa = ?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, placa);		
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public static void eliminarHistorialVehiculo(String placa){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "delete from tb_viaje where placa = ?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, placa);		
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public ResultSet cargarVentaTemporal(){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from tb_venta_temporal");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	//1MERMA 2SIGUEL
	//VIENE DE DATOS1
	public static void actualizarVentaTemporal01(int estado, int empresa, int dniconductor, String placa, int modelovh, float prepasaje){ 
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set estado=? , empresa=?, dniconductor=?, placa=?, modelovh=?, prepasaje=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, estado);
			prepareStmt.setInt(2, empresa);
			prepareStmt.setInt(3, dniconductor);
			prepareStmt.setString(4, placa);
			prepareStmt.setInt(5, modelovh);
			prepareStmt.setFloat(6, prepasaje);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	//VIENE DE MODIFICACION A CONDUCTOR
	public static void actualizarVentaTemporal02(int dniconductor, float prepasaje){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set dniconductor=?, prepasaje=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, dniconductor);
			prepareStmt.setFloat(2, prepasaje);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	//VIENE DE VENTA PASAJES
	public static void actualizarVentaTemporal03(int idorigen, String origen){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set origen=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, origen);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	public static void actualizarVentaTemporal04(int iddestino, String destino){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set destino=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, destino);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	public static void actualizarVentaTemporal05(String fOrigen){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set fpartida=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, fOrigen);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	public static void actualizarVentaTemporal06(String fDestino){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set fllegada=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, fDestino);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	public static void actualizarVentaTemporal07(int nViaje){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set nviaje=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, nViaje);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	public static void actualizarVentaTemporal08(int vstandar, int escalascom, String desde, String hasta, String pencuentro, String escalasparadas,
			String horainicio2, int dniconductor2, String licencia2, String horafin1, String horafin2, String comentarios){ // Viene de LLenar Datos Faltantes
		
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_venta_temporal set standar=?, escalacom=?, ciudaddesde=?, ciudadhasta=?, puntoencuentro=?, escalas=?, horainicio2=?, dniconductor2=?, licencia2=?, horafin1=?, horafin2=?, comentarios=? where id=1";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, vstandar);
			prepareStmt.setInt(2, escalascom);
			prepareStmt.setString(3, desde);
			prepareStmt.setString(4, hasta);
			prepareStmt.setString(5, pencuentro);
			prepareStmt.setString(6, escalasparadas);
			prepareStmt.setString(7, horainicio2);
			prepareStmt.setInt(8, dniconductor2);
			prepareStmt.setString(9, licencia2);
			prepareStmt.setString(10, horafin1);
			prepareStmt.setString(11, horafin2);
			prepareStmt.setString(12, comentarios);
			prepareStmt.execute();
			JOptionPane.showMessageDialog(null, "Cambios guardados correctamente");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public ResultSet buscarSocio(int codsocio){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			st = con.createStatement();
			String sql = "select * from tb_socio where codsocio = ?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, codsocio);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet buscarEmpresa(int idEmpresa){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			st = con.createStatement();
			String sql = "select * from tb_empresa where idempresa = ?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, idEmpresa);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet buscarVehiculo(String placa){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			st = con.createStatement();
			String sql = "select * from tb_vehiculo where placa = ?";
			pst = con.prepareStatement(sql);
			pst.setString(1, placa);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public static void eliminarSalidaVehiculo(){
		Connection con = MySQLConexion.getConection();
		try {
			String sql1 = "delete from tb_venta_temporal where id = 1";
			String sql2 = "insert into tb_venta_temporal values(1, 0, null, null, null, 0, null, null, null, null, null, null, 1, 0, null, null, null, null, null, null, null, null, null, null)";
			String sql3 = "delete from tb_pasajeros_temporal where asiento < 100";
			PreparedStatement prepareStmt = con.prepareStatement(sql1);
			prepareStmt.execute();
			PreparedStatement prepareStmt2 = con.prepareStatement(sql2);
			prepareStmt2.execute();
			PreparedStatement prepareStmt3 = con.prepareStatement(sql3);
			prepareStmt3.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public ResultSet buscarPasajero(int dni){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			st = con.createStatement();
			String sql = "select * from tb_pasajero where dnipasajero = ?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, dni);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public static void crearPasajero(int dnipasajero, String ruc, String fnacimiento, String nombre, String razsocial, String nacionalidad){
		Object fn = fnacimiento;
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "insert into tb_pasajero (dnipasajero, ruc, fnacimiento, nombre, razsocial, nacionalidad)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, dnipasajero);
			prepareStmt.setString(2, ruc);
			prepareStmt.setObject(3, fn);
			prepareStmt.setString(4, nombre);
			prepareStmt.setString(5, razsocial);
			prepareStmt.setString(6, nacionalidad);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR" + e);
		}
	}
	
	public static void actualizarPasajero(int dnipasajero, String ruc, String fnacimiento, String nombre, String razsocial, String nacionalidad){
		Object fn = fnacimiento;
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_pasajero set ruc=?, fnacimiento=?, nombre=?, razsocial=?, nacionalidad=? where dnipasajero=?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, ruc);
			prepareStmt.setObject(2, fn);
			prepareStmt.setString(3, nombre);
			prepareStmt.setString(4, razsocial);
			prepareStmt.setString(5, nacionalidad);
			prepareStmt.setInt(6, dnipasajero);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRORnbvnb: " + e);
		}
	}
	
	public static void eliminarPasajero(int dnipasajero){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		try {
			String sql = "delete from tb_pasajero where dnipasajero = ?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, dnipasajero);		
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public static void asignarAsiento(int asiento, int dnipasajero, int edad, float prepasaje, int nboleto, int contratante){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "insert into tb_pasajeros_temporal (asiento, estado, nboleto, dnipasajero, edad, prepasaje, contratante)" + " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, asiento);
			prepareStmt.setInt(2, 1);
			prepareStmt.setInt(3, nboleto);
			prepareStmt.setInt(4, dnipasajero);
			prepareStmt.setInt(5, edad);
			prepareStmt.setFloat(6, prepasaje);
			prepareStmt.setInt(7, contratante);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR" + e);
		}
	}
	
	public ResultSet cargarPasajerosTemporal(){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			st = con.createStatement();
			String sql = "select * from tb_pasajeros_temporal";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet buscarPasajerosTemporal(int asiento){
		Connection con = MySQLConexion.getConection();
		java.sql.Statement st;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			st = con.createStatement();
			String sql = "select * from tb_pasajeros_temporal where asiento = ?";
			pst = con.prepareStatement(sql);
			pst.setInt(1, asiento);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public static void eliminarAsiento(int asiento){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "delete from tb_pasajeros_temporal where asiento = ?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, asiento);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR" + e);
		}
	}
	
	public ResultSet cargarDestinos(){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			String sql = "select * from tb_destinos";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public static void crearDestino(String destino){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "insert into tb_destinos (destino)" + " values (?)";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setString(1, destino);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR" + e);
		}
	}
	
	public static void eliminarDestino(int iddestino){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		try {
			String sql = "delete from tb_destinos where iddestino = ?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, iddestino);		
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public ResultSet cargarPasajeros(){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			String sql = "select * from tb_pasajero order by nombre";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet cantPasajeros(){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			String sql = "select count(*) as cantPasajeros from tb_pasajeros_temporal";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public ResultSet ultimoNboleto(){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			String sql = "select nboleto from tb_pasajeros_temporal order by nboleto desc limit 1";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	public static void crearSocio(int codsocio, int idempresa, int dnisocio, String nombresocio, int dniconductor, String placa){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "insert into tb_socio (codsocio, idempresa, dnisocio, nombresocio, dniconductor, placa)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, codsocio);
			prepareStmt.setInt(2, idempresa);
			prepareStmt.setInt(3, dnisocio);
			prepareStmt.setString(4, nombresocio);
			prepareStmt.setInt(5, dniconductor);
			prepareStmt.setString(6, placa);
			prepareStmt.execute();
			JOptionPane.showMessageDialog(null, "Socio creado correctamente.");
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR" + e);
		}
	}

	public static void modificarSocio(int codsocio, int idempresa, int dnisocio, String nombresocio, int dniconductor, String placa){
		Connection con = MySQLConexion.getConection();
		try {
			String sql = "update tb_socio set idempresa=?, dnisocio=?, nombresocio=?, dniconductor=?, placa=? where codsocio=?";
			PreparedStatement prepareStmt = con.prepareStatement(sql);
			prepareStmt.setInt(1, idempresa);
			prepareStmt.setInt(2, dnisocio);
			prepareStmt.setString(3, nombresocio);
			prepareStmt.setInt(4, dniconductor);
			prepareStmt.setString(5, placa);
			prepareStmt.setInt(6, codsocio);
			prepareStmt.execute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
	}
	
	public ResultSet cargarSocios(){
		Connection con = MySQLConexion.getConection();
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			String sql = "select sc.codsocio, sc.nombresocio, sc.dnisocio, e.empresa, c.conductor, c.dniconductor, sc.placa from tb_socio sc inner join tb_conductor c on c.dniconductor = sc.dniconductor inner join tb_empresa e on e.idempresa = sc.idempresa;";
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: " + e);
		}
		return rs;
	}
	
	
	
}
