package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.Impl.ConsoleViewServiceImpl;
import main.java.org.ce.ap.server.Profile;
import main.java.org.ce.ap.server.Tweet;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        ConnectionServiceImpl network= ConnectionServiceImpl.getInstance();
//        network.connectToServer("127.0.0.1",7660);
//        network.sendToServer("Hey");
//        System.out.println("Server said :"+ network.receiveFromServer());
//        network.sendToServer("sdsadsadfs");
//        System.out.println("Server said :"+ network.receiveFromServer());
//        network.sendToServer("Bye");
//        System.out.println("Server said :"+ network.receiveFromServer());
//        network.closeConnection();

        ConsoleViewServiceImpl console = ConsoleViewServiceImpl.getInstance();
        console.printHeading("Login");
        console.printNormal("Enter username");
        String nn=null;
        console.printNormal(nn);
        Profile sender;
        try {
            sender = new Profile("Ahmad", "Rezai", "AR", "sss");
            Tweet t=new Tweet("dgfddgdgdgd gdfgdgfd dgdfgdf dfgffdgfg dfgdfd dg fddgfdgfd dfgdgfd fgdgfgdhfg dfgdgfdgfdf fgdgdrdgf g12534 lkhkjg drwfdsd gtghjghkh ytdfdsd sddfgsfdgfd fhbgfhghfy yjgyjkuu khghjfg szdasdawed erdfsd sfesdf sdfdsefaefdsgf 123456789 fddgdf qewry uyjhgjur sdvf",sender);
            Tweet tt=new Tweet("Hey retweet",sender,t);
            tt.addLike(sender);
            console.printTweet(t);

            console.printTweet(tt);

        }catch (Exception e){
            console.printError(e.toString());
        }

//        CommandParserServiceImpl commander=CommandParserServiceImpl.getInstance();
//        commander.runAuthenticationInterface();

        RequestPackageMaker packageMaker=new RequestPackageMaker(netWorkingParams.AUTHENTICATION_REQUEST,"Hello");
        packageMaker.putParameter("username",12);
        ArrayList<String > in= new ArrayList<>();
        in.add("javad");
        in.add("ted");
        packageMaker.putParameterArray("Likes",in);

        System.out.println(packageMaker);
    }


}
