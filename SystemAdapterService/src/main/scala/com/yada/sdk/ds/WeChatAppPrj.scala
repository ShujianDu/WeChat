package com.yada.sdk.ds

import javax.jws.{WebMethod, WebParam, WebResult, WebService}
import javax.xml.bind.annotation._
import javax.xml.ws.{RequestWrapper, ResponseWrapper}

/**
  * 直销系统webservice接口
  */
@WebService(targetNamespace = "http://webService.forms.com", name = "WechatAppPrj")
trait WeChatAppPrj {
  @WebMethod
  @RequestWrapper(localName = "getWechatAppPrj", targetNamespace = "http://webService.forms.com", className = "com.yada.sdk.ds.GetWeChatAppPrj")
  @ResponseWrapper(localName = "getWechatAppPrjResponse", targetNamespace = "http://webService.forms.com", className = "com.yada.sdk.ds.GetWeChatAppPrjResponse")
  @WebResult(name = "getWechatAppPrjReturn", targetNamespace = "http://webService.forms.com")
  def getWeChatAppPrj(@WebParam(name = "data", targetNamespace = "http://webService.forms.com") data: String): String
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = Array("data"))
@XmlRootElement(name = "getWechatAppPrj")
class GetWeChatAppPrj {
  @XmlElement(required = true)
  var data: String = ""
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = Array("getWechatAppPrjReturn"))
@XmlRootElement(name = "getWechatAppPrjResponse")
class GetWeChatAppPrjResponse{
  @XmlElement(required = true)
  var getWechatAppPrjReturn: String = ""
}