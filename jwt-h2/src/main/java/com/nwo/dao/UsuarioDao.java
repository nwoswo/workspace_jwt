package com.nwo.dao;

import java.util.List;
import com.nwo.domain.Usuario;


public interface UsuarioDao {
	
 public Usuario findByUsername(String username);
	
 public int insertRow(Usuario usuario);

 public List getList();

 public Usuario getRowById(int id);

 public int updateRow(Usuario usuario);

 public int deleteRow(int id);

}