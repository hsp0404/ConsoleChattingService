import java.util.Scanner;
import java.sql.*;

public class ChatMain {
    public static void main(String[] args) {
//        Select s = new Select("SELECT * FROM CMEMBER");
//        s.Result();

        Sign sign = new Sign();
        sign.SignIn();
        Channel channel = new Channel(sign.getId(),sign.getNickname());
        channel.ShowChannel();

    }
//        System.out.println("----------------------------SIGN IN----------------------------");
}
