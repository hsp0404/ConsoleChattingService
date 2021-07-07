import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Channel {
    private String id;
    private String nickname;
    private String channelNo;
    private String otherId;

    public String getChannelNo() {
        return channelNo;
    }

    public Channel(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
    public String ShowChannel(){
        Scanner s = new Scanner(System.in);
        ArrayList<String> others = new ArrayList<>();
//        String[] others = new String[3];
        int channelCnt = 1;
        Map<Integer, String> channelPair = new HashMap<>();
        System.out.println("------------------------"+nickname+"님의 대화방--------------------------");
        Select select = new Select("SELECT a.CNO, a.CDATE, a.OID, b.MEMBER_NICK FROM (SELECT CHANNEL_NO AS CNO, TO_CHAR(CHANNEL_DATE,'MM/DD HH24:MM') AS CDATE, CASE WHEN CHANNEL_MEMBER1 = '"+id+"' THEN CHANNEL_MEMBER2 WHEN CHANNEL_MEMBER2 = '"+id+"' THEN CHANNEL_MEMBER1 END AS OID FROM CHANNEL WHERE CHANNEL_NO IN (SELECT DISTINCT CHANNEL_NO FROM CHANNEL WHERE CHANNEL_MEMBER1 = '"+id+"' OR CHANNEL_MEMBER2 = '"+id+"') ORDER BY 2 DESC)a, CMEMBER b WHERE a.OID = b.MEMBER_ID ");
        select.Connect();
        try {
            select.Execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
//            int columnCnt = select.rsmd.getColumnCount();
            while(select.rs.next()){
                channelPair.put(channelCnt,select.rs.getString(select.rsmd.getColumnName(1)));
                System.out.println(channelCnt+". "+select.rs.getString(select.rsmd.getColumnName(2))+"       "+select.rs.getString(select.rsmd.getColumnName(4))+"님과의 대화");
//                others[channelCnt-1] = select.rs.getString(select.rsmd.getColumnName(3));
                others.add(select.rs.getString(select.rsmd.getColumnName(3)));
                channelCnt++;
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
        while (true) {
            String channelInput = s.next();
            if (channelInput.matches("^[0-"+(channelCnt-1)+"]*$")||channelInput.equals("logout")) {
                if(channelInput.equals("0")){
                    SearchMember searchMember = new SearchMember(id);
                    if(searchMember.MemberList()){
                        channelNo = String.valueOf(searchMember.newChannelno);
                        otherId = searchMember.otherId;
                        return "con";
                    }else{
                        return "newChannel";
                    }
                }else if(channelInput.equals("logout")){
                       return "newSign";
                }
                else {
                    channelNo = channelPair.get(Integer.parseInt(channelInput));
//                    otherId = others[Integer.parseInt(channelInput) - 1];
                    otherId = others.get(Integer.parseInt(channelInput)-1);
                    select.Close();
                    return "con";
                }
            } else {
                System.out.println("올바른 값을 입력해주세요");
                System.out.print(">> ");
            }
        }
    }

    public String getOtherId() {
        return otherId;
    }
}
