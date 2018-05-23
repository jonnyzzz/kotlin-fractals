import kotlinx.html.a
import kotlinx.html.stream.appendHTML
import org.jetbrains.demo.kotlinfractals.Color
import org.jetbrains.demo.kotlinfractals.Colors
import org.jetbrains.demo.kotlinfractals.Complex
import org.jetbrains.demo.kotlinfractals.GRAY
import org.jetbrains.demo.kotlinfractals.JSFractalImage
import org.jetbrains.demo.kotlinfractals.MandelbrotRender
import org.jetbrains.demo.kotlinfractals.Pixel
import org.jetbrains.demo.kotlinfractals.Rect
import org.jetbrains.demo.kotlinfractals.to
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window

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

  window.setTimeout({
    application.initApp(state)
  }, 10)

  //TODO: start the app some how
  return application
}

fun Event.toPoint() = Pixel(asDynamic().layerX, asDynamic().layerY)

fun ApplicationBase.initApp(state: dynamic) {
  println("It runs!")

  val canvas = document.getElementById("canvas") ?: return
  canvas as HTMLCanvasElement

  val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

  val image = JSFractalImage(ctx)
  println("client width=${image.width}, height=${image.height}")
  image.fill(Colors.GRAY)
  image.commit()

  println("Gray color is done")

  val render = MandelbrotRender(image = image, maxIterations = 200)

  fun serverSideRender() {
    render.fractalArea.run {
      println("Rendering with Server-side!: $this")

      val loader = js("new Image();") as HTMLImageElement
      loader.addEventListener("load", {
        println("Loaded image from the file")
        image.loadFromImage(loader)
      })
      val url = "http://localhost:8888/mandelbrot?top=$top&right=$right&bottom=$bottom&left=$left&width=${image.width}&height=${image.height}"
      document.getElementById("jvmLink").unsafeCast<HTMLDivElement>().innerHTML = buildString {


        appendHTML().a(href = "$url&jvm=false", target = "_blank") {
          +"Open JVM image in new window"
        }

      }

      //fake server is too slow
      window.setTimeout({
        loader.setAttribute("src", url)
      }, 3_000)
    }
  }

  fun clientSideRender() {
    //load local fast
    window.setTimeout({
      render.render()
      image.commit()
    })
  }

  fun render(r: Rect<Double> = MandelbrotRender.initialArea) {
    render.setArea(r)

    //load from server (slooow)
    serverSideRender()

    document.getElementById("pxD").unsafeCast<HTMLDivElement>().innerText = "${render.fractalArea}"

    clientSideRender()
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
    println("Render executed")
    render()
  })

  document.getElementById("openInJVM").unsafeCast<HTMLButtonElement>().addEventListener("click", {
    serverSideRender()
  })

  document.getElementById("openInJS").unsafeCast<HTMLButtonElement>().addEventListener("click", {
    println("re-render with JS")
    clientSideRender()
  })

  println("Rendering Fractal")
  render()
}
