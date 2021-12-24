package Server;

import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket connectionSocket;
    private String clientId;

    public ClientHandler(Socket connectionSocket, String id) {
        this.connectionSocket = connectionSocket;
        this.clientId=id;
        System.out.println("New client. id="+this.clientId);
    }

    @Override
    public void run(){


    }
}
