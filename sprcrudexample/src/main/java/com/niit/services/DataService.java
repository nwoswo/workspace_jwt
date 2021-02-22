package com.niit.services;

import java.util.List;

import com.niit.domain.Employee;


public interface DataService {
 public int insertRow(Employee employee);

 public List getList();

 public Employee getRowById(int id);

 public int updateRow(Employee employee);

 public int deleteRow(int id);

}
