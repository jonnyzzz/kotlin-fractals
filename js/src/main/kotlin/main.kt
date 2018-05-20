import org.jetbrains.demo.kotlinfractals.Color
import org.jetbrains.demo.kotlinfractals.ColorPicker
import org.jetbrains.demo.kotlinfractals.Complex
import org.jetbrains.demo.kotlinfractals.JSCanvasPixelRenderer
import org.jetbrains.demo.kotlinfractals.MandelbrotPointIteration
import org.jetbrains.demo.kotlinfractals.Pixel
import org.jetbrains.demo.kotlinfractals.Rect
import org.jetbrains.demo.kotlinfractals.Transformation
import org.jetbrains.demo.kotlinfractals.forEachPixel
import org.jetbrains.demo.kotlinfractals.to
import org.jetbrains.demo.kotlinfractals.toComplex
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import kotlin.browser.document


interface ApplicationBase {
  fun disposeAndExtractState(): dynamic
}


fun main(args: Array<String>) {
  var application: ApplicationBase? = null

  val state: dynamic = module.hot?.let { hot ->
    hot.accept()

    hot.dispose { data ->
      data.appState = application?.disposeAndExtractState()
      application = null
    }

    hot.data
  }

  if (document.body != null) {
    application = start(state)
  } else {
    application = null
    document.addEventListener("DOMContentLoaded", { _ -> application = start(state) })
  }

  println("ok...")
}

fun start(state: dynamic): ApplicationBase {
  println("start...")

  val application = object : ApplicationBase {
    override fun disposeAndExtractState() = mapOf<String, String>()
  }

  println("It runs!")

  val canvas = document.getElementById("canvas") as HTMLCanvasElement
  val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

  val image = JSCanvasPixelRenderer(ctx)
  println("client width=${image.width}, height=${image.height}")
  image.fill(Color.GRAY)
  image.commit()

  println("Gray color is done")

  println("Rendering Fractal")
  val initialRect = Rect(-2.0, -2.0, 2.0, 2.0)

  var t = Transformation(image.pixelRect, initialRect)
  fun render(r: Rect<Double>) {
    t = Transformation(image.pixelRect, r)

    val maxIterations = 1500
    val picker = ColorPicker(maxIterations)

    t.forEachPixel { p, c ->
      var pt = MandelbrotPointIteration(c)
      repeat(maxIterations) {
        if (pt.hasNext()) {
          pt = pt.next()
        }
      }

      image.putPixel(p, picker.selectColour(pt))
    }

    image.commit()
  }

  var fromPixel = Complex.ZERO

  canvas.addEventListener("mousedown", {
    val p = it.toPoint()
    val c = t.toComplex(p)
    document.getElementById("pxD").unsafeCast<HTMLDivElement>().innerText = "$c"
    fromPixel = c
  })

  canvas.addEventListener("mouseup", {
    val p = it.toPoint()
    val c = t.toComplex(p)
    render(fromPixel to c)
  })

  canvas.addEventListener("mousemove", {
    val p = it.toPoint()
    val c = t.toComplex(p)
    document.getElementById("pxU").unsafeCast<HTMLDivElement>().innerText = "$c"
  })

  document.getElementById("reset").unsafeCast<HTMLButtonElement>().addEventListener("click", {
    render(initialRect)
  })

  render(initialRect)

  //TODO: start the app some how
  return application
}

fun Event.toPoint() = Pixel(asDynamic().layerX, asDynamic().layerY)
