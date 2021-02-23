package com.nwo.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nwo.domain.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {
	
	 @Autowired
	 SessionFactory sessionFactory;
	 
	 
	 
    public Usuario findByUsername(String username) {
    	System.out.println("username -"+username);
        return (Usuario) sessionFactory.openSession()
                .createCriteria(Usuario.class)
                .add(Restrictions.eq("username", username))
                .uniqueResult();
    }

	public int insertRow(Usuario usuario) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(usuario);
		tx.commit();
		Serializable id = session.getIdentifier(usuario);
		session.close();
		return (Integer) id;
	}

	public List getList() {
		  Session session = sessionFactory.openSession();
		  List employeeList = session.createQuery("from Usuario").list();
		  session.close();
		  return employeeList;
	}

	public Usuario getRowById(int id) {
		  Session session = sessionFactory.openSession();
		  Usuario employee = (Usuario) session.load(Usuario.class, id);
		  return employee;
	}

	public int updateRow(Usuario usuario) {
		  Session session = sessionFactory.openSession();
		  Transaction tx = session.beginTransaction();
		  session.saveOrUpdate(usuario);
		  tx.commit();
		  Serializable id = session.getIdentifier(usuario);
		  session.close();
		  return (Integer) id;
	}

	public int deleteRow(int id) {
		  Session session = sessionFactory.openSession();
		  Transaction tx = session.beginTransaction();
		  Usuario employee = (Usuario) session.load(Usuario.class, id);
		  session.delete(employee);
		  tx.commit();
		  Serializable ids = session.getIdentifier(employee);
		  session.close();
		  return (Integer) ids;
	}


}