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
            SIGN_IN_REQUEST,
            SIGN_UP_REQUEST,
            SEND_TWEET_REQUEST,
            GET_TIMELINE_REQUEST
        }

        public enum ParametersFields {//available parameters to have for request
            password,
            username,
            firstname,
            lastname

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
                PACKAGE_ERROR,
                SIGN_IN_ERROR
            }
            public enum ErrorCodes{
                UNABLE_TO_PARSE_PACKAGE,
                INTERNAL_SERVER_ERROR
            }

        }
        //Standard response package
        public enum StandardResponsePackage{
            count,
            results;
            public enum ResultsFields{
                isAuthenticationSucceed,//if authentication was successful
                isNewAccountCreated,//weather for authentication used an existing account or created a new one
                isUsernameDuplicated // weather if username has been used before
            }
        }
    }


}
