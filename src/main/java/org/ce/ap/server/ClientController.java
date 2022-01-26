package main.java.org.ce.ap.server;


import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.ProfilesManagerImpl;
import main.java.org.ce.ap.server.impl.TweetingServiceImpl;
import main.java.org.ce.ap.netWorkingParams;
import org.json.simple.JSONArray;

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
        }else if(inputParser.getMethod().equals(netWorkingParams.RequestPackage.Methods.GET_TWEET_BY_ID_REQUEST)){
            responsePackageMaker= getTweetById();
        }else if(inputParser.getMethod().equals(netWorkingParams.RequestPackage.Methods.GET_PROFILE_BY_USERNAME)){
            responsePackageMaker= getProfileByUsername();
        }else if(inputParser.getMethod().equals(netWorkingParams.RequestPackage.Methods.SEND_RETWEET_REQUEST)){
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
            Tweet tweet;
            if(!isRetweet)
                tweet=tweetingService.publishTweet(profile,context);
            else {
                String reTweetId=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.reTweetId);
                Tweet targetReTweet=tweetingService.getTweetById(reTweetId);
                tweet=new Tweet(context,profile,targetReTweet);
                tweetingService.publishTweet(tweet);
            }
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

    private ResponsePackageMaker getTweetById(){
        String id=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.tweetId);
        Tweet target=tweetingService.getTweetById(id);
        ResponsePackageMaker responsePackageMaker;
        Map m=new LinkedHashMap();

        if(target==null){
            responsePackageMaker=makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.TWEETING_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.NO_SUCH_A_TWEET_ID);
//            responsePackageMaker.addErrorParameter(ex.toString());
        }else {
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetContext,target.getContext());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetSubmissionDate,target.getSubmissionDate().toString());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetSenderUsername,target.getSender().getUsername());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetId,target.getId());
            if(target.containsReTweet()) {
                m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.reTweetedTweetID, target.getReTweetedTweet().getId());
                m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.hasRetweet,true);
            }else
                m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.hasRetweet,false);

            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetNumberOfLikes,target.numOfLikes());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetNumberOfRetweets,target.numOfRetweeters());

            JSONArray likers=new JSONArray();
            for(Profile liker: target.getLikers()){
                likers.add(liker.getUsername());
            }
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetLikersUsernamesArray,likers);

            JSONArray reTweetsIds=new JSONArray();
            JSONArray reTweetersUsernames=new JSONArray();
            for( Tweet t: target.getTweetsWhomHaveRetweetedThisTweet()){
                reTweetsIds.add(t.getId());
                reTweetersUsernames.add(t.getSender().getUsername());
            }
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetReTweetersTweetsIdsArray,reTweetsIds);
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetReTweetersUsernamesArray,reTweetersUsernames);

            responsePackageMaker.addResult(m);

        }
        return responsePackageMaker;
    }

    private ResponsePackageMaker getProfileByUsername() {
        String username = (String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.username);
        Profile targetProfile = profilesManager.getProfileByUserName(username);
        ResponsePackageMaker responsePackageMaker;
        Map m = new LinkedHashMap();

        if (targetProfile == null) {
            responsePackageMaker = makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.PROFILE_DATA_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.NO_SUCH_A_USERNAME);
//            responsePackageMaker.addErrorParameter(ex.toString());
        } else {
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileUsername,targetProfile.getUsername());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileFirstname,targetProfile.getFirstName());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileLastname,targetProfile.getLastName());
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileBio,targetProfile.getBio());
            responsePackageMaker.addResult(m);
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
