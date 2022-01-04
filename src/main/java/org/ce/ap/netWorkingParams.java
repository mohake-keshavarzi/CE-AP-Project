package main.java.org.ce.ap;

//fields are written in camel format and values are in uppercase
public enum netWorkingParams {
    ;
    //Request package
    public enum RequestPackage {
        ;
        public enum Fields {
            method,//the method which the client is requesting
            description,//a string for the description of that method
            parametersValues//other needed parameters
        }

        public enum Methods {//available methods to request
            AUTHENTICATION_REQUEST,
            SEND_TWEET_REQUEST,
            GET_TIMELINE_REQUEST
        }

        public enum ParametersFields {//available parameters to have for request
            password,
            username

        }

    }

    //Response Package
    public enum ResponsePackage{
        hasError;//do package is an Error package or a standard one
        //Error Package
        public enum ErrorPackage{
            ;
            public enum Fields{
                errorType,//the type of error
                errorCode,//what is the error
                errorParameters//other needed parameters for describing the error
            }
            public enum ErrorTypes{
                PACKAGE_ERROR
            }
            public enum ErrorCodes{
                UNABLE_TO_PARSE_PACKAGE
            }
            public enum ErrorParametersFields{

            }
        }
        //Standard response package
        public enum StandardResponsePackage{
            count,
            results;
            public enum ResultsFields{
                isAuthenticationSucceed,//if authentication was successful
                isNewAccountCreated,//weather for authentication used an existing account or created a new one
            }
        }
    }


}
