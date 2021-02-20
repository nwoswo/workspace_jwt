package pe.gob.mef.repository.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;



import pe.gob.mef.repository.bean.Usuario;
import pe.gob.mef.repository.util.Conexion;

@Repository
public class UserDetailDao implements UserDetailDaoInterface {

	

	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		Conexion con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Usuario usuario = null;

	

			
			try { con = new Conexion(); } catch (Exception e) {e.printStackTrace();}
			try {
				//System.out.println(con.getConexion().isClosed());
				con.getConexion().isClosed();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String SQL = "select  "
					+ "u.USU_C_USUARIO, "
					+ "u.USU_D_USUARIO, "
					+ "u.USU_D_PASSWORD, "
					+ "u.USU_E_USUARIO, "
					+ "u.USU_N_UBIGEO,"
					+ "u.USU_D_PERMISOS,"  
					+ "u.ARE_C_AREA,"
					+ "a.ARE_D_NOMBRE "
					+ ""
					+ "FROM  TSOC_USUARIO u, TSOC_AREA a where u.USU_D_USUARIO=?  and u.ARE_C_AREA=a.ARE_C_AREA order BY u.USU_C_USUARIO";
			try {
			con.getConexion().setAutoCommit(false);
			pstmt =  con.getConexion().prepareStatement(SQL);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			con.getConexion().commit();
			while (rs.next()) {
				usuario = new Usuario();
				usuario.setUsuCUsuario(rs.getInt("USU_C_USUARIO"));
				
				usuario.setUsuDUsuario(rs.getString("USU_D_USUARIO"));
				usuario.setUsuDPassword(rs.getString("USU_D_PASSWORD"));
				usuario.setUsuDPermisos(rs.getString("USU_D_PERMISOS"));
				
				usuario.setUsuNUbigeo(rs.getString("USU_N_UBIGEO"));
				usuario.setAreCArea(rs.getInt("ARE_C_AREA"));
				usuario.setAreDNombre(rs.getString("ARE_D_NOMBRE"));
				
				
			}

		} catch (Exception e) {
			e.getStackTrace();
		
			}finally {
				try { con.closeResources(con.getConexion(), rs, null, null, pstmt);} 
				catch (Exception e) {e.printStackTrace();}
				
			}

		
		return usuario;
	}

	@Override
	public void guardar(Usuario usuario)  throws Exception {

		
		System.out.println("save------");
		PreparedStatement pstmt = null;
		Conexion con = null;
		
		
		String SQL = "INSERT INTO TSOC_USUARIO (USU_C_USUARIO, USU_D_USUARIO,USU_D_PASSWORD,USU_N_UBIGEO,ARE_C_AREA,USU_D_PERMISOS,USU_E_USUARIO) "
					+ "VALUES (?, ?,?, ?,?,?,1)";
		try {
			con = new Conexion();
			con.getConexion().setAutoCommit(false);
			
			pstmt = con.getConexion().prepareCall(SQL);
			

			pstmt.setLong(1, usuario.getUsuCUsuario());
			pstmt.setString(2, usuario.getUsuDUsuario());
			pstmt.setString(3, usuario.getUsuDPassword());
			pstmt.setString(4, usuario.getUsuNUbigeo());
			pstmt.setInt(5, usuario.getAreCArea());
			pstmt.setString(6, usuario.getUsuDPermisos());

			
			pstmt.executeUpdate();

			/*
			 * tx.setCodigo("0000"); tx.setDescripcion("Proceso Conforme");
			 */
			con.getConexion().commit();
			
			
		
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception(e.getMessage());
			
		}finally {
			try {
				con.closeResources(con.getConexion(), null, pstmt, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
