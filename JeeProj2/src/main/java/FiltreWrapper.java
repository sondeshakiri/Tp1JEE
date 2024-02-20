import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

@WebFilter("/MyServlet")
public class FiltreWrapper implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException 
    
    {
      
    	System.out.println("MyFilter - Before Servlet Execution");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        String userName = req.getParameter("username");
		String passWord = req.getParameter("password");

        // Utiliser notre propre wrapper pour la réponse
        MyResponseWrapper myResponseWrapper = new MyResponseWrapper(res);

        // Continuer la chaîne de filtres avec le wrapper personnalisé
        chain.doFilter(request, myResponseWrapper);

        // Modifier le contenu de la réponse
        String modifiedContent = myResponseWrapper.getCapturedOutput().toUpperCase();

        // Écrire la réponse modifiée dans le flux de sortie d'origine
        res.getWriter().write(modifiedContent);

        System.out.println("MyFilter - After Servlet Execution");
    	
    }

    public void destroy() 
    {
    }

    
    
    public class MyResponseWrapper extends HttpServletResponseWrapper {

        private CharArrayWriter charArray = new CharArrayWriter();
        private PrintWriter writer = new PrintWriter(charArray);

        public MyResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public PrintWriter getWriter() {
            // Utiliser notre propre PrintWriter qui écrit dans charArray
            return writer;
        }

        public String getCapturedOutput() {
            // Récupérer le contenu capturé dans charArray
            return charArray.toString();
        }
    }
    
}
