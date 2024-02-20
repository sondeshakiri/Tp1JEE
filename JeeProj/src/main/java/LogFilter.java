import java.io.IOException;
import javax.servlet.annotation.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@WebFilter(filterName = "LogFilter", urlPatterns = { "/hello" })
public class LogFilter implements Filter
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

	public void doFilter(ServletRequest req, ServletResponse res,
	        FilterChain chain) throws IOException, ServletException
	{

		 // Logguer la requête
        System.out.println("LoggingFilter - Requête interceptée.");

        // Passer la requête au filtre suivant dans la chaîne
        chain.doFilter(req, res);

        // Logguer la réponse
        System.out.println("LoggingFilter - Réponse interceptée.");

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