package org.jetbrains.demo.kotlinfractals

import kotlinx.coroutines.experimental.CoroutineScope
import org.khronos.webgl.Uint16Array
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLImageElement


fun CoroutineScope.fractalImageFromCanvas(canvas : HTMLCanvasElement)
        = JSFractalImage({isActive}, canvas.getContext("2d") as CanvasRenderingContext2D)

class JSFractalImage(
        private val isAlive : () -> Boolean,
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
    if (!isAlive()) return

    pixelRect.forEachPixel {
      putPixel(it, c)
    }
  }

  fun loadFromImage(image: HTMLImageElement) {
    if (!isAlive()) return

    ctx.drawImage(image, 0.0, 0.0)
  }

  override fun putPixel(p: Pixel, c: Color) {
    if (!isAlive()) return

    val base = 4 * (p.x + imageData.width * p.y)

    val image : dynamic = imageData.data

    image[base + 0] = c.r
    image[base + 1] = c.g
    image[base + 2] = c.b
    image[base + 3] = 255
  }

  fun commit() {
    if (!isAlive()) return

    println("Commit image to ctx: width=${imageData.width}, height=${imageData.height}")
    ctx.putImageData(imageData, 0.0, 0.0)
  }
}
