import java.sql.*;
import java.util.Scanner;

public class Sign {
    private String id;
    private String pw;

    public String getNickname() {
        return nickname;
    }

    private String nickname;

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public Sign() {
    }

    public void SignIn() {
        Scanner s = new Scanner(System.in);
        try{
            while (true) {
                System.out.println("----------------------------SIGN IN----------------------------");
                System.out.println();
                System.out.println("             회원가입은 ID란에 Signup 을 적으시면 됩니다.");
                System.out.println();
                System.out.print("                     ID : ");
                this.id = s.next();
                if (id.equals("Signup")) {
                    throw new Exception();
                }
                System.out.println();
                System.out.println();
                System.out.print("                     PW : ");
                String pw = s.next();
                try {
                    DBConnect select = new Select("SELECT MEMBER_ID, MEMBER_PW, MEMBER_NICK FROM CMEMBER WHERE MEMBER_ID = '" + id + "'");
                    select.Connect();
                    select.Execute();
                    if (!select.rs.next()) {
                        System.out.println("아이디가 존재하지 않습니다.");
                        continue;
                    } else if (select.rs.getString(2).equals(pw)) {
                        nickname = select.rs.getString(3);
                        System.out.println("로그인 성공!");
                        select.Close();
                        break;
                    } else{
                        System.out.println("비밀번호가 틀립니다.");
                        continue;
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Error : " + e.getMessage());
                }
            }

        } catch (Exception e) {
            SignUp();
        }
    }
    public void SignUp(){
        Scanner s = new Scanner(System.in);
        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;
        String id = null;
        String pw = null;
        String nickname = null;

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
        while (true) {
            System.out.println("----------------------------SIGN UP----------------------------");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.print("                     ID : ");
            id = s.next();
            if (id.equals("Sign up")) {
            }
            System.out.println();
            System.out.println();
            System.out.print("                     PW : ");
            pw = s.next();
            System.out.println();
            System.out.println();
            System.out.print("                  닉네임 : ");
            nickname = s.next();
            System.out.println("ID : " + id + ", 비밀번호: " + pw + ", 닉네임 : " + nickname);
            System.out.print("등록하시겠습니까? (y/n) ");
            String yn = s.next();
            if (yn.equals("y")) {
                try {
                    stmt = conn.createStatement();
                    String sql = "INSERT INTO CMEMBER VALUES('"+id+"','"+pw+"','"+nickname+"') ";
                    rs = stmt.executeQuery(sql);
                    System.out.println("등록 완료!");
                    SignIn();
                } catch (SQLException e) {
                    System.out.println("아이디가 중복됩니다. 다시 등록하세요");
                    continue;
                }
                break;

            } else {
                continue;
            }
        }
        if(stmt!=null) try {stmt.close();}catch(Exception e){}
        if(conn!=null) try {conn.close();}catch(Exception e){}

        }


}
