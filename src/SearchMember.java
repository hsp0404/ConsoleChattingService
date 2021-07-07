import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchMember {
    private String id;
    public String otherId;
    public String otherNick;
    public int newChannelno = NewChannelno();
    public SearchMember(String id) {
        this.id = id;
    }
    public boolean MemberList(){
        String channelInput;
        Scanner s = new Scanner(System.in);
        Map<Integer, String> otherIdPair = new HashMap<>();
        Map<Integer, String> otherNickPair = new HashMap<>();
        System.out.println("------------------- 대화할 상대를 고르세요 ------------------------");
        Select select = new Select("SELECT * FROM CMEMBER WHERE MEMBER_ID !='"+id+"'");
        select.Connect();
        try {
            select.Execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int channelCnt = 1;
        try {
            while(select.rs.next()){
                System.out.println(channelCnt+". "+select.rs.getString(select.rsmd.getColumnName(3))+" 님과 대화하기");
                otherNickPair.put(channelCnt,select.rs.getString(select.rsmd.getColumnName(3)));
                otherIdPair.put(channelCnt,select.rs.getString(select.rsmd.getColumnName(1)));
                channelCnt++;
            }
//            if(channelPair.isEmpty()){
//                System.out.println("대화방이 없습니다.");
//                System.out.println();
//                System.out.println();
//            }
            System.out.println();
            System.out.println();
            System.out.println("대화할 상대를 선택하세요              뒤로 가시려면 back을 입력하세요");
            System.out.println("----------------------------------------------------------------");
            System.out.print(">> ");
            String regax = "^[1-" +String.valueOf(channelCnt-1)+ "]*$";

            while (true) {
                channelInput = s.next();
                if (channelInput.matches(regax)||channelInput.equals("back")){
                    if(channelInput.equals("back")){
                        return false;
                    } else{
                        otherId = otherIdPair.get(Integer.parseInt(channelInput));
                        otherNick = otherNickPair.get(Integer.parseInt(channelInput));
                        DBConnect insert = new Insert("INSERT INTO CHANNEL VALUES ("+newChannelno+", SYSDATE,'"+id+"','"+ otherId+"')");
                        insert.Connect();
                        insert.Execute();
                        System.out.println("<<<<<<<<<<<<<<<<<< "+otherNick+"님과의 대화가 시작됐습니다! >>>>>>>>>>>>>>>>>");
                        System.out.println();
                        System.out.println();
                        return true;
                    }
                }else{
                    System.out.println("올바른 값을 입력해주세요");
                    System.out.print(">> ");
                }
            }
//            if (channelInput.equals("back")){
//                return false;
//            }
        } catch (SQLException throwables) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public int NewChannelno(){
        Select select = new Select("SELECT COUNT(*) FROM CHANNEL");
        int val = 0;
        try {
            select.Connect();
            select.Execute();
            int columnCnt = select.rsmd.getColumnCount();
            select.rs.next();
            val = Integer.parseInt(select.rs.getString(select.rsmd.getColumnName(1)))+1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }
}
