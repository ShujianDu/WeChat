package com.yada.system.adapter.directsale

import play.api.libs.functional.syntax._
import play.api.libs.json.{Writes, _}

/**
  * 直销系统service
  */
trait DirectSale {
  /**
    * 预约办卡进度查询
    *
    * @param scheduleOfCrdCardApplyParams ScheduleOfCrdCardApplyParams
    * @return ScheduleOfCrdCardApplyResult
    */
  def getScheduleOfCrdCardApply(scheduleOfCrdCardApplyParams: ScheduleOfCrdCardApplyParams): ScheduleOfCrdCardApplyResult
}

/**
  *
  * @param txnId       交易号
  * @param deviceType  设备类型
  * @param deviceToken TOKEN号
  * @param imei        设备号
  * @param channelNo   渠道编码
  * @param name        客户姓名
  * @param idType      证件类型
  * @param id          证件号
  * @param currentPage 当前页
  */
case class ScheduleOfCrdCardApplyParams(txnId: String, deviceType: String, deviceToken: String, imei: String, channelNo: String, name: String, idType: String, id: String, currentPage: String)

object ScheduleOfCrdCardApplyParams {
  implicit val scheduleOfCrdCardApplyParamsReads: Reads[ScheduleOfCrdCardApplyParams] = (
    (__ \ "txnId").read[String] ~ (__ \ "deviceType").read[String] ~ (__ \ "deviceToken").read[String] ~ (__ \ "imei").read[String]
      ~ (__ \ "channelNo").read[String] ~ (__ \ "name").read[String] ~ (__ \ "idType").read[String]~ (__ \ "id").read[String] ~ (__ \ "currentPage").read[String]
    ) (ScheduleOfCrdCardApplyParams.apply _)

  implicit val scheduleOfCrdCardApplyParamsWrites: Writes[ScheduleOfCrdCardApplyParams] = (
    (__ \ "txnId").write[String] ~ (__ \ "deviceType").write[String] ~ (__ \ "deviceToken").write[String] ~ (__ \ "id").write[String]
      ~ (__ \ "channelNo").write[String] ~ (__ \ "name").write[String] ~ (__ \ "idType").write[String]~ (__ \ "deviceToken").write[String] ~ (__ \ "currentPage").write[String]
    ) (unlift(ScheduleOfCrdCardApplyParams.unapply))
}

/**
  *
  * @param hasNext     是否有下一页
  * @param cardApplies 卡片申请实体集合
  */
case class ScheduleOfCrdCardApplyResult(hasNext: Boolean, cardApplies: List[CardApply])

object ScheduleOfCrdCardApplyResult {
  implicit val scheduleOfCrdCardApplyParamsWrites: Writes[ScheduleOfCrdCardApplyResult] = (
    (__ \ "hasNext").write[Boolean] ~ (__ \ "cardApplies").write[ List[CardApply]]
    ) (unlift(ScheduleOfCrdCardApplyResult.unapply))
}

/**
  * 预约办卡进度实体
  *
  * @param applyNo   申请编号
  * @param pdnDes    卡产品
  * @param passDate  申请通过日期
  * @param phase     阶段编码
  * @param phaseDesc 阶段描述
  */
case class CardApply(applyNo: String, pdnDes: String, passDate: String, phase: String, phaseDesc: String)

object CardApply {
//  implicit val cardApplyReads: Reads[CardApply] = (
//    (__ \ "applyNo").read[String] ~ (__ \ "pdnDes").read[String] ~ (__ \ "passDate").read[String] ~ (__ \ "phase").read[String]
//      ~ (__ \ "phaseDesc").read[String]
//    ) (CardApply.apply _)

  implicit val cardApplyWrites: Writes[CardApply] = (
    (__ \ "applyNo").write[String] ~ (__ \ "pdnDes").write[String] ~ (__ \ "passDate").write[String] ~ (__ \ "phase").write[String]
      ~ (__ \ "phaseDesc").write[String]
    ) (unlift(CardApply.unapply))
}
