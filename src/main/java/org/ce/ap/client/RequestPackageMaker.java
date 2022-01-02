package main.java.org.ce.ap.client;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestPackageMaker {
    private JSONObject jsonObject;
    private Map m;


    public RequestPackageMaker(netWorkingParams method, String description){
        jsonObject= new JSONObject();
        jsonObject.put("method",method);
        jsonObject.put("description",description);
        m=new LinkedHashMap();
        setParameters();
    }


    public void putParameter(String parameterName,Object value){

        m.put(parameterName,value);
        setParameters();
    }
    public void putParameter(netWorkingParams parameter,Object value){
        putParameter(parameter.name(),value);
    }

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
        jsonObject.put("parameterValues",m);
    }

    public String toString(){
       return jsonObject.toJSONString();
    }

    public String getString(){return jsonObject.toString();}

}
