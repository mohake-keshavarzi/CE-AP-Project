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
    private int resultsCount;
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

        hasError=(boolean) jsonObject.get(netWorkingParams.ResponsePackage.hasError);
        if(hasError){
            parseErrorPackage(jsonObject);
        }
        else{
            parseStandardPackage(jsonObject);
        }

    }

    private void parseErrorPackage(JSONObject ErrorObj){
        errorType=(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes) ErrorObj.get(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorType);
        errorCode=(netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes) ErrorObj.get(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorCode);
        errorParametersArray=(JSONArray) jsonObject.get(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorParameters);
    }

    private void parseStandardPackage(JSONObject object){
        resultsCount=(int) object.get(netWorkingParams.ResponsePackage.StandardResponsePackage.count);
        resultsArray=(JSONArray) object.get(netWorkingParams.ResponsePackage.StandardResponsePackage.results);
    }

    public boolean hasError(){
        return hasError;
    }

    /**
     * if input package was an authentication package parse it and returns needed parameters
     * @return a hashmap which shows weather the authentication was successful
     */
    public HashMap<netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields,Boolean> paresAuthenticationResponse(){
        HashMap<netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields,Boolean> results =
                new HashMap<>(
                (Map<netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields, Boolean>)
                        resultsArray.get(0));
        if(results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed)==null
        || results.get(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated)==null)
            throw new IllegalStateException("This package dose not have authentication results");
        return results;
    }
}
