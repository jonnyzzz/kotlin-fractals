package org.jetbrains.demo.kotlinfractals

import org.w3c.dom.CanvasRenderingContext2D

class JSFractalImage(
        private val ctx: CanvasRenderingContext2D
) : FractalImage {

  private val imageData = ctx.createImageData(
          ctx.canvas.width.toDouble(),
          ctx.canvas.height.toDouble())

  val width
    get() = imageData.width

  val height
    get() = imageData.height

  override val pixelRect
    get() = Rect(0, 0, right = width, bottom = height)

  fun fill(c: Color) {
    pixelRect.forEachPixel {
      putPixel(it, c)
    }
  }

  override fun putPixel(p: Pixel, c: Color) {
    val base = 4 * (p.x + imageData.width * p.y)

    val image : dynamic = imageData.data

    image[base + 0] = c.r
    image[base + 1] = c.g
    image[base + 2] = c.b
    image[base + 3] = 255
  }

  fun commit() {
    println("Commit image to ctx: width=${imageData.width}, height=${imageData.height}")
    ctx.putImageData(imageData, 0.0, 0.0)
  }
}
