package main.java.org.ce.ap.server;

/**
 * manages and stores the clients
 */
public interface ClientsManager {

    void addClient(ClientHandler handler);

    void removeClient(ClientHandler handler);

}
