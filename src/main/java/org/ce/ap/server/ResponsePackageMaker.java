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

    /**
     * initializes the response package
     * @param isErrorPackage if the response package should be an error package one
     */
    public ResponsePackageMaker(boolean isErrorPackage){
        jsonObject= new JSONObject();
        m=new LinkedHashMap();
        this.isErrorPackage=isErrorPackage;
        jsonObject.put(netWorkingParams.ResponsePackage.hasError,isErrorPackage);
    }

    /**
     * if package is an error one, this method will initialize the error package
     * @param errorType type of error
     * @param errorCode code of error
     */
    public void createErrorPackage(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes errorType,
                                     netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes errorCode){
        if(!isErrorPackage) throw new IllegalStateException("It is not an error package");
        if(errorPackageCreated) throw new IllegalStateException("error structure has been created before");
        errorParametersArray=new JSONArray();
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorType, errorType.name());
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorCode, errorCode.name());
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorParameters,errorParametersArray);
        errorPackageCreated=true;
    }

    /**
     * if package has been initialized as an error one, this package will add error parameters to it
     * @param value value to be added
     */
    public void addErrorParameter(Object value){
        if(!isErrorPackage) throw new IllegalStateException("It is not an error package");
        errorParametersArray.add(value);
        jsonObject.put(netWorkingParams.ResponsePackage.ErrorPackage.Fields.errorParameters,errorParametersArray);
    }

    /**
     * if package is a standard one, this method will initialize it as a standard package
     */
    public void creatStandardResponsePackage(){
        if(isErrorPackage) throw new IllegalStateException("It is not a standard package");
        if(standardPackageCreated) throw new IllegalStateException("standard structure has been created before");
        resultsArray=new JSONArray();
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.count,resultsArray.size());
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.results,resultsArray);
        standardPackageCreated=true;
    }

    private void addResult(Object obj){
        if(isErrorPackage) throw new IllegalStateException("It is not a standard package");
        resultsArray.add(obj);
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.count,resultsArray.size());
        jsonObject.put(netWorkingParams.ResponsePackage.StandardResponsePackage.results,resultsArray);

    }
    /**
     * if package is s standard one, it will add a result Map to array of results
     * @param m a hashmap which contains parameters names and values
     */

    public void addResult(Map m){
        addResult((Object) m);
    }

    /**
     * returns the generated package as a String
     * @return a String of package
     */
    public String getPackage(){
        return jsonObject.toString();
    }

}
