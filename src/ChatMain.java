import java.util.Scanner;
import java.sql.*;

public class ChatMain {
    public static void main(String[] args) {
//        Select s = new Select("SELECT * FROM CMEMBER");
//        s.Result();

        Sign sign = new Sign();
        sign.SignIn();
        Channel channel = new Channel(sign.getId(),sign.getNickname());
        while(true){
            channel.ShowChannel();
            Chat chat = new Chat(channel.getChannelNo(), sign.getId(), channel.getOtherId());
            try {
                chat.ChatStart();
            } catch (Exception e) {}
        }
    }
//        System.out.println("----------------------------SIGN IN----------------------------");
}
