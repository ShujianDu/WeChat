package com.yada.mock

import com.yada.system.adapter.points.{PointsValidates, PointsDetail, PointsBalance}

object PointsMockConfig {

  val cardNo = "5149580068840943"

  val pointsBalanceResult = Map{
    cardNo -> PointsBalance("10000","9000")
  }

  val pointsDetailResult = Map{
    cardNo -> List(
      PointsDetail("1","","1000","001","都市卡",cardNo,"ACCC","00"),
      PointsDetail("2","","2000","001","都市卡",cardNo,"ACCC","00")
    )
  }

  val pointsValidatesResult = Map{
    cardNo -> List(
      PointsValidates("10001","基金",cardNo,"10000","2012/06/30"),
      PointsValidates("10001","基金",cardNo,"6666","2012/07/31")
    )
  }

}
