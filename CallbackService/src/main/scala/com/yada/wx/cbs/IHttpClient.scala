package com.yada.wx.cbs


trait IHttpClient {
  def send(value:String,url:String):String
}

class HttpClient extends IHttpClient{
  override def send(value: String,url:String): String = ???
}

object HttpClient extends HttpClient{

}
