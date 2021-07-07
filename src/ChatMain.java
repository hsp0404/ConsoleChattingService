public class ChatMain {
    public static void main(String[] args) {
        NewSign:
        while (true) {
            Sign sign = new Sign();
            sign.SignIn();
            NewChannel:
            while(true){
                Channel channel = new Channel(sign.getId(),sign.getNickname());
                String backTo = channel.ShowChannel();
                if (backTo.equals("newChannel")) {
                    continue NewChannel;
                }else if (backTo.equals("newSign")){
                    continue NewSign;
                }else{
                    Chat chat = new Chat(channel.getChannelNo(), sign.getId(), channel.getOtherId());
                    try {
                        chat.ChatStart();
                    } catch (Exception e) {
                        System.out.println("대화방에서 나오셨습니다.");
                        System.out.println();
                        System.out.println();
                    }
                }
            }
        }
    }
}