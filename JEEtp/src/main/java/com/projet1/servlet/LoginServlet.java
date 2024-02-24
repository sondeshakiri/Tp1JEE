package com.projet1.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginServlet  extends HttpServlet{

	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
     
		resp.setContentType("text/html");//Présicer le type de notre respo ,on specifie quelle est html
		PrintWriter out=resp.getWriter();//isntancier un objet de type printwriter pour aider a afficher sur notre site
		String nom=req.getParameter("nom");//recuperer le nom depuis la requete 
		String ville=req.getParameter("ville");
		System.out.println(nom);
		System.out.println(ville);

		out.println("Bienvenue User :"+nom +" de:  "+ville);//donc a travers out on peux afficher sur notre site
		System.out.println("methode get");
    }
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
     
		resp.setContentType("text/html");//pour dire que l'affichage et en HTML
		PrintWriter out= resp.getWriter();//permet l'ecriture sur la page
		String nom=req.getParameter("nom");//recuperer le nom depuis la requete 
		String pass=req.getParameter("pass");//recuperer le mot de passe
		if(LoginDao.validate(nom, pass))//appel au méthode dans la classe LoginDao pour tester si l'utilisateur est autorisé ou pas
        {  
			
			System.out.println("methode Post");
	        RequestDispatcher rd=req.getRequestDispatcher("WelcomeServlet");  //faire la redirection vers servlet 2 si il est autoriser de connecter
	        rd.forward(req,resp);  
	    }  
	    else{  
	        out.print("Sorry username or password error");  
	        RequestDispatcher rd=req.getRequestDispatcher("index.html");  
	        rd.include(req,resp);  
	    }  
	          
	 
	    
	
    }
	
	
	
	
	
}
