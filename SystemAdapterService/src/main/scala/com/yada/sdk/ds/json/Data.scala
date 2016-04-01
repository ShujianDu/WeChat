package com.yada.sdk.ds.json

import play.api.libs.json.JsValue

private[ds] case class Data(head: JsValue, body: JsValue)
