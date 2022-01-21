package main.java.org.ce.ap.server.impl;

import main.java.org.ce.ap.server.ClientHandler;
import main.java.org.ce.ap.server.ClientsManager;

import java.util.ArrayList;

public class ClientsManagerImpl implements ClientsManager {
    private final int limitNumber=4;
    private final ArrayList<ClientHandler> Clients;
    private static ClientsManagerImpl INSTANCE;

    public ClientsManagerImpl(){
        Clients=new ArrayList<>(limitNumber);
    }
    /**
     * for implanting  the singleton design.
     * if an instance of this class have been made returns that else makes a new one and returns that
     * @return current instance or newly created instance of this class
     */
    public static ClientsManagerImpl getInstance(){
        if(INSTANCE==null) {
            INSTANCE = new ClientsManagerImpl();
        }
        return INSTANCE;
    }

    @Override
    public void addClient(ClientHandler handler) {
        if(Clients.contains(handler))
            throw new IllegalArgumentException("trying to add a same handler for two times");
        if(Clients.size()>limitNumber)
            throw new IllegalStateException("Clients list is full");
        Clients.add(handler);
    }

    @Override
    public void removeClient(ClientHandler handler) {
        if(!Clients.contains(handler))
            throw new IllegalArgumentException("No such a client to remove");
        Clients.remove(handler);
    }
}
