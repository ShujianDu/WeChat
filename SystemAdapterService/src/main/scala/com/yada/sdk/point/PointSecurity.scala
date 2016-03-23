

package com.yada.sdk.point

import java.net.{URLDecoder, URLEncoder}
import java.security.MessageDigest
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.{Cipher, SecretKey, SecretKeyFactory}

import com.typesafe.config.ConfigFactory
import org.apache.commons.codec.binary.Base64

import scala.util.Random

/**
  * 积分加密
  * 用于积分协议（0118）和（0154）
  */
private[point] class PointSecurity {

  // 产生a-z的字母
  protected val chars = (for (i <- 0 until 26) yield ('a' + i).toChar).toList
  // 建立一个随机
  protected val random = new Random()
  // 微信积分页面跳转的DES的密钥
  val weChatUserAuthenticationDESKey = get3DESKey(ConfigFactory.load().getString("Point.weChatUserAuthenticationDESKey"))
  // 微信聪明购页面跳转的DES的密钥
  val weChatUserAuthenticationDESKeyForCMG = get3DESKey(ConfigFactory.load().getString("Point.weChatUserAuthenticationDESKeyForCMG"))

  /**
    * 加密
    *
    * @param content 要加密的内容
    * @return 加密后的内容
    */
  def encrypt(content: String, key: SecretKey): String = {
    val head = new StringBuilder
    val foot = new StringBuilder
    for (i <- 0 until 6) {
      val char = chars(random.nextInt(26))
      if (i < 3) {
        head.append(char)
      } else {
        foot.append(char)
      }
    }
    val tempContent = String.format("%s-%s-%s", head.toString, content, head.toString).getBytes("UTF-16LE")
    val cipher = Cipher.getInstance("DESede")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptData = cipher.doFinal(tempContent)
    val tempBase64Content = Base64.encodeBase64String(encryptData)
    URLEncoder.encode(tempBase64Content, "ISO8859-1")
  }

  /**
    * 解密
    *
    * @param content 要解密的内容
    * @param key     解密所用的key
    * @return
    */
  def decrypt(content: String, key: SecretKey): String = {
    val decodeContent = URLDecoder.decode(content, "ISO8859-1")
    val encryptData = Base64.decodeBase64(decodeContent)
    val cipher = Cipher.getInstance("DESede")
    cipher.init(Cipher.DECRYPT_MODE, key)
    val data = cipher.doFinal(encryptData)
    val dataStr = new String(data, "UTF-16LE")
    dataStr.substring(4, dataStr.length - 4)
  }

  private def get3DESKey(key: String): SecretKey = {
    val messageDigest = MessageDigest.getInstance("MD5")
    val digest = messageDigest.digest(key.getBytes("GBK"))
    val keyBts = if (digest.length < 24) {
      digest ++ Array.fill(24 - digest.length)(0.toByte)
    } else {
      digest.take(24)
    }
    val dks = new DESedeKeySpec(keyBts)
    val keyFactory = SecretKeyFactory.getInstance("DESede")
    keyFactory.generateSecret(dks)
  }
}

object PointSecurity {
  var GLOBAL = new PointSecurity
}