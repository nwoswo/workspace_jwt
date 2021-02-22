package com.niit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.dao.DataDao;
import com.niit.domain.Employee;

@Service("dataservice")
public class DataServiceImpl implements DataService {
 
 @Autowired
 DataDao dataDao;

 @Override
 public int insertRow(Employee employee) {
  return dataDao.insertRow(employee);
 }

 @Override
 public List getList() {
  return dataDao.getList();
 }

 @Override
 public Employee getRowById(int id) {
  return dataDao.getRowById(id);
 }

 @Override
 public int updateRow(Employee employee) {
  return dataDao.updateRow(employee);
 }

 @Override
 public int deleteRow(int id) {
  return dataDao.deleteRow(id);
 }

}