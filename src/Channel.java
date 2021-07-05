import java.sql.*;
import java.util.Scanner;

public class Channel {
    private String id;
    private String nickname;
    public Channel(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
    public void ShowChannel(){
        Scanner s = new Scanner(System.in);
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String driver = "jdbc:oracle:thin:@152.70.238.46:1521/XE";
            String user = "hsp94";
            String pass = "java";
            conn = DriverManager.getConnection(driver, user, pass);
        } catch (SQLException se) {
            System.out.println(se);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("------------------------"+nickname+"님의 대화방--------------------------");


    }
}
