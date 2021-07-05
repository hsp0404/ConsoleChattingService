import java.sql.*;

public class DBConnect {
    String sql;
    Connection conn = null;
    ResultSet rs = null;
    Statement stmt = null;
    ResultSetMetaData rsmd = null;

    public DBConnect(ResultSet rs, ResultSetMetaData rsmd) {
        this.rs = rs;
        this.rsmd = rsmd;
    }


    public DBConnect(String sql) {
        this.sql = sql;
    }

    public void Connect(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String driver = "jdbc:oracle:thin:@152.70.238.46:1521/XE";
            String user = "hsp94";
            String pass = "java";
            conn = DriverManager.getConnection(driver, user, pass);
            stmt = conn.createStatement();
        } catch (SQLException se) {
            System.out.println(se);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Execute(){
        try {
            rs = stmt.executeQuery(sql);
            rsmd = rs.getMetaData();
        } catch (SQLException e) {
            System.out.println("SQL Error : " + e.getMessage());
        }
    }
    public void Result(){
    }
    public void Close(){
        if(rs!=null) try {rs.close();}catch(Exception e){}
        if(stmt!=null) try {stmt.close();}catch(Exception e){}
        if(conn!=null) try {conn.close();}catch(Exception e){}
    }
}
