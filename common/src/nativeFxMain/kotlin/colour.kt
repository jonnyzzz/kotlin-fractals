package org.jetbrains.demo.kotlinfractals

@ExperimentalUnsignedTypes
actual class Color(val color: UInt)

@ExperimentalUnsignedTypes
private val black = Color(0x00_00_00_ffu)

@ExperimentalUnsignedTypes
private val white = Color(0xff_ff_ff_ffu)

@ExperimentalUnsignedTypes
actual fun Colors.hsl(h: Double,
                      s: Double,
                      l: Double) = white

@ExperimentalUnsignedTypes
actual val Colors.BLACK
  get() = black
