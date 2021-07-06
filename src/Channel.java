import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Channel {
    private String id;
    private String nickname;
    private String channelNo;

    public String getChannelNo() {
        return channelNo;
    }

    public Channel(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
    public void ShowChannel(){
        Scanner s = new Scanner(System.in);
        Map<Integer, String> channelPair = new HashMap<>();
        System.out.println("------------------------"+nickname+"님의 대화방--------------------------");
        Select select = new Select("SELECT CHANNEL_NO,TO_CHAR(CHANNEL_DATE,'MM/DD HH24:MM'), CASE WHEN CHANNEL_MEMBER1 = '"+id+"' THEN CHANNEL_MEMBER2 WHEN CHANNEL_MEMBER2 = '"+id+"' THEN CHANNEL_MEMBER1 END AS CHATMEM FROM CHANNEL WHERE CHANNEL_NO IN (SELECT DISTINCT CHANNEL_NO FROM CHANNEL WHERE CHANNEL_MEMBER1 = '"+id+"' OR CHANNEL_MEMBER2 = '"+id+"') ORDER BY 2 DESC");
        select.Connect();
        try {
            select.Execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int columnCnt = select.rsmd.getColumnCount();
            String[] channelColumn = new String[3];
            for(int i=0; i<columnCnt; i++){
                channelColumn[i] = select.rsmd.getColumnName(i+1);
            }
            int i = 1;
            while(select.rs.next()){
                channelPair.put(i,select.rs.getString(select.rsmd.getColumnName(1)));
                System.out.println(i+". "+select.rs.getString(select.rsmd.getColumnName(2))+"       "+select.rs.getString(select.rsmd.getColumnName(3))+"님과의 대화");
                i++;
            }
            if(channelPair.isEmpty()){
                System.out.println("대화방이 없습니다.");
                System.out.println();
                System.out.println();
            }
            System.out.println();
            System.out.println();
            System.out.println("입장하실 대화방을 선택하세요               로그아웃은 logout을 입력하세요");
            System.out.println("대화 상대를 선택하려면 0를 입력하세요");
            System.out.println("----------------------------------------------------------------");
            System.out.print(">> ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String channelInput = s.next();
        channelNo = channelPair.get(Integer.parseInt(channelInput));
        select.Close();
    }
}
