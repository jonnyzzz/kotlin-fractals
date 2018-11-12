package org.jetbrains.demo.kotlinfractals

actual class Color(val color: Int)

private val black = Color(0x0)

actual fun Colors.hsl(h: Double,
                      s: Double,
                      l: Double): Color {
  return Color(0x00ffffff)
/*
  val c = UIColor.colorWithHue(hue = h, saturation = s, brightness = 1.0, alpha = 1.0).CIColor

  fun scale(x: Double, p: Int) = (256 * x).toInt() * p

  return Color(scale(c.red, 256 * 256) + scale(c.green, 256) + scale(c.blue, 1))*/
}

actual val Colors.BLACK
  get() = black
