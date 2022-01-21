package main.java.org.ce.ap.server;


import main.java.org.ce.ap.server.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.server.impl.ProfilesManagerImpl;
import org.json.simple.parser.ParseException;
import main.java.org.ce.ap.netWorkingParams;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClientController {
    private String inputString;
    private Profile profile;
    private RequestPackageParser inputParser;
    private ResponsePackageMaker responsePackageMaker;
    private ProfilesManagerImpl profilesManager;
    private AuthenticationServiceImpl authenticationService;

    public ClientController(ProfilesManagerImpl profilesManager, AuthenticationServiceImpl authenticationService){
        this.profilesManager=profilesManager;
        this.authenticationService=authenticationService;

    }
    public String doTaskAndCreateResponse(RequestPackageParser input){
        this.inputParser=input;
        if (inputParser.getMethod()== netWorkingParams.RequestPackage.Methods.SIGN_IN_REQUEST){
            responsePackageMaker =doSignIn();
        }else if(inputParser.getMethod()==netWorkingParams.RequestPackage.Methods.SIGN_UP_REQUEST){
            responsePackageMaker =doSignUp();
        }
        return responsePackageMaker.getPackage();
    }

    private ResponsePackageMaker doSignIn(){
        String username=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.username);
        String password=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.password);
        ResponsePackageMaker responsePackageMaker;
        Map m=new LinkedHashMap();
        try {
            profile=authenticationService.login(username,password);
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed,true);
            responsePackageMaker.addResult(m);
        }catch (NoSuchAlgorithmException ex){
            responsePackageMaker=makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.SIGN_IN_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.INTERNAL_SERVER_ERROR);
            responsePackageMaker.addErrorParameter(ex);
        }catch (IllegalStateException ex){
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isAuthenticationSucceed,false);
            responsePackageMaker.addResult(m);
        }

        return responsePackageMaker;
    }

    private ResponsePackageMaker doSignUp(){
        String username=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.username);
        String password=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.password);
        String firstname=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.firstname);
        String lastname=(String) inputParser.getParameterValue(netWorkingParams.RequestPackage.ParametersFields.lastname);
        ResponsePackageMaker responsePackageMaker;
        Map m=new LinkedHashMap();
        try {
            profile=authenticationService.creatProfile(firstname,lastname,username,password);
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated,true);
            responsePackageMaker.addResult(m);
        }catch (NoSuchAlgorithmException ex){
            responsePackageMaker=makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes.SIGN_IN_ERROR,
                    netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes.INTERNAL_SERVER_ERROR);
            responsePackageMaker.addErrorParameter(ex);
        }catch (IllegalStateException ex){
            responsePackageMaker=makeStandardResponsePackageMaker();
            m.put(netWorkingParams.ResponsePackage.StandardResponsePackage.ResultsFields.isNewAccountCreated,false);
            responsePackageMaker.addResult(m);
        }
        return responsePackageMaker;
    }




    private ResponsePackageMaker makeErrorPackageMaker(netWorkingParams.ResponsePackage.ErrorPackage.ErrorTypes errorType
            ,netWorkingParams.ResponsePackage.ErrorPackage.ErrorCodes errorCode){
        ResponsePackageMaker errorMaker = new ResponsePackageMaker(true);
        errorMaker.createErrorPackage(errorType,errorCode);
        return errorMaker;
    }
    private ResponsePackageMaker makeStandardResponsePackageMaker(){
        ResponsePackageMaker standardPackageMaker = new ResponsePackageMaker(false);
        standardPackageMaker.creatStandardResponsePackage();
        return standardPackageMaker;
    }

}
