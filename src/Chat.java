import java.sql.SQLException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Chat {
    private String channelNo;
    private String id;
    private String inputMsg = "";
    private int msgCount = 0;
    private String nick;
    private String otherId;
    Timer timer = new Timer();

    public Chat(String channelNo, String id, String otherId) {
        this.channelNo = channelNo;
        this.id = id;
        this.otherId = otherId;
    }
    public void ChatStart() throws Exception {
        Scanner s = new Scanner(System.in);
        Select select = new Select("SELECT a.MESSAGE_FROM , a.MESSAGE_TO , a.MESSAGE , a.MESSAGE_DATE , b.MEMBER_NICK FROM MESSAGE a, CMEMBER b WHERE a.MESSAGE_FROM = b.MEMBER_ID AND CHANNEL_NO = '" + channelNo + "' ORDER BY MESSAGE_DATE");
        select.Connect();
        try {
            select.Execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int columnCnt = select.rsmd.getColumnCount();
            String[] channelColumn = new String[5];
            for(int i=0; i<columnCnt; i++){
                channelColumn[i] = select.rsmd.getColumnName(i+1);
            }
            while(select.rs.next()){
                if(select.rs.getString(select.rsmd.getColumnName(1)).equals(id)){
                    System.out.println("                                                  <나>");
                    System.out.println("                                                  "+select.rs.getString(select.rsmd.getColumnName(3)));
                    nick = select.rs.getString(select.rsmd.getColumnName(5));
                } else{
                    System.out.println("<"+select.rs.getString(select.rsmd.getColumnName(5))+">");
                    System.out.println(select.rs.getString(select.rsmd.getColumnName(3)));
                }
                msgCount++;
            }
            System.out.println("종료를 원하시면 exit를 입력하세요");
            select.Close();
            System.out.println("----------------------------------------------------------------");
            System.out.print( ">>" );

        } catch (SQLException throwables) {
        }
        try{
            getInput();
        }catch(Exception e){
            throw new Exception();
        }
//        Scanner s = new Scanner(System.in);
//        스캐너가 3번 실행됨 왜?
        inputMsg = s.nextLine();
        timer.cancel();
        if(inputMsg.equals("exit")){
            throw new Exception();
        }else {
            DBConnect insert = new Insert("INSERT INTO MESSAGE VALUES(" + channelNo + ",'" + id + "','" + otherId + "','" + inputMsg + "',SYSDATE)");
            insert.Connect();
            insert.Execute();
            System.out.println();
            System.out.println("----------------------------------------------------------------");
            new Chat(channelNo, id,otherId).ChatStart(); // 입력 후
        }

    }

    public void getInput() throws Exception{
        timer.schedule( new TimerTask(){
            public void run(){
                while(true) {
                    if (inputMsg.equals("")) { // 입력 안하면 실행하는것 (갱신 구현해야됨)
                        if (msgCount == newChat()) {
                            timer = new Timer();
                            try {
                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            timer.cancel();
                            try {
                                new Chat(channelNo,id,otherId).ChatStart();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                try {
                    getInput();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5*1000 );
//        Scanner s = new Scanner(System.in);
//        inputMsg = s.nextLine();
//        timer.cancel();
//        if(inputMsg.equals("exit")){
//            throw new Exception();
//        }else {
//            DBConnect insert = new Insert("INSERT INTO MESSAGE VALUES(" + channelNo + ",'" + id + "','" + otherId + "','" + inputMsg + "',SYSDATE)");
//            insert.Connect();
//            insert.Execute();
//            new Chat(channelNo, id,otherId).ChatStart(); // 입력 후
//        }
    }
    public int newChat(){
        Select select2 = new Select("SELECT MESSAGE_FROM, MESSAGE_TO,MESSAGE FROM MESSAGE WHERE CHANNEL_NO = '" + channelNo + "' ORDER BY MESSAGE_DATE");
        select2.Connect();
        try {
            select2.Execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int cnt = 0;
        try {
            while(select2.rs.next()) {
                cnt++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cnt;
    }
}
