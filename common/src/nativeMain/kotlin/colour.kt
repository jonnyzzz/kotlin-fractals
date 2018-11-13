package org.jetbrains.demo.kotlinfractals

actual class Color

private val white = Color()
private val black = Color()

actual fun Colors.hsl(h: Double,
                      s: Double,
                      l: Double) = white

actual val Colors.BLACK
  get() = black
