import org.w3c.dom.HTMLCanvasElement
import react.createRef
import react.dom.body
import react.dom.button
import react.dom.canvas
import react.dom.div
import react.dom.h1
import react.dom.head
import react.dom.render
import react.dom.title
import kotlin.browser.document


fun renderReactMain() {
  render(document.documentElement) {
    head {
      title { +"Multiplatform Fractals in Kotlin" }
    }
    body {

      h1 { +"Kotlin Fractals" }

      canvas {
        ref = createRef<HTMLCanvasElement>()
      }

      div {
        +"pixel info"
      }

      div {
        button { +"Reset" }
        button { +"JVM" }
        button { +"JS" }
      }
    }
  }
}

