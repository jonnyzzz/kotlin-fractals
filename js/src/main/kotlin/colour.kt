package org.jetbrains.demo.kotlinfractals

import kotlinext.js.require
import kotlin.math.absoluteValue

private val lib = require("color-convert")

actual fun Colors.hsl(h: Double,
                      s: Double,
                      l: Double): Color {
  val x = lib.hsl.rgb(h, s, l)
  return Color(x[0], x[1], x[2])
}

actual class Color(r: Int,
                   g: Int,
                   b: Int) {
  val r = r.absoluteValue % 256
  val g = g.absoluteValue % 256
  val b = b.absoluteValue % 256
}

actual val Colors.BLACK
  get() = Color(0, 0, 0)

val Colors.GRAY
  get() = Color(255, 255, 255)

