package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket connectionSocket;
    private String clientId;
    private boolean stopFlag=false;

    public ClientHandler(Socket connectionSocket, String id) {
        this.connectionSocket = connectionSocket;
        this.clientId=id;
        System.out.println("New client. id="+this.clientId);
    }

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
                System.out.println("Client:"+clientId+" Says:"+input);
                switch (input) {
                    case "Hey" -> response = "Hey Back";
                    case "Bye" -> {
                        response = "GoodBye";
                        stopFlag = true;
                    }
                    default -> response = "What?";
                }
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
                System.err.println(ex);
            }
                NetworkService.getInstance().removeClient(this);
        }

    }
}
