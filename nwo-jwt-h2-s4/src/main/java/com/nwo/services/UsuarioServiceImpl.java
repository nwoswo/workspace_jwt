package com.nwo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nwo.dao.UsuarioDao;
import com.nwo.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	 @Autowired
	 UsuarioDao usuarioDao;

	public int insertRow(Usuario usuario) {
		return usuarioDao.insertRow(usuario);
	}

	public List getList() {
		return usuarioDao.getList();
	}

	public Usuario getRowById(int id) {
		return usuarioDao.getRowById(id);
	}

	public int updateRow(Usuario usuario) {
		return usuarioDao.updateRow(usuario);
	}

	public int deleteRow(int id) {
		return usuarioDao.deleteRow(id);
	}

}
