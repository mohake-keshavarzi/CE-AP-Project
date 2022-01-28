package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.ClientHandler;
import main.java.org.ce.ap.server.NetworkService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkServiceImpl implements NetworkService{
    private static NetworkServiceImpl INSTANCE;
    private ServerSocket welcomeSocket;
    private ArrayList<ClientHandler> clientHandlers;
    private ExecutorService pool;
    private int clientsNumberLimit=4;
    private NetworkServiceImpl(){
        clientHandlers=new ArrayList<>(clientsNumberLimit);
    }

    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static NetworkServiceImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new NetworkServiceImpl();
        }
        return INSTANCE;
    }

    /**
     * if there is no welcoming socket ,Creates one on the given port number
     * @param welcomePortNum number of the port that we want open socket on it
     */
    public void init(int welcomePortNum) {
        if (welcomeSocket == null) {
            try {
                welcomeSocket = new ServerSocket(welcomePortNum);
                pool= Executors.newCachedThreadPool();
                System.out.println("Welcome Socket opened on port " + welcomePortNum);
                System.out.println("Waiting for clients...");
            } catch (IOException ex) {
                System.err.println("We have problem on making Welcome port: "+ex);
            }
        }
        else {
            System.out.println("A welcome socket already exist on port "+this.welcomeSocket.getLocalPort());
        }
    }

    /**
     * if there is a welcoming socket
     * waits for a new client and accept its connection
     * after that creates a new socket and new ClientHandler and runs it in a thread pool
     */
    public void acceptNewClient(ProfilesManagerImpl prf,AuthenticationServiceImpl aut,
                                TweetingServiceImpl twtS,ObserverServiceImpl obs,TimeLineServiceImpl tls){
            try {
                Socket connectionSocket = welcomeSocket.accept();
                if(clientHandlers.size()<clientsNumberLimit) {
                    clientHandlers.add(new ClientHandler(connectionSocket, UUID.randomUUID().toString(), prf, aut,twtS,obs,tls));
                    pool.execute(clientHandlers.get(clientHandlers.size() - 1));
                    System.out.println("New Client accepted. Num of clients:" + clientHandlers.size());
                }
                else {
                    connectionSocket.close();
                    System.out.println("Clients number limit reached");
                }
            } catch (IOException ex) {
                System.err.println("Error in accepting new client " + ex);
            }
    }

    /**
     * closes the welcoming socket and will not accept any more client
     */
    public void closeWelcomeSocket(){
        if(welcomeSocket!=null){
            try {
                welcomeSocket.close();
                pool.shutdown();
                System.out.println("Welcoming Socket closed. No more client will be accepted");
            }
            catch (IOException ex){
                System.err.println("Error in closing welcoming socket "+ex);
            }
        }
    }

    /**
     * returns number of stored clients in the client handler arraylist
     * @return number of stored clients
     */
    public int getNumberOfClients(){
        return clientHandlers.size();
    }

    /**
     * removes given client handler from the arraylist
     * @param clientHandler given client handler to be removed from arraylist
     */
    public void removeClient(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
    }

    /**
     * checks weather the limit number of client has been reached
     * @return true if the list is full
     */
    public boolean isClientsListFull(){
        return clientHandlers.size()==clientsNumberLimit;
    }
}
