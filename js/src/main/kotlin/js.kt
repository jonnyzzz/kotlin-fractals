import org.jetbrains.demo.kotlinfractals.Color
import org.jetbrains.demo.kotlinfractals.Point
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.w3c.dom.CanvasRenderingContext2D

external val module: Module

external fun require(name: String): dynamic

class JSCanvasPixelRenderer(
        weight : Int,
        height : Int,
        private val ctx: CanvasRenderingContext2D) {

  private val imageData = ctx.createImageData(weight.toDouble(), height.toDouble())
  private val image = imageData.data

  fun putPixel(p: Point, c: Color) {
    val base = 4 * (p.x + imageData.height * p.y)

    image[base + 0] = 127
    image[base + 1] = 127
    image[base + 2] = 127
    image[base + 3] = -127

    println("$base: ${image[base]}-${image[base + 1]}-${image[base + 2]}-${image[base + 3]}")
  }

  fun commit() {
    println("Commit image to ctx: width=${imageData.width}, height=${imageData.height}")
    ctx.putImageData(imageData, 0.0, 0.0)
    ctx.save()
  }
}
