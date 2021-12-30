package main.java.org.ce.ap.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public abstract class Network {
    private static String serverIP;
    private static int serverPort;
    private static Socket socket;
    private static OutputStream out;
    private static InputStream in;
    private static int timeOut;

    /**
     * gets the ip and port of welcoming socket of the server and trys to connect to it
     * @param IP ip of server's welcoming socket
     * @param port port of server's welcoming socket
     */
    protected static void connectToServer(String IP,int port){
        try{
            socket=new Socket(IP,port);
            out=socket.getOutputStream();
            in=socket.getInputStream();
            System.out.println("Connected to the server");

        }
        catch (IOException ex){
            System.err.println("Problem in connection: "+ex);
        }

    }

    /**
     * sends the given string to the server
     * @param input given message as a String
     * @throws IllegalStateException if there is no connection to the server
     */
    protected static void sendToServer(String input) throws IllegalStateException{
        if(socket.isConnected()) {
            try {
                out.write(input.getBytes());
            } catch (IOException ex) {
                System.err.println("An error occurred during sending data: " + ex);
            }
        }
        else
            throw new IllegalStateException("Socket is not connected");
    }

    /**
     * waits until to receive a message from server
     * @return returns the message as a String
     * @throws IllegalStateException if there is no connection
     */
    protected static String receiveFromServer() throws IllegalStateException{
        byte[] buffer = new byte[2048];
        if(socket.isConnected()) {
            try {
                int read = in.read(buffer);
                return new String(buffer, 0, read);
            } catch (IOException ex) {
                System.err.println("An error occurred during sending data: " + ex);
                return null;
            }
        }
        else
            throw new IllegalStateException("Socket is not connected");

    }

    /**
     * closes the socket
     * @throws IllegalStateException if there is no socket
     */
    protected static void closeConnection() throws IllegalStateException{
        if(socket!=null) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.err.println("Something went wrong while closing connection " + ex);
            }
        }
        else
            throw new IllegalStateException("No socket exists");
    }




}
