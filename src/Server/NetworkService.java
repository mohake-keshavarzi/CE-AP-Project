package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NetworkService {

    private static NetworkService INSTANCE;
    private ServerSocket welcomeSocket;
    private ArrayList<ClientHandler> clientHandlers;
    private ExecutorService pool;
    private NetworkService(){
        clientHandlers=new ArrayList<>();
    }

    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    protected static NetworkService getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new NetworkService();
        }
        return INSTANCE;
    }

    /**
     * if there is no welcoming socket ,Creates one on the given port number
     * @param welcomePortNum number of the port that we want open socket on it
     */
    protected void init(int welcomePortNum) {
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
    protected void acceptNewClient(){
        try {
            Socket connectionSocket = welcomeSocket.accept();
            clientHandlers.add(new ClientHandler(connectionSocket, UUID.randomUUID().toString()));
            pool.execute(clientHandlers.get(clientHandlers.size()-1));
            System.out.println("New Client accepted. Num of clients:"+clientHandlers.size());
        }
        catch (IOException ex){
            System.err.println("Error in accepting new client "+ex);
        }
    }

    /**
     * closes the welcoming socket and will not accept any more client
     */
    protected void closeWelcomeSocket(){
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
    protected int getNumberOfClients(){
        return clientHandlers.size();
    }

    /**
     * removes given client handler from the arraylist
     * @param clientHandler given client handler to be removed from arraylist
     */
    protected void removeClient(ClientHandler clientHandler){
        clientHandlers.remove(clientHandler);
    }

}
