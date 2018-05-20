package org.jetbrains.demo.kotlinfractals

import org.jetbrains.demo.kotlinfractals.Color

actual fun colorFromHSL(h : Double, s: Double, l: Double) : Color {
  //OK for console mode, for now
  return Color.WHITE
}
