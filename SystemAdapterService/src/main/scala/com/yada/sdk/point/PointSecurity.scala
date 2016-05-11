

package com.yada.sdk.point

import java.net.{URLDecoder, URLEncoder}
import java.security.MessageDigest
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

import com.typesafe.config.ConfigFactory
import com.yada.PointDes
import org.apache.commons.codec.binary.Base64

import scala.util.Random

/**
  * 积分加密
  * 用于积分协议（0118）和（0154）
  */
private[point] class PointSecurity {
  // 微信积分页面跳转的DES的密钥
  val weChatUserAuthenticationDESKey = ConfigFactory.load().getString("Point.weChatUserAuthenticationDESKey")
  // 微信聪明购页面跳转的DES的密钥
  val weChatUserAuthenticationDESKeyForCMG = ConfigFactory.load().getString("Point.weChatUserAuthenticationDESKeyForCMG")

  val pointDes = new PointDes()

  /**
    * 微信积分页面跳转加密
    *
    * @param content 要加密的内容
    * @return 加密后的内容
    */
  def weChatUserAuthenticationEncrypt(content: String): String = {
    pointDes.encrypt(content, weChatUserAuthenticationDESKey)
  }

  /**
    * 微信聪明购页面跳转
    */
  def weChatUserAuthenticationForCMGEncrypt(content: String): String = {
    pointDes.encrypt(content, weChatUserAuthenticationDESKeyForCMG)
  }
}

object PointSecurity {
  var GLOBAL = new PointSecurity
}