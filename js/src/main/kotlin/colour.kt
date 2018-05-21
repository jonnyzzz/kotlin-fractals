package org.jetbrains.demo.kotlinfractals

import kotlinext.js.require

private val lib = require("color-convert")

actual fun colorFromHSL(h : Double,
                        s: Double,
                        l: Double) : Color {
  val x = lib.hsl.rgb(h,s,l)
  return Color(x[0], x[1], x[2])
}
