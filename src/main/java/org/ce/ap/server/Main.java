package main.java.org.ce.ap.server;

import main.java.org.ce.ap.server.impl.*;

public class Main {
    public static void main(String[] args) {
        ProfilesManagerImpl profilesManager=ProfilesManagerImpl.getInstance();
        AuthenticationServiceImpl authenticationService=AuthenticationServiceImpl.getInstance();
        TweetingServiceImpl tweetingService=TweetingServiceImpl.getInstance();
        NetworkServiceImpl networkService= NetworkServiceImpl.getInstance();
        ObserverServiceImpl observerService=ObserverServiceImpl.getInstance();
        TimeLineServiceImpl timeLineService=TimeLineServiceImpl.getInstance();
        networkService.init(7660);
        while (true){
            networkService.acceptNewClient(profilesManager,authenticationService
                    ,tweetingService,observerService,timeLineService);
        }


    }
}
