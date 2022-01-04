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
