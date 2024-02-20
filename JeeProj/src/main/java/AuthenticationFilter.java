import java.io.IOException;
import javax.servlet.annotation.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "NFilter", urlPatterns = { "/hello" })
public class AuthenticationFilter implements Filter
{

	public void init(FilterConfig config) throws ServletException
	{
		System.out
		        .println("------------------------------------------------------");
		System.out.println(" init method is called in "
		        + this.getClass().getName());
		System.out
		        .println("------------------------------------------------------");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
	        FilterChain chain) throws IOException, ServletException
	{
		System.out.println("AuthenticationFilter - Vérification d'authentification. ");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String userName = req.getParameter("username");
		String passWord = req.getParameter("password");

		if (!(userName.equalsIgnoreCase("sondes") && passWord.equals("sondes")))
		{
			System.out
			        .println("######################################################");
			System.out.println("Utilisateur non autorisé");
			System.out
			        .println("######################################################\n\n");
			res.sendRedirect("index.html");
		}
		else
		{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	public void destroy()
	{
		// add code to release any resource
		System.out
		        .println("------------------------------------------------------");
		System.out.println(" destroy method is called in "
		        + this.getClass().getName());
		System.out
		        .println("------------------------------------------------------");
	}
}