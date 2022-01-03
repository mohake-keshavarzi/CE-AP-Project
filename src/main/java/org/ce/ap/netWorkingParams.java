package main.java.org.ce.ap;

public enum netWorkingParams {
    ;
    //Request package
    public enum RequestPackage {
        ;
        public enum Fields {
            method,
            description,
            parametersValues
        }

        public enum Methods {
            AUTHENTICATION_REQUEST,
            SEND_TWEET_REQUEST,
            GET_TIMELINE_REQUEST
        }

        public enum ParametersFields {
            PASSWORD

        }

    }

    //Response Package
    public enum ResponsePackage{
        HAS_ERROR;
        //Error Package
        public enum ErrorPackage{
            ;
            public enum Fields{
                ERROR_TYPE,
                ERROR_CODE,
                ERROR_PARAMETERS
            }
            public enum ErrorTypes{

            }
            public enum ErrorCodes{

            }
        }
        //Standard response package
        public enum StandardResponsePackage{
            COUNT,
            RESULTS;
            public enum ResultsFields{

            }
        }
    }


}
