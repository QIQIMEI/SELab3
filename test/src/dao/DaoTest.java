package dao;

import bean.User;

import java.sql.*;

public class DaoTest {
    private Connection con=null;
    private Statement sm = null;
    private ResultSet results = null;

    private Dao dao;

    @org.junit.Before
    public void setUp() throws Exception {
        String url = "jdbc:mysql://fdu.nxtsysx.net:3306/meetingbase?useUnicode=true&characterEncoding=UTF-8";
        String dbUsername = "selab3";
        String dbPassword = "selab3qqm";

        try {
            con = DriverManager.getConnection(url, dbUsername, dbPassword);
            sm = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dao = Dao.getInstance();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        try {
            if(sm != null)
                sm.close();
            if(con != null)
                con.close();
            if(results != null)
                results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void login() throws Exception {
        String username="testName1", password="testPass1";

        results = sm.executeQuery("select * from user where user.username='"+username+"'");
        if(results.next()){
            String oldPassword = results.getString("password");
            if(oldPassword.equals(password)){
                User user = new User(results.getInt("userID"),username);
                user.setUserID(results.getInt("userID"));
                user.setUsername(username);
                //TODO test here
            }
        }
    }

    @org.junit.Test
    public void getSchedule() throws Exception {

    }

    @org.junit.Test
    public void getMeeting() throws Exception {

    }

    @org.junit.Test
    public void getNotice() throws Exception {

    }

    @org.junit.Test
    public void cancelMeeting() throws Exception {

    }

    @org.junit.Test
    public void attendMeeting() throws Exception {

    }

    @org.junit.Test
    public void create() throws Exception {

    }

    @org.junit.Test
    public void addattendence() throws Exception {

    }

    @org.junit.Test
    public void askfree() throws Exception {

    }

}