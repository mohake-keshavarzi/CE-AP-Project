package main.java.org.ce.ap.server;

import main.java.org.ce.ap.netWorkingParams;
import main.java.org.ce.ap.server.impl.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable{
    private Socket connectionSocket;
    private String clientId;
    private String clientLocalAddress;
    private boolean stopFlag=false;
    private RequestPackageParser requestPackageParser;
    private ResponsePackageMaker responsePackageMaker;
    private ProfilesManagerImpl profilesManager;
    private AuthenticationServiceImpl authenticationService;
    private TweetingServiceImpl tweetingService;
    private ObserverServiceImpl observerService;
    private TimeLineServiceImpl timeLineService;
    /**
     * makes a new client handler
     * @param connectionSocket the socket which client will communicate with
     * @param id the id of this client
     */
    public ClientHandler(Socket connectionSocket, String id, ProfilesManagerImpl prf,
                         AuthenticationServiceImpl aut, TweetingServiceImpl twtS,
                         ObserverServiceImpl obs,TimeLineServiceImpl tls) {
        this.connectionSocket = connectionSocket;
        this.clientLocalAddress=connectionSocket.getLocalAddress().toString();
        this.clientId=id;
        this.profilesManager=prf;
        this.authenticationService=aut;
        this.tweetingService=twtS;
        this.observerService=obs;
        this.timeLineService=tls;
        System.out.println("New client. id="+this.clientId);
    }

    /**
     * As each client should run in a seperated thread we should implement Runnable and override run function
     * Here we get input and output stream then we will send and receive data in the appropriate way
     * with the help of client controller
     */
    @Override
    public void run(){
        String response;
        try {
            OutputStream out = connectionSocket.getOutputStream();
            InputStream in = connectionSocket.getInputStream();
            byte[] buffer = new byte[2048];
            ClientController clientController =new ClientController(profilesManager,authenticationService,tweetingService,observerService,timeLineService);
            while (!stopFlag){

                int read=in.read(buffer);
                String input=new String(buffer,0,read);
//                System.out.println("Client:"+clientId+" Says:"+input);

//                switch (input) {
//                    case "Hey" -> response = "Hey Back";
//                    case "Bye" -> {
//                        response = "GoodBye";
//                        stopFlag = true;
//                    }
//                    default -> response = "What?";
//                }
                try {
                    requestPackageParser=new RequestPackageParser(input);
                    response= clientController.doTaskAndCreateResponse(requestPackageParser);
                }catch (ParseException ex){
                    System.err.println(ex+"unable to parse request");
                    responsePackageMaker =new ResponsePackageMaker(true);
                    responsePackageMaker.createErrorPackage(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.PACKAGE_ERROR
                            ,netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.UNABLE_TO_PARSE_PACKAGE);
                    response=responsePackageMaker.getPackage();
                }
                out.write((response.getBytes()));
            }
        }
        catch (IOException ex){
            System.out.println("Connection lost with "+clientLocalAddress+" ID:"+clientId+"    "+ex);
            //ex.printStackTrace();

        }
        finally {

            try {
                connectionSocket.close();
            }catch (IOException ex){
                System.err.println("Error in closing connection socket "+ ex);
            }
                NetworkServiceImpl.getInstance().removeClient(this);
        }

    }
}
