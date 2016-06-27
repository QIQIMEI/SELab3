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

import bean.User;
import dao.Dao;

/**
 * Servlet implementation class AttendMeetingServlet
 */
@WebServlet("/AddAttendenceServlet")
public class AddAttendenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAttendenceServlet() {
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
		response.setContentType("text/html;charset=gb2312");
		//获取用户ID
		String userList = request.getParameter("userlist");
		//获取要参加的会议的ID
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String duration=request.getParameter("duration");
		String content=request.getParameter("content");
		String place = null;
		JSONArray array = new JSONArray(userList);
		System.out.println("1313131313"+ array);
		Dao dao = Dao.getInstance();
		if ( array.length()!=0)
		{
		for (int i = 0; i < array.length(); i++){
	       JSONObject jo = (JSONObject) array.get(i);
	       int level=(int) jo.get("level");
	       int userID=(int) jo.get("userID");
		   place = dao.addattendence(userID,level,beginTime,endTime,content);
	    }		      
		
		PrintWriter out=response.getWriter();
		String info="会议内容:"+content+" 开始时间:"+beginTime+" 持续时间:"+duration+"分钟  地点："+place+" 人数:"+ array.length()+"人";
		out.append(new String(info.getBytes("gb2312")));
		}
		else
		{
		PrintWriter out=response.getWriter();
		String info="请重新选择时间或者人员";
		out.append(new String(info.getBytes("gb2312")));
		}
	}
}
