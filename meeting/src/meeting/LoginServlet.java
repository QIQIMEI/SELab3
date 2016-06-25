package meeting;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import meeting.User;
import meeting.Dao;

/**
 * Servlet implementation class LoginServlet
 * @param <HttpServletRequest>
 * @param <HttpServletResponse>
 */
@WebServlet("/login")
public class LoginServlet<HttpServletRequest, HttpServletResponse> extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		((ServletRequest) request).setCharacterEncoding("utf-8");
		
		String username = ((ServletRequest) request).getParameter("username");
		String password = ((ServletRequest) request).getParameter("password");
		
		Dao dao = Dao.getInstance();
		User user = null;
		user = dao.login(username, password);
		if(user != null){
			((javax.servlet.http.HttpServletRequest) request).getSession().setAttribute("user", user);
			// your note board jsp, not this login jsp
			((javax.servlet.http.HttpServletResponse) response).sendRedirect("jsp");
		}else{
			((javax.servlet.http.HttpServletRequest) request).getSession().setAttribute("error", "error");
			((javax.servlet.http.HttpServletResponse) response).sendRedirect("Login.jsp");
		}
	}
}
