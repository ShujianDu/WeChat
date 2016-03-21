package com.yada.sdk.gcs.protocol

import com.yada.sdk.gcs.GCSClient

/**
  * GCS协议
  */
trait GCSProtocol[ResponseType <: GCSResp] extends GCSReq {
  var gcsClient = GCSClient.GLOBAL

  def send: ResponseType = {
    val resp = gcsClient.send(reqXML)
    generate(resp)
  }

  protected def generate(xml: String): ResponseType
}
