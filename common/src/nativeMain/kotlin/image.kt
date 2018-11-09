package org.jetbrains.demo.kotlinfractals

class NativeTextImage(
        val width: Int,
        val height: Int
) : FractalImage {

  val data = Array(width) { Array(height) { Colors.BLACK } }

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
                  if (data[x][y] == Colors.BLACK) {
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
