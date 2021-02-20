package pe.gob.mef.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

/**
 * Created by LynAs on 20-Jan-16
 */
@Configuration
@EnableTransactionManagement
public class PersistConfig {
	
	
	@Bean
	  public DataSource dataSource() {


	    DataSource dataSource = null;
	    try {
	      InitialContext init = new InitialContext();
	      
		  Context env = (Context) init.lookup("java:comp/env");
	      dataSource = (DataSource) env.lookup("jdbc/TransparenciaDS");
	      
	      /*
	      Context env = (Context) init.lookup("java:jboss");
	      dataSource = (DataSource) env.lookup("datasources/TransparenciaDS");
	      */

	    } catch (Exception e) {
	    	System.out.println(e);

	      throw new RuntimeException(e);
	    }
	    return dataSource;
	  }
	
}
