package com.yada.gcs.sdk

import javax.jws.{WebService, WebMethod, WebParam, WebResult}
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlType}
import javax.xml.ws.{RequestWrapper, ResponseWrapper}

/**
  * Created by locky on 2016/3/17.
  */
@WebService(targetNamespace = "www.boc.com.cn/gcs", name = "Gateway")
trait Gateway {
  @WebMethod
  @RequestWrapper(localName = "service", targetNamespace = "www.boc.com.cn/gcs", className = "com.yada.gcs.sdk.Service")
  @ResponseWrapper(localName = "serviceResponse", targetNamespace = "www.boc.com.cn/gcs", className = "com.yada.gcs.sdk.ServiceResponse")
  @WebResult(name = "response", targetNamespace = "")
  @throws(classOf[GatewayException])
  def service(@WebParam(name = "request", targetNamespace = "") request: String): String
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "service", propOrder = Array("request"))
class Service {
  var request: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceResponse", propOrder = Array("response"))
class ServiceResponse{
  var response: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GatewayException")
class GatewayException extends Exception