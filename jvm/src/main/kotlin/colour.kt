package org.jetbrains.demo.kotlinfractals


actual fun colorFromHSL(h : Double, s: Double, l: Double) : Color {
  val x = java.awt.Color.getHSBColor(h.toFloat() / 100f, s.toFloat() / 100f, l.toFloat() / 100f)
  return Color(x.red, x.green, x.blue)
}

