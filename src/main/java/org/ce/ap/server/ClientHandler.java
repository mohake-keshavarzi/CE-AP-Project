package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.NetworkServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket connectionSocket;
    private String clientId;
    private boolean stopFlag=false;

    /**
     * makes a new client handler
     * @param connectionSocket the socket which client will communicate with
     * @param id the id of this client
     */
    public ClientHandler(Socket connectionSocket, String id) {
        this.connectionSocket = connectionSocket;
        this.clientId=id;
        System.out.println("New client. id="+this.clientId);
    }

    /**
     * As each client should run in a seperated thread we should implement Runnable and override run function
     * Here we get input and output stream then we will send and receive data in the appropriate way
     */
    @Override
    public void run(){
        String response;
        try {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[2048];
            while (!stopFlag){
                int read=in.read(buffer);
                String input=new String(buffer,0,read);
//                System.out.println("Client:"+clientId+" Says:"+input);
                RequestToResponse requestToResponse=new RequestToResponse();
//                switch (input) {
//                    case "Hey" -> response = "Hey Back";
//                    case "Bye" -> {
//                        response = "GoodBye";
//                        stopFlag = true;
//                    }
//                    default -> response = "What?";
//                }
                response=requestToResponse.createResponse(input);
                out.write((response.getBytes()));
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            try {
                connectionSocket.close();
            }catch (IOException ex){
                System.err.println("Error in closing connection socket "+ ex);
            }
                NetworkServiceImpl.getInstance().removeClient(this);
        }

    }
}
