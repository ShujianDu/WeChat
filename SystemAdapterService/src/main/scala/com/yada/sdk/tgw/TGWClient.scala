package com.yada.sdk.tgw

import java.net.HttpCookie

import com.typesafe.scalalogging.slf4j.Logger
import com.yada.sdk.tgw.xml.{XmlHandler, TxnRsp, TxnReq}
import org.slf4j.LoggerFactory

/**
  * TGW客户端
  */
class TGWClient(httpCookie: HttpCookie) extends ITGWClient {
  private val log = Logger(LoggerFactory.getLogger(classOf[TGWClient]))
  //    private val xmlHandler:XmlHandler = new XmlHandler
//  val httpCookie: HttpCookie = new HttpCookie("", "")

  override def send(txnReq: TxnReq): TxnRsp = {

    ???
  }
}
