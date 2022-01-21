package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.NetworkServiceImpl;
import main.java.org.ce.ap.server.impl.ProfilesManagerImpl;

public class Main {
    public static void main(String[] args) {
        ProfilesManagerImpl profilesManager=ProfilesManagerImpl.getInstance();
        AuthenticationServiceImpl authenticationService=AuthenticationServiceImpl.getInstance();
        NetworkServiceImpl networkService= NetworkServiceImpl.getInstance();
        networkService.init(7660);
        networkService.acceptNewClient(profilesManager,authenticationService);

        networkService.closeWelcomeSocket();
    }
}
