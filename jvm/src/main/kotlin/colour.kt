package org.jetbrains.demo.kotlinfractals

import www.camick.com.HSLColor

actual typealias Color = java.awt.Color

actual val Colors.BLACK get() = Color.BLACK
actual val Colors.GRAY get() = Color.GRAY
actual val Colors.WHITE get() = Color.WHITE

actual fun colorFromHSL(h : Double, s: Double, l: Double) : Color {
  val x = HSLColor(h.toFloat(),s.toFloat(),l.toFloat()).rgb
  return Color(x.red, x.green, x.blue)
}

