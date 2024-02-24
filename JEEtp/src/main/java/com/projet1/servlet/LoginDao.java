package com.projet1.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao {

	public static boolean validate(String name,String pass){  
		boolean status=false;  
		try{  
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/empolyees?useSSL=false", "root", "");//faire la connection au DB
		      
		PreparedStatement ps=con.prepareStatement(               //select user form DB
		"select * from userreg where name=? and pass=?");  
		ps.setString(1,name);  
		ps.setString(2,pass);  //affecter les variable nécassaire au requet select
		      
		ResultSet rs=ps.executeQuery();  //execution de requet
		status=rs.next();  
		          
		}catch(Exception e){System.out.println(e);}  
		return status;  
		}  
}
