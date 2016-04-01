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
  @RequestWrapper(localName = "getWechatAppPrj", targetNamespace = "http://webService.forms.com", className = "com.yada.sdk.ds.GetWechatAppPrj")
  @ResponseWrapper(localName = "getWechatAppPrjResponse", targetNamespace = "http://webService.forms.com", className = "com.yada.sdk.ds.GetWechatAppPrjResponse")
  @WebResult(name = "getWechatAppPrjReturn", targetNamespace = "http://webService.forms.com")
  def getWechatAppPrj(@WebParam(name = "data", targetNamespace = "http://webService.forms.com") data: String): String
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "data", propOrder = Array("data"))
@XmlRootElement(name = "getWechatAppPrj")
class GetWechatAppPrj {
  @XmlElement(required = true)
  var data: String = _
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getWechatAppPrjReturn", propOrder = Array("getWechatAppPrjReturn"))
@XmlRootElement(name = "getWechatAppPrjResponse")
class GetWechatAppPrjResponse{
  @XmlElement(required = true)
  var getWechatAppPrjReturn: String = _
}