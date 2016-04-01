package com.yada.sdk.ds

import javax.jws.{WebMethod, WebParam, WebResult, WebService}
import javax.xml.bind.annotation._
import javax.xml.ws.{RequestWrapper, ResponseWrapper}

import scala.beans.BeanProperty

/**
  * 直销系统webservice接口
  */
@WebService(targetNamespace = "http://webService.forms.com", name = "WechatAppPrj")
trait WeChatAppPrj {
  @WebMethod
  @RequestWrapper(localName = "getWechatAppPrj", targetNamespace = "http://webService.forms.com", className = "com.yada.sdk.ds.GetWechatAppPrj")
  @ResponseWrapper(localName = "getWechatAppPrjResponse", targetNamespace = "http://webService.forms.com", className = "com.yada.sdk.ds.GetWechatAppPrjResponse")
  @WebResult(name = "getWechatAppPrjReturn", targetNamespace = "")
  def getWechatAppPrj(@WebParam(name = "data", targetNamespace = "") data: String): String
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = Array("data"))
@XmlRootElement(name = "getWechatAppPrj")
class GetWechatAppPrj {
  @XmlElement(required = true)
  var data: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = Array("getWechatAppPrjReturn"))
@XmlRootElement(name = "getWechatAppPrjResponse")
class GetWechatAppPrjResponse {
  @XmlElement(required = true)
  @BeanProperty
  var getWechatAppPrjReturn: String = _
}