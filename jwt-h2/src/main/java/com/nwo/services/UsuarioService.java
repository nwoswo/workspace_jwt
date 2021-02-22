package com.nwo.services;

import java.util.List;

import com.nwo.domain.Usuario;


public interface UsuarioService {

	 public int insertRow(Usuario usuario);

	 public List getList();

	 public Usuario getRowById(int id);

	 public int updateRow(Usuario usuario);

	 public int deleteRow(int id);
}
