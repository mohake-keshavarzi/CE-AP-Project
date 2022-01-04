package main.java.org.ce.ap.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import main.java.org.ce.ap.netWorkingParams;

import javax.swing.text.html.HTMLDocument;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponsePackageMaker {
    private JSONObject jsonObject;
    private JSONArray errorParametersArray;
    private JSONArray resultsArray;
    private Map m;
    private final boolean isErrorPackage;
    private boolean errorPackageCreated=false;
    private boolean standardPackageCreated=false;


    public ResponsePackageMaker(boolean isErrorPackage){
        jsonObject= new JSONObject();
        m=new LinkedHashMap();
        this.isErrorPackage=isErrorPackage;
        jsonObject.put(netWorkingParams.ResponsePackage.hasError,isErrorPackage);
    }

    public void createErrorPackage(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes errorType,
                                     netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes errorCode){
        if(!isErrorPackage) throw new IllegalStateException("It is not an error package");
        if(errorPackageCreated) throw new IllegalStateException("error structure has been created before");
        errorParametersArray=new JSONArray();
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorType, errorType);
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorCode, errorCode);
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorParameters,errorParametersArray);
        errorPackageCreated=true;
    }
    public void addErrorParameter(netWorkingParams.ResponsePackage.ErrorPackage.ErrorParametersFields field,Object value){
        if(!isErrorPackage) throw new IllegalStateException("It is not an error package");
        m.put(field,value);
        errorParametersArray.add(m);
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorParameters,errorParametersArray);
    }

    public void creatStandardResponsePackage(){
        if(isErrorPackage) throw new IllegalStateException("It is not a standard package");
        if(standardPackageCreated) throw new IllegalStateException("standard structure has been created before");
        resultsArray=new JSONArray();
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.count,resultsArray.size());
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.results,resultsArray);
        standardPackageCreated=true;
    }

    public void addResult(Object obj){
        if(isErrorPackage) throw new IllegalStateException("It is not a standard package");
        resultsArray.add(obj);
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.count,resultsArray.size());
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.results,resultsArray);

    }

    public String getPackage(){
        return jsonObject.toString();
    }

}
