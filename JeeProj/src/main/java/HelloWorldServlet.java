import java.io.IOException;
import javax.servlet.annotation.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MyServlet", urlPatterns = { "/hello" })
public class HelloWorldServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException
	{
		System.out
		        .println("------------------------------------------------------");
		System.out.println(" Init method is called in "
		        + this.getClass().getName());
		System.out
		        .println("------------------------------------------------------");
	}

	// Method to handle GET method request.
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	{

		
		System.out.println(" doGet method is called in "
		        + this.getClass().getName() + "\n\n");
		System.out
		        .println("------------------------------------------------------");

		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Ma Page HTML</title></head>");
		out.println("<body>");
		out.println("<h1>Bienvenue sur ma page HTML </h1>");
		out.println("<p>Hello from MyServlet</p>");
		
		out.println("</body>");
		out.println("</html>");


	}

	public void destroy()
	{
		System.out
		        .println("------------------------------------------------------");
		System.out.println(" destroy method is called in "
		        + this.getClass().getName());
		System.out
		        .println("------------------------------------------------------");
	}
}