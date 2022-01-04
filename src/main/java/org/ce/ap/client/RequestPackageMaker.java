package main.java.org.ce.ap.client;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import main.java.org.ce.ap.netWorkingParams;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

 public class RequestPackageMaker {
    private JSONObject jsonObject;
    private Map m;

     /**
      * creates a new request package with desired method and ad a description to it
      * @param method what should server do
      * @param description a string for logging
      */
     public RequestPackageMaker(netWorkingParams.RequestPackage.Methods method, String description){
        jsonObject= new JSONObject();
        jsonObject.put(netWorkingParams.RequestPackage.Fields.method,method);
        jsonObject.put(netWorkingParams.RequestPackage.Fields.description,description);
        m=new LinkedHashMap();
        setParameters();
    }


     /**
      * puts a parameter with given name and value in the parameters map
      * @param parameterName name of parameter
      * @param value value of the parameter
      */
    public void putParameter(String parameterName,Object value){

        m.put(parameterName,value);
        setParameters();
    }
    public void putParameter(netWorkingParams.RequestPackage.ParametersFields parameter, Object value){
        putParameter(parameter.name(),value);
    }

     /**
      * puts a collection of parameters with a specific name
      * @param arrayName the name of the collection
      * @param data the collection
      */
    public void putParameterArray(String arrayName, Collection data){
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(data);
        m.put(arrayName,jsonArray);
        setParameters();
    }
    public void putParameterArray(netWorkingParams arrayName, Collection data){
        putParameterArray(arrayName.name(),data);
    }


    private void setParameters(){
        jsonObject.put(netWorkingParams.RequestPackage.Fields.parametersValues,m);
    }

    public String toString(){
       return jsonObject.toJSONString();
    }

    public String getString(){return jsonObject.toString();}

}
