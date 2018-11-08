package org.jetbrains.demo.kotlinfractals

import java.awt.Graphics2D
import java.awt.image.BufferedImage


inline fun BufferedImage.graphics(paint : Graphics2D.() -> Unit) {
  val g = createGraphics()
  try {
    g.paint()
  } finally {
    g.dispose()
  }
}

class FractalGraphics(val g: BufferedImage) : FractalImage {
  override val pixelRect
    get() = Rect(0, 0, g.width, g.height)

  override fun putPixel(p: Pixel, c: Color) {
    g.setRGB(p.x, p.y, c.rgb)
  }
}

