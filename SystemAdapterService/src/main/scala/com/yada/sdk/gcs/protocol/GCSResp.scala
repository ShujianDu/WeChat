package com.yada.sdk.gcs.protocol

import com.yada.sdk.gcs.xml.XmlHandler

/**
  * GCS响应报文
  */
class GCSResp(xml: String) {

  val gcs = xmlHandler.fromXml(xml)
  if (failedThrowException) {
    // 如果不是成功的响应，直接抛出异常
    if (systemValue("returnCode") != "+GC00000") throw new RuntimeException(s"error response XML...$xml")
  }

  protected def xmlHandler = XmlHandler.GLOBAL

  /**
    * 获取响应头的内容
    *
    * @param key 内容所对应的标签name的值
    * @return
    */
  def systemValue(key: String): String = {
    gcs.system.props.getOrElse(key, "")
  }

  /**
    * 获取响应正文的内容
    *
    * @param key 内容所对应的标签name的值
    * @return
    */
  def pageValue(key: String): String = {
    if (gcs.page.isEmpty) throw new RuntimeException("GCS resp page is not exist...")
    gcs.page.get.props.getOrElse(key, "")
  }

  /**
    * 获取相应正文的Entity列表
    *
    * @return List中是Entity，每个Entity的是一个Map，其中key为prop标签name属性的值,value是prop标签value属性的值
    */
  def pageList: List[Map[String, String]] = {
    if (gcs.page.isEmpty) throw new RuntimeException("GCS resp page is not exist...")
    val list = gcs.page.get.list
    if (gcs.page.isEmpty) throw new RuntimeException("GCS resp page[list] is not exist...")
    list.get.entities.map(x => x.toMap)
  }

  /**
    * 获取响应正文的Entity列表
    *
    * @param propsToObj entity下的一组prop标签转换成对象的方法，其中props的key为prop标签name属性的值，value是prop标签value属性的值
    * @tparam T 输出对象的类型
    * @return
    */
  def pageListValues[T](propsToObj: Map[String, String] => T): List[T] = {
    val list = pageList
    list.map(values => {
      propsToObj(values)
    })
  }

  /**
    * 获取响应正文中List下Entity的内容
    *
    * @param keys 所需要值对应的KEY（在Entity下，prop标签的name属性的值列表）
    * @return
    */
  def pageListValues(keys: Array[String]): List[Map[String, String]] = {
    pageListValues(map => {
      keys.map(key => key -> map.getOrElse(key, "")).toMap
    })
  }

  /**
    * 当报文验证非成功，抛出异常
    *
    * @return
    */
  protected def failedThrowException: Boolean = true
}
