package main.java.org.ce.ap.client;

import main.java.org.ce.ap.client.Impl.ConnectionServiceImpl;

public class Main {
    public static void main(String[] args) {
        ConnectionServiceImpl network= ConnectionServiceImpl.getInstance();
        network.connectToServer("127.0.0.1",7660);
        network.sendToServer("Hey");
        System.out.println("Server said :"+ network.receiveFromServer());
        network.sendToServer("sdsadsadfs");
        System.out.println("Server said :"+ network.receiveFromServer());
        network.sendToServer("Bye");
        System.out.println("Server said :"+ network.receiveFromServer());
        network.closeConnection();
    }
}
