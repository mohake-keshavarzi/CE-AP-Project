package main.java.org.ce.ap.server;


import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.ProfilesManagerImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.netWorkingParams;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClientController {
    private String inputString;
    private Profile profile;
    private RequestPackageParser inputParser;
    private ResponsePackageMaker responsePackageMaker;
    private ProfilesManagerImpl profilesManager;
    private AuthenticationServiceImpl authenticationService;
    private TweetingServiceImpl tweetingService;

    public ClientController(ProfilesManagerImpl profilesManager, AuthenticationServiceImpl authenticationService,TweetingServiceImpl tweetingService){
        this.profilesManager=profilesManager;
        this.authenticationService=authenticationService;
        this.tweetingService=tweetingService;

    }
    public String doTaskAndCreateResponse(RequestPackageParser input){
        this.inputParser=input;
        if (inputParser.getMethod().equals(netWorkingParams.RequestPackage.Methods.SIGN_IN_REQUEST)){
            responsePackageMaker =doSignIn();
        }else if(inputParser.getMethod().equals(netWorkingParams.RequestPackage.Methods.SIGN_UP_REQUEST)){
            responsePackageMaker =doSignUp();
        }else if(inputParser.getMethod().equals(netWorkingParams.RequestPackage.Methods.SEND_TWEET_REQUEST)){
            responsePackageMaker = doSendTweet();
        }
        return responsePackageMaker.getPackage();
    }

    private ResponsePackageMaker doSignIn(){
        String username=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.username);
        String password=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.password);
        ResponsePackageMaker responsePackageMaker;
        Map m=new LinkedHashMap();
        try {
            profile=authenticationService.login(username,password);
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed,true);
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInFirstname,profile.getFirstName());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInLastname,profile.getLastName());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInUsername,profile.getUsername());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInBio,profile.getBio());

            responsePackageMaker.addResult(m);
        }catch (NoSuchAlgorithmException ex){
            responsePackageMaker=makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.SIGN_IN_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.INTERNAL_SERVER_ERROR);
            responsePackageMaker.addErrorParameter(ex.toString());
        }catch (IllegalArgumentException ex){
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed,false);
            responsePackageMaker.addResult(m);
        }

        return responsePackageMaker;
    }

    private ResponsePackageMaker doSignUp(){
        String username=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.username);
        String password=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.password);
        String firstname=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.firstname);
        String lastname=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.lastname);
        ResponsePackageMaker responsePackageMaker;
        Map m=new LinkedHashMap();
        try {
            profile=authenticationService.creatProfile(firstname,lastname,username,password);
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated,true);
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInFirstname,profile.getFirstName());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInLastname,profile.getLastName());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInUsername,profile.getUsername());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInBio,profile.getBio());

            responsePackageMaker.addResult(m);
        }catch (NoSuchAlgorithmException ex){
            responsePackageMaker=makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.SIGN_IN_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.INTERNAL_SERVER_ERROR);
            responsePackageMaker.addErrorParameter(ex.toString());
        }catch (IllegalArgumentException ex){
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated,false);
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isUsernameDuplicated,true);
            responsePackageMaker.addResult(m);
        }
        return responsePackageMaker;
    }

    private ResponsePackageMaker doSendTweet(){
        boolean isRetweet = (boolean) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.isRetweet);
        String context=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.tweetContext);
        ResponsePackageMaker responsePackageMaker;
        Map m=new LinkedHashMap();
        try {
            Tweet tweet=tweetingService.publishTweet(profile,context);
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isTweetPostedSuccessfully,true);
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetSubmissionDate,tweet.getSubmissionDate().toString());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetId,tweet.getId());
            responsePackageMaker.addResult(m);
        }catch (IllegalArgumentException ex){
            responsePackageMaker=makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.TWEETING_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.MORE_THAN_256_CHARS);
            responsePackageMaker.addErrorParameter(ex.toString());
        }catch (NullPointerException ex){
            responsePackageMaker=makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.TWEETING_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.NULL_CONTEXT);
            responsePackageMaker.addErrorParameter(ex.toString());
        }

        return responsePackageMaker;
    }


    private ResponsePackageMaker makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes errorType
            ,netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes errorCode){
        ResponsePackageMaker errorMaker = new ResponsePackageMaker(true);
        errorMaker.createErrorPackage(errorType,errorCode);
        return errorMaker;
    }
    private ResponsePackageMaker makeStandardResponsePackageMaker(){
        ResponsePackageMaker standardPackageMaker = new ResponsePackageMaker(false);
        standardPackageMaker.creatStandardResponsePackage();
        return standardPackageMaker;
    }

}
