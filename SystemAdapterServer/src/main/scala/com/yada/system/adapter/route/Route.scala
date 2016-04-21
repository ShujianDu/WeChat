package com.yada.system.adapter.route

trait Route {
  def execute(json: String): String
}

