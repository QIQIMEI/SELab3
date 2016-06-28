package dao;

import bean.User;

import java.sql.*;

public class DaoTest {
    private String url = "jdbc:mysql://fdu.nxtsysx.net:3306/meetingbase?useUnicode=true&characterEncoding=UTF-8";

    //your username and password
    private String dbUsername = "selab3";
    private String dbPassword = "selab3qqm";

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void login() throws Exception {
        String username="testName1", password="testPass1";

        Dao dao = Dao.getInstance();
        Connection con = null;
        Statement sm = null;
        ResultSet results = null;
        try {
            con = DriverManager.getConnection(url, dbUsername, dbPassword);
            sm = con.createStatement();
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
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if( sm != null){
                    sm.close();
                }
                if(con != null){
                    con.close();
                }
                if(results != null){
                    results.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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