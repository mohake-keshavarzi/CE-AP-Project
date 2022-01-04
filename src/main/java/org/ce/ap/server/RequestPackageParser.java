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
    public RequestPackageParser(String request) throws ParseException {
        jsonObject=(JSONObject) new JSONParser().parse(request);
        method=(netWorkingParams.RequestPackage.Methods) jsonObject.get(netWorkingParams.RequestPackage.Fields.method);
        description=(String) jsonObject.get(netWorkingParams.RequestPackage.Fields.description);
    }

    public netWorkingParams.RequestPackage.Methods getMethod(){
        return method;
    }

    public String getDescription() {
        return description;
    }

    public Object getParameterValue(netWorkingParams.RequestPackage.ParametersFields field){
        Map m=(Map) jsonObject.get(netWorkingParams.RequestPackage.Fields.parametersValues);
        return m.get(field);
    }
}
