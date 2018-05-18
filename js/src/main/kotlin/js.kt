import org.jetbrains.demo.kotlinfractals.Color
import org.jetbrains.demo.kotlinfractals.Point
import org.w3c.dom.CanvasRenderingContext2D

external val module: Module

external fun require(name: String): dynamic

class JSCanvasPixelRenderer(
        private val ctx: CanvasRenderingContext2D) {

  private val imageData = ctx.createImageData(
          ctx.canvas.width.asDynamic(),
          ctx.canvas.height.asDynamic())

  val width
    get() = imageData.width

  val height
    get() = imageData.height

  fun putPixel(p: Point, c: Color) {
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
