package main.java.org.ce.ap.client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import main.java.org.ce.ap.netWorkingParams;

import java.util.HashMap;
import java.util.Map;

public class ResponsePackageParser {

    private JSONObject jsonObject;
    private final boolean hasError;
    private netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes errorType;
    private netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes errorCode;
    private Long resultsCount;
    private JSONArray resultsArray;
    private JSONArray errorParametersArray;
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
    }

    public boolean hasError(){
        return hasError;
    }

    /**
     * if input package was an authentication package parse it
     * @return true if sign in was successful
     */
    public boolean wasSignInSuccessful(){
        HashMap<String,Boolean> results =
                new HashMap<>(
                (Map<String, Boolean>)
                        resultsArray.get(0));
        if(results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed.name())==null)
            throw new IllegalStateException("This package dose not have Sign in results");
        return results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed.name());
    }

    public boolean wasSignUpSuccessful(){
        HashMap<String,Boolean> results =
                new HashMap<>(
                        (Map<String, Boolean>)
                                resultsArray.get(0));
        if(results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated.name())==null)
            throw new IllegalStateException("This package dose not have Sign up results");
        return results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated.name());
    }

    public  boolean wasUsernameDuplicated(){
        HashMap<String,Boolean> results =
                new HashMap<>(
                        (Map<String, Boolean>)
                                resultsArray.get(0));
        if(results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isUsernameDuplicated.name())==null)
            throw new IllegalStateException("This package dose not have Sign up results");
        return results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isUsernameDuplicated.name());

    }

    public ProfileInfo getLoggedInProfileData(){
        HashMap<String,Boolean> BooleanResults =
                new HashMap<>(
                        (Map<String, Boolean>)
                                resultsArray.get(0));
        if(!(BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed.name())!=null || BooleanResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated.name())!=null))
            throw new IllegalStateException("This package dose not have Sign in results");

        HashMap<String,String> StringResults =
                new HashMap<>(
                        (Map<String, String>)
                                resultsArray.get(0));
        String firstname=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInFirstname.name());
        String lastname=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInLastname.name());
        String username=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInUsername.name());
        String bio=StringResults.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.loggedInBio.name());

        return new ProfileInfo(firstname,lastname,username,bio);

    }

    public netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes getErrorType() {
        return errorType;
    }

    public netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes getErrorCode() {
        return errorCode;
    }
}
