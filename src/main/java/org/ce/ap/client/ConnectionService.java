package main.java.org.ce.ap.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public interface ConnectionService {

    /**
     * gets the ip and port of welcoming socket of the server and trys to connect to it
     * @param IP ip of server's welcoming socket
     * @param port port of server's welcoming socket
     */
    void connectToServer(String IP,int port);


    /**
     * sends the given string to the server
     * @param input given message as a String
     * @throws IllegalStateException if there is no connection to the server
     */
      void sendToServer(String input) throws IllegalStateException;

    /**
     * waits until to receive a message from server
     * @return returns the message as a String
     * @throws IllegalStateException if there is no connection
     */
      String receiveFromServer() throws IllegalStateException;

    /**
     * closes the socket
     * @throws IllegalStateException if there is no socket
     */
     void closeConnection() throws IllegalStateException;




}
