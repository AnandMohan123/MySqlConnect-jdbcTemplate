package com.jcg.spring.jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class MainApp {

	static JdbcTemplate jdbcTemplateObj;
	static SimpleDriverDataSource dataSourceObj;

	// Database Configuration Parameters
	static String DB_USERNAME = "anandmohan";
	static String DB_PASSWORD = "Accenture@123";
	static String DB_URL = "jdbc:mysql://localhost:3306/contactdb";

	public static SimpleDriverDataSource getDatabaseConnection()  {
		dataSourceObj = new SimpleDriverDataSource();
		try {			
			dataSourceObj.setDriver(new com.mysql.jdbc.Driver());
			dataSourceObj.setUrl(DB_URL);
			dataSourceObj.setUsername(DB_USERNAME);
			dataSourceObj.setPassword(DB_PASSWORD);
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return dataSourceObj;
	}

	public static void main(String[] args) throws SQLException {
		// Code To Set Driver Class Name, Database URL, Username & Password
		// SpringApplication.run(MainApp.class, args);
		jdbcTemplateObj = new JdbcTemplate(getDatabaseConnection());
		
		

		if(null != jdbcTemplateObj) {

			// SQL Operation #1 - SQL INSERT Operation
			String sqlInsertQuery = "INSERT INTO contact (name, email, address, telephone) VALUES (?, ?, ?, ?)";
			for(int j=101; j<106; j++) {
				jdbcTemplateObj.update(sqlInsertQuery, "Editor " + j, "editor" + j +"@javacodegeek.com", "Greece", "0123456789");
			}

			// SQL Operation #2 - SQL UPDATE Operation
			String sqlUpdateQuery = "UPDATE contact set email=? where name=?";
			jdbcTemplateObj.update(sqlUpdateQuery, "administrator101@javacodegeek.com", "Editor 101");

			// SQL Operation #3 - SQL READ Operation
			String sqlSelectQuery = "SELECT name, email, address, telephone FROM contact";
			List<Contact> listContacts = jdbcTemplateObj.query(sqlSelectQuery, new RowMapper<Contact>() {
				public Contact mapRow(ResultSet result, int rowNum) throws SQLException {
					Contact contactObj = new Contact();
					contactObj.setName(result.getString("name"));
					contactObj.setEmail(result.getString("email"));
					contactObj.setAddress(result.getString("address"));
					contactObj.setPhone(result.getString("telephone"));
					return contactObj;
				}
			});

			// Displaying The SQL Records
			for (Contact contactDetail : listContacts) {
				System.out.println(contactDetail.toString());
			}

			// SQL Operation #4 - SQL DELETE Operation
			String sqlDeleteQuery = "DELETE FROM contact where name=?";
			jdbcTemplateObj.update(sqlDeleteQuery, "Editor 104");
		} else {
			System.out.print("Application Is Not Able To Bind With The Database! Please Check!");
		}
	}
	
	
	
	public void save(Contact p){    
		
		// SQL Operation #1 - SQL INSERT Operation
		String sqlInsertQuery = "INSERT INTO contact (name, email, address, telephone) VALUES (?, ?, ?, ?)";
			for(int j=101; j<106; j++) {
				jdbcTemplateObj.update(sqlInsertQuery, "Editor " + j, "editor" + j +"@javacodegeek.com", "Greece", "0123456789");
				// return jdbcTemplateObj.update(sqlInsertQuery);  
			}
			
	}

	public int delete(int id){    
		
		// SQL Operation #4 - SQL DELETE Operation
		String sqlDeleteQuery = "DELETE FROM contact where name=?";
		return jdbcTemplateObj.update(sqlDeleteQuery, "Editor 104");
	   // String sql="delete from Emp99 where id="+id+"";    
	   // jdbcTemplateObj.update(sql);    
	}    
	
	public int update(Contact p){   
		
		String sqlUpdateQuery = "UPDATE contact set email=? where name=?";
		return jdbcTemplateObj.update(sqlUpdateQuery, "administrator101@javacodegeek.com", "Editor 101");
	    // String sql="update Emp99 set name='"+p.getName()+"', salary="+p.getSalary()+",designation='"+p.getDesignation()+"' where id="+p.getId()+"";    
	    // return template.update(sql);    
	}    
	
}