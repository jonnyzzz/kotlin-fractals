package org.jetbrains.demo.kotlinfractals

import org.jetbrains.demo.kotlinfractals.Pixel
import org.jetbrains.demo.kotlinfractals.Complex
import org.jetbrains.demo.kotlinfractals.Color

class NativeImage(
        val width: Int,
        val height: Int
) : FractalImage {

  val data = Array(width) { Array(height) { Color.BLACK } }

  override val pixelRect
    get() = Rect(0, 0, width, height)

  override fun putPixel(p: Pixel, c: Color) {
    data[p.x][p.y] = c
  }

  fun toConsole() {
    println(buildString {
      appendLine()
      (0 until height).forEach { y ->
        (0 until width).forEach { x ->
          append(
                  if (data[x][y] == Color.BLACK) {
                    'X'
                  } else {
                    '.'
                  }
          )
        }
        appendLine()
      }
      appendLine()
    })
  }
}

fun StringBuilder.appendLine() = append('\n')
