package com.yada.sdk.tgw

import com.typesafe.config.ConfigFactory
import com.yada.sdk.tgw.xml.{TxnRsp, TxnReq}

/**
  * TGW客户端
  */
trait ITGWClient {
  def send(txnReq: TxnReq): TxnRsp
}

object ITGWClient {
  /**
    * 全局的一个TGW客户端
    */
  val GLOBAL = Class.forName(ConfigFactory.load().getString("TGW.client")).newInstance().asInstanceOf[ITGWClient]
}