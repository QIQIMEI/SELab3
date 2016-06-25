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
@WebServlet("/create")
public class CreateServlet<HttpServletRequest, HttpServletResponse> extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
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
		
		String meetingID = ((ServletRequest) request).getParameter("");
		String userID = ((ServletRequest) request).getParameter("");	
		Dao dao = Dao.getInstance();
        dao.create(meetingID, userID);
		{
			((javax.servlet.http.HttpServletRequest) request).getSession().setAttribute("user", userID);
			// your note board jsp, not this login jsp
			((javax.servlet.http.HttpServletResponse) response).sendRedirect("NoteBoard.jsp");
		}
	}
}
