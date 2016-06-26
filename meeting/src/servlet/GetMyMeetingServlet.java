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
import org.json.JSONException;
import org.json.JSONObject;

import bean.Meeting;
import dao.Dao;

/**
 * Servlet implementation class GetMyMeetingServlet
 */
@WebServlet("/GetMyMeetingServlet")
public class GetMyMeetingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMyMeetingServlet() {
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
		
		JSONObject jsobj = new JSONObject();
	    
	    for (int i = 2; i >= 0; i--) {
	    	ArrayList<Meeting> meetingList = null;
			
			Dao dao = Dao.getInstance();
			meetingList = dao.getSchedule(temp,i);
			
			JSONArray jsoarray = new JSONArray();
			int index = 0;
			for (int j=0;j < meetingList.size();j++) {
				JSONObject jsobjt = new JSONObject(meetingList.get(j));
				jsoarray.put(index, jsobjt);
				index++;
			}
			
			try {
				jsobj.append("type"+String.valueOf(i), jsoarray);	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
		
		
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(jsobj.toString());
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
