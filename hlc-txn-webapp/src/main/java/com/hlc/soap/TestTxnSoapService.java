package com.hlc.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


/**
 * User: rsmith
 * Date: 3/21/14 12:45 PM
 */

@WebService
@SOAPBinding(style = Style.RPC)
public interface TestTxnSoapService {

    @WebMethod
    String getHelloWorldAsString();

    @WebMethod
    String makeWsCallForSuccessfulSave();

//    @WebMethod
//    String localTxnCall();

}
