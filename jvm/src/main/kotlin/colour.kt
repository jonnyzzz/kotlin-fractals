package org.jetbrains.demo.kotlinfractals

import www.camick.com.HSLColor


actual fun colorFromHSL(h: Double,
                        s: Double,
                        l: Double): Color {
  val x = HSLColor(h.toFloat(), s.toFloat(), l.toFloat()).rgb
  return Color(x.red, x.green, x.blue)
}

