package pe.gob.mef.repository.dao;

import pe.gob.mef.repository.bean.Usuario;

public interface UserDetailDaoInterface {
	
	public Usuario findByUsername(String username);


	void guardar(Usuario usuario) throws Exception;

}
