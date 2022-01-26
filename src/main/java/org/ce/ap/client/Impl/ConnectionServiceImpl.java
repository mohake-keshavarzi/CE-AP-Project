package main.java.org.ce.ap.client.Impl;

import main.java.org.ce.ap.client.ConnectionService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public  class ConnectionServiceImpl implements ConnectionService {
    private static String serverIP;
    private static int serverPort;
    private static Socket socket;
    private static OutputStream out;
    private static InputStream in;
    private static int timeOut;
    static private ConnectionServiceImpl INSTANCE;

    private ConnectionServiceImpl(){

    }

    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */

     public static ConnectionServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new ConnectionServiceImpl();
        }
        return INSTANCE;
    }
    public void connectToServer(String IP,int port){
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
    public void sendToServer(String input) throws IllegalStateException{
        if(socket.isConnected()) {
            try {
                out.write(input.getBytes());
                System.out.println("This package sent:");
                System.out.println(input);
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
    public String receiveFromServer() throws IllegalStateException{
        byte[] buffer = new byte[2048];
        if(socket.isConnected()) {
            try {
                int read = in.read(buffer);
                String result=new String(buffer, 0, read);
                System.out.println("This package received");
                System.out.println(result);
                return result;
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
    public void closeConnection() throws IllegalStateException{
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
