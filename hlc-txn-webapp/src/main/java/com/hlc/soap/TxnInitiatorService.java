package com.hlc.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * User: rsmith
 * Date: 4/15/14 6:59 PM
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface TxnInitiatorService {

    @WebMethod
    String localTxnCall();

}
