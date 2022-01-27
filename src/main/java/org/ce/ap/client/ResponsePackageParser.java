package main.java.org.ce.ap.client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import main.java.org.ce.ap.netWorkingParams;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ResponsePackageParser {

    private JSONObject jsonObject;
    private final boolean hasError;
    private netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes errorType;
    private netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes errorCode;
    private Long resultsCount;
    private JSONArray resultsArray;
    private JSONArray errorParametersArray;
    HashMap<String,String> StringResults;
    HashMap<String,Boolean> BooleanResults;
    HashMap<String,JSONArray> JSONArrayResults;


//    private Map errorParameters;

    /**
     * gets a string and parse it to a standard response package or error package
     * checks weather the package is a standard one or an error one and creates the appropriate package
     * @param inputPackage string of request package
     * @throws ParseException if there was any error parsing package
     */
    public ResponsePackageParser(String inputPackage) throws ParseException {
        Object obj = new JSONParser().parse(inputPackage);
        jsonObject = (JSONObject) obj;

        hasError=(boolean) jsonObject.get(netWorkingParams.ResponsePackage.hasError.name());
//        System.out.println(hasError);
        if(hasError){
            parseErrorPackage(jsonObject);
        }
        else{
            parseStandardPackage(jsonObject);
        }

    }

    private void parseErrorPackage(JSONObject ErrorObj){
        errorType=netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.valueOf((String) ErrorObj.get(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorType.name()));
        errorCode=netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.valueOf((String) ErrorObj.get(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorCode.name()));
        errorParametersArray=(JSONArray) jsonObject.get(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorParameters);
    }

    private void parseStandardPackage(JSONObject object){
        resultsCount=(Long) object.get(netWorkingParams.ResponsePackage.StandardResponsePackage.count.name());
        resultsArray=(JSONArray) object.get(netWorkingParams.ResponsePackage.StandardResponsePackage.results.name());
        StringResults =
                new HashMap<>(
                        (Map<String, String>)
                                resultsArray.get(0));
        BooleanResults =
                new HashMap<>(
                        (Map<String, Boolean>)
                                resultsArray.get(0));
        JSONArrayResults =
                new HashMap<>(
                        (Map<String, JSONArray>)
                                resultsArray.get(0));

    }

    public boolean hasError(){
        return hasError;
    }

    /**
     * if input package was an authentication package parse it
     * @return true if sign in was successful
     */
    public boolean wasSignInSuccessful(){

        if(BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed.name())==null)
            throw new IllegalStateException("This package dose not have Sign in results");
        return BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed.name());
    }

    public boolean wasSignUpSuccessful(){

        if(BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated.name())==null)
            throw new IllegalStateException("This package dose not have Sign up results");
        return BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated.name());
    }

    public  boolean wasUsernameDuplicated(){

        if(BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isUsernameDuplicated.name())==null)
            throw new IllegalStateException("This package dose not have Sign up results");
        return BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isUsernameDuplicated.name());

    }

    public ProfileInfo getLoggedInProfileData(){

        if(!(BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed.name())!=null || BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated.name())!=null))
            throw new IllegalStateException("This package dose not have Sign in results");

        String firstname=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInFirstname.name());
        String lastname=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInLastname.name());
        String username=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInUsername.name());
        String bio=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInBio.name());

        return new ProfileInfo(firstname,lastname,username,bio);

    }

    public boolean wasTweetPublishingSuccessful(){

        if(BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isTweetPostedSuccessfully.name())==null)
            throw new IllegalStateException("This package dose not have tweeting service results");

        return BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isTweetPostedSuccessfully.name());
    }
    public void completeTweetInfoData(TweetInfo tweetInfo){

        if(StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetSubmissionDate.name())==null)
            throw new IllegalStateException("This package dose not have tweeting service results");

          tweetInfo.setPublishingDate(StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetSubmissionDate.name()));
          tweetInfo.setId(StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetId.name()));
    }


    public String getTweetSenderUsername(){
        return StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetSenderUsername.name());
    }

    public ProfileInfo getResultProfile(){
        String firstname,lastname,username,bio;
        firstname=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileFirstname.name());
        lastname=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileLastname.name());
        username=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileUsername.name());
        bio=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.profileBio.name());

        ProfileInfo prf= new ProfileInfo(firstname,lastname,username,bio);
        return prf;
    }
    public boolean hasReTweet(){
        return BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.hasRetweet.name());
    }
    public String getReTweetedTweetId(){
        return StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.reTweetedTweetID.name());
    }
    public String getResultTweetContext(){
        return StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetContext.name());
    }

    public TweetInfo completeResultTweetInfo(TweetInfo tweet){
        tweet.setPublishingDate(StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetSubmissionDate.name()));
        tweet.setId(StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetId.name()));
        JSONArray likers=(JSONArrayResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetLikersUsernamesArray.name()));
        JSONArray reTweetIds=(JSONArrayResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetReTweetersTweetsIdsArray.name()));
        JSONArray reTweetUsernames=(JSONArrayResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetReTweetersUsernamesArray.name()));

        for(Object o:likers){
            tweet.addLiker((String) o);
        }
        for(Object o:reTweetIds){
            tweet.addReTweeterTweetId((String) o);
        }
        for(Object o:reTweetUsernames){
            tweet.addReTweeterUsername((String) o);
        }

        return  tweet;
    }

    public boolean likedSuccessfully(){
        if(hasError){
            if(errorCode== netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.NO_SUCH_A_TWEET_ID){
                throw new NoSuchElementException("Id dose not exists");
            }
        }

        return BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isLikingSuccessful.name());
    }

    public String getResultTweetId(){
        return StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.tweetId.name());
    }
    public netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes getErrorType() {
        return errorType;
    }

    public netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes getErrorCode() {
        return errorCode;
    }


}
