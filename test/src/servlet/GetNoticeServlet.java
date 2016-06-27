package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.Notice;
import dao.Dao;

/**
 * Servlet implementation class GetNoticeServlet
 */
@WebServlet("/GetNoticeServlet")
public class GetNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		//获取用户的ID
		String userID = request.getParameter("userID");
		int temp = Integer.parseInt(userID);
		
		ArrayList<Notice> noticeList = null;
		
		Dao dao = Dao.getInstance();
		noticeList = dao.getNotice(temp);
		
		JSONArray jsoarray = new JSONArray();
		int index = 0;
		for (int i=0;i < noticeList.size();i++) {
			JSONObject jsobjt = new JSONObject(noticeList.get(i));
			jsoarray.put(index, jsobjt);
			index++;
		}
		
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(jsoarray.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
