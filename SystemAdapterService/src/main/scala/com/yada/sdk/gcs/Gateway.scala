package com.yada.sdk.gcs

import javax.jws.{WebService, WebMethod, WebParam, WebResult}
import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlType}
import javax.xml.ws.{RequestWrapper, ResponseWrapper}

/**
  * GCS的WSDL文件对应的接口
  */
@WebService(targetNamespace = "www.boc.com.cn/gcs", name = "Gateway")
trait Gateway {
  @WebMethod
  @RequestWrapper(localName = "service", targetNamespace = "www.boc.com.cn/gcs", className = "com.yada.sdk.gcs.Service")
  @ResponseWrapper(localName = "serviceResponse", targetNamespace = "www.boc.com.cn/gcs", className = "com.yada.sdk.gcs.ServiceResponse")
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
class ServiceResponse {
  var response: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GatewayException")
class GatewayException extends Exception