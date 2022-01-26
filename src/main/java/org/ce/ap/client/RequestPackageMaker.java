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
      * creates a new request package maker
      */
     public RequestPackageMaker(String description){
        jsonObject= new JSONObject();
//        jsonObject.put(netWorkingParams.RequestPackage.Fields.method,method);
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
    public void putParameterArray(netWorkingParams.RequestPackage.ParametersFields arrayName, Collection data){
        putParameterArray(arrayName.name(),data);
    }


    private void setParameters(){
        jsonObject.put(netWorkingParams.RequestPackage.Fields.parametersValues,m);
    }

    public String toString(){
       return jsonObject.toJSONString();
    }

    public String getString(){return jsonObject.toString();}

     public void creatSignInRequestPackage(String username,String password){
         jsonObject.put(netWorkingParams.RequestPackage.Fields.method,netWorkingParams.RequestPackage.Methods.SIGN_IN_REQUEST.name());
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.username,username);
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.password,password);
     }

     public void creatSignUpRequestPackage(String firstname,String lastname,String username , String password){
         jsonObject.put(netWorkingParams.RequestPackage.Fields.method,netWorkingParams.RequestPackage.Methods.SIGN_UP_REQUEST.name());
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.username,username);
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.password,password);
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.firstname,firstname);
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.lastname,lastname);
     }

     public void createTimelineRequestPackage(){
         jsonObject.put(netWorkingParams.RequestPackage.Fields.method,netWorkingParams.RequestPackage.Methods.GET_TIMELINE_REQUEST.name());
     }

     public void createPublishNewTweetPackage(TweetInfo tweet,boolean isRetweet){
         jsonObject.put(netWorkingParams.RequestPackage.Fields.method,netWorkingParams.RequestPackage.Methods.SEND_TWEET_REQUEST.name());
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.isRetweet,isRetweet);
         this.putParameter(netWorkingParams.RequestPackage.ParametersFields.tweetContext,tweet.getContext());
         if(isRetweet){
//             this.putParameter();
         }

     }

}
