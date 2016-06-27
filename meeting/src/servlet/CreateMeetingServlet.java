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

import bean.User;
import dao.Dao;

/**
 * Servlet implementation class CreateMeetingServlet
 */
@WebServlet("/CreateMeetingServlet")
public class CreateMeetingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMeetingServlet() {
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
		request.setCharacterEncoding("utf-8");
		//获取用户ID
		String userID = request.getParameter("userID");
		int temp = Integer.parseInt(userID);
		//获取要参加的会议的ID
		String duration=request.getParameter("duration");
		int temp1 = Integer.parseInt(duration);
		String content=request.getParameter("content");
		String beginTime=request.getParameter("beginTime");	
		String endTime=request.getParameter("endTime");
		
		int x=(int)(Math.random()*100);
		String place = "会议室" + x;
		Dao dao = Dao.getInstance();
		dao.create(beginTime,temp1,content,place,"已推送",temp,endTime);	
		ArrayList<User> userList = null;
		userList = dao.askfree(beginTime,endTime);

		JSONArray jsoarray = new JSONArray();
		int index = 0;
		for (int i=0;i<userList.size()-1;i++) {
			JSONObject jsobjt = new JSONObject(userList.get(i));
			jsoarray.put(index, jsobjt);
			index++;
		}	
		
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = null;
		JSONObject jsobj = new JSONObject();
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


