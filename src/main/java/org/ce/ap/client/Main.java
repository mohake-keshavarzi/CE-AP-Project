package main.java.org.ce.ap.client;

public class Main {
    public static void main(String[] args) {
        Network.connectToServer("127.0.0.1",7660);
        Network.sendToServer("Hey");
        System.out.println("Server said :"+ Network.receiveFromServer());
        Network.sendToServer("sdsadsadfs");
        System.out.println("Server said :"+ Network.receiveFromServer());
        Network.sendToServer("Bye");
        System.out.println("Server said :"+ Network.receiveFromServer());
        Network.closeConnection();
    }
}
