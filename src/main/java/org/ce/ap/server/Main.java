package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.NetworkServiceImpl;
import main.java.org.ce.ap.server.impl.ProfilesManagerImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;

public class Main {
    public static void main(String[] args) {
        ProfilesManagerImpl profilesManager=ProfilesManagerImpl.getInstance();
        AuthenticationServiceImpl authenticationService=AuthenticationServiceImpl.getInstance();
        TweetingServiceImpl tweetingService=TweetingServiceImpl.getInstance();
        NetworkServiceImpl networkService= NetworkServiceImpl.getInstance();
        networkService.init(7660);
        while (true){
            networkService.acceptNewClient(profilesManager,authenticationService,tweetingService);
        }


    }
}
