package main.java.org.ce.ap.server;
import main.java.org.ce.ap.server.impl.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public interface NetworkService {



    /**
     * if there is no welcoming socket ,Creates one on the given port number
     * @param welcomePortNum number of the port that we want open socket on it
     */
     void init(int welcomePortNum) ;

    /**
     * if there is a welcoming socket
     * waits for a new client and accept its connection
     * after that creates a new socket and new ClientHandler and runs it in a thread pool
     */
     void acceptNewClient(ProfilesManagerImpl prf, AuthenticationServiceImpl aut, TweetingServiceImpl twtS, ObserverServiceImpl obs, TimeLineServiceImpl tls);


    /**
     * closes the welcoming socket and will not accept any more client
     */
     void closeWelcomeSocket();


    /**
     * returns number of stored clients in the client handler arraylist
     * @return number of stored clients
     */
     int getNumberOfClients();

    /**
     * removes given client handler from the arraylist
     * @param clientHandler given client handler to be removed from arraylist
     */
     void removeClient(ClientHandler clientHandler);

}
