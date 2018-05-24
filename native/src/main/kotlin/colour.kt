package org.jetbrains.demo.kotlinfractals

import org.jetbrains.demo.kotlinfractals.Color

actual fun Colors.hsl(h: Double,
                      s: Double,
                      l: Double): Color {
  //OK for console mode, for now
  return Colors.WHITE
}

actual class Color private constructor() {
  companion object {
    val BLACK = Color()
    val WHITE = Color()
  }
}

actual val Colors.BLACK
  get() = Color.BLACK

val Colors.WHITE
  get() = Color.WHITE
