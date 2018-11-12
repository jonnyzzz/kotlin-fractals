package org.jetbrains.demo.kotlinfractals

import kotlin.math.max
import kotlin.math.min

actual class Color(val color: Int)

private val black = Color(0x0)

actual fun Colors.hsl(h: Double,
                      s: Double,
                      l: Double): Color {

  //A ported `common-jvm` code for simplicity

  if (s < 0.0f || s > 100.0f) error("Color parameter outside of expected range - Saturation: $s")
  if (l < 0.0f || l > 100.0f) error("Color parameter outside of expected range - Luminance: $l")

  //  Formula needs all values between 0 - 1.
  var h = h
  var s = s
  var l = l
  h %= 360.0
  h /= 360
  s /= 100
  l /= 100

  val q = if (l < 0.5)
    l * (1 + s)
  else
    l + s - s * l

  val p = 2 * l - q

  val r = minmax(HueToRGB(p, q, h + 1.0 / 3.0), 16)
  val g = minmax(HueToRGB(p, q, h), 8)
  val b = minmax(HueToRGB(p, q, h - 1.0 / 3.0), 0)
  return Color(r + g + b)
}

private inline fun minmax(v: Double, bits : Int) = (min(1.0, max(0.0, v)) * 255 + 0.5).toInt().shl(bits)

private inline fun HueToRGB(p: Double, q: Double, h: Double): Double {
  var h = h
  if (h < 0) h += 1f
  if (h > 1) h -= 1f

  return when {
    6 * h < 1 -> p + (q - p) * 6f * h
    2 * h < 1 -> q
    3 * h < 2 -> p + (q - p) * 6f * (2.0f / 3.0f - h)
    else -> p
  }
}


actual val Colors.BLACK
  get() = black
