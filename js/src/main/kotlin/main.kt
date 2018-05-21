import org.jetbrains.demo.kotlinfractals.Color
import org.jetbrains.demo.kotlinfractals.Complex
import org.jetbrains.demo.kotlinfractals.FractalCanvas
import org.jetbrains.demo.kotlinfractals.FractalCanvasProperties
import org.jetbrains.demo.kotlinfractals.JSFractalImage
import org.jetbrains.demo.kotlinfractals.MandelbrotRender
import org.jetbrains.demo.kotlinfractals.Pixel
import org.jetbrains.demo.kotlinfractals.Rect
import org.jetbrains.demo.kotlinfractals.fractalImageFromCanvas
import org.jetbrains.demo.kotlinfractals.to
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.createRef
import react.dom.canvas
import react.dom.h1
import react.dom.p
import react.dom.render
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

  render(document.getElementById("reactElement")) {
    h1 { +"Kotlin Multiplatform Fractals!" }
    p { +"Mandelbrot set"}

    node(FractalCanvas::class, FractalCanvasProperties())
  }

  println("It runs!")

  val canvas = document.getElementById("canvas") as HTMLCanvasElement
  val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

  val image = JSFractalImage(ctx)
  println("client width=${image.width}, height=${image.height}")
  image.fill(Color.GRAY)
  image.commit()

  println("Gray color is done")

  val render = MandelbrotRender(image = image)

  fun render(r: Rect<Double> = MandelbrotRender.initialArea) {
    render.setArea(r)
    render.render()
    image.commit()

    document.getElementById("pxD").unsafeCast<HTMLDivElement>().innerText = "Area: ${render.fractalArea}"

  }

  var fromPixel = Complex.ZERO
  canvas.addEventListener("mousedown", {
    val p = it.toPoint()
    val c = render.toComplex(p)
    document.getElementById("pxD").unsafeCast<HTMLDivElement>().innerText = "$c"
    fromPixel = c
  })

  canvas.addEventListener("mouseup", {
    val p = it.toPoint()
    val c = render.toComplex(p)
    render(fromPixel to c)
  })

  canvas.addEventListener("mousemove", {
    val p = it.toPoint()
    val c = render.toComplex(p)
    document.getElementById("pxU").unsafeCast<HTMLDivElement>().innerText = "$c"
  })

  document.getElementById("reset").unsafeCast<HTMLButtonElement>().addEventListener("click", {
    render()
  })

  println("Rendering Fractal")
  render()

  //TODO: start the app some how
  return application
}

fun Event.toPoint() = Pixel(asDynamic().layerX, asDynamic().layerY)
