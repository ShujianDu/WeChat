package com.yada.sdk.commons

import java.io.IOException

case class SystemIOException(channelName :String,channelAddress:String) extends IOException
