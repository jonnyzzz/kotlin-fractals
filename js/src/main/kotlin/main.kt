import org.jetbrains.demo.kotlinfractals.Color
import org.jetbrains.demo.kotlinfractals.JSCanvasPixelRenderer
import org.jetbrains.demo.kotlinfractals.MandelbrotPointIteration
import org.jetbrains.demo.kotlinfractals.Rect
import org.jetbrains.demo.kotlinfractals.Transformation
import org.jetbrains.demo.kotlinfractals.forEachPixel
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
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
  val t = Transformation(
          image.pixelRect,
          Rect(-2.0, -2.0, 2.0, 2.0))

  t.forEachPixel { p, c ->
    val isReachable = MandelbrotPointIteration(c).asSequence().drop(1500).firstOrNull()
    image.putPixel(p,
            if (isReachable != null) {
              Color.WHITE
            } else {
              Color.BLACK
            })
  }

  image.commit()

  //TODO: start the app some how
  return application
}

