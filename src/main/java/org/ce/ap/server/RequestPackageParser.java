package main.java.org.ce.ap.server;

import org.json.simple.JSONObject;
import main.java.org.ce.ap.netWorkingParams;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

public class RequestPackageParser {
    private JSONObject jsonObject;
    private final netWorkingParams.RequestPackage.Methods method;
    private final String description;

    /**
     * gets a string and trys to parse it as a request package
     * @param request input string
     * @throws ParseException if parsing was not successful
     */
    public RequestPackageParser(String request) throws ParseException {
        jsonObject=(JSONObject) new JSONParser().parse(request);
        method=(netWorkingParams.RequestPackage.Methods) jsonObject.get(netWorkingParams.RequestPackage.Fields.method);
        description=(String) jsonObject.get(netWorkingParams.RequestPackage.Fields.description);
    }

    /**
     * returns the value of method field in request package
     * @return name of method
     */
    public netWorkingParams.RequestPackage.Methods getMethod(){
        return method;
    }

    /**
     * returns the string of description field in request package
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets name of field and search for it in parameters Map in the request package and returns value of that field
     * @param field name of desired field
     * @return the value of that field
     */
    public Object getParameterValue(netWorkingParams.RequestPackage.ParametersFields field){
        Map m=(Map) jsonObject.get(netWorkingParams.RequestPackage.Fields.parametersValues);
        return m.get(field);
    }


}
