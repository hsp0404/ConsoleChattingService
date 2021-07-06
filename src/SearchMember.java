import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchMember {
    private String id;
    private String channelInput;
    public SearchMember(String id) {
        this.id = id;
    }
    public void memberList(){
        Scanner s = new Scanner(System.in);
        String[] others = new String[2];
        Map<Integer, String> channelPair = new HashMap<>();
        System.out.println("------------------- 대화할 상대를 고르세요 ------------------------");
        Select select = new Select("SELECT * FROM CMEMBER WHERE MEMBER_ID !='"+id+"'");
        select.Connect();
        try {
            select.Execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int columnCnt = select.rsmd.getColumnCount();

            int i = 1;
            while(select.rs.next()){
                System.out.println(i+". "+select.rs.getString(select.rsmd.getColumnName(3))+" 님과 대화하기");
//                others[i-1] = select.rs.getString(select.rsmd.getColumnName(3));
                i++;
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
            channelInput = s.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
