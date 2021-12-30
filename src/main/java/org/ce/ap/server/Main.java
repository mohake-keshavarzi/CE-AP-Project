package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.NetworkServiceImpl;

public class Main {
    public static void main(String[] args) {
        NetworkServiceImpl networkService= NetworkServiceImpl.getInstance();
        networkService.init(7660);
        networkService.acceptNewClient();
        networkService.acceptNewClient();
        networkService.acceptNewClient();
        networkService.closeWelcomeSocket();
    }
}
