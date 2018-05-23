package org.jetbrains.demo.kotlinfractals

import org.w3c.dom.HTMLCanvasElement
import react.createRef
import react.dom.a
import react.dom.button
import react.dom.canvas
import react.dom.div
import react.dom.h1
import react.dom.render
import styled.css
import styled.styledCanvas
import styled.styledDiv
import kotlin.browser.document


fun renderReactMain() {
  injectStyles()

  document.title = "Fractals in Kotlin"

  render(document.getElementById("root")) {
    h1 { +"Kotlin Fractals" }

    styledCanvas {
      css { +Styles.canvas}
      ref = createRef<HTMLCanvasElement>()
    }

    styledDiv {
      css { + Styles.status }

      button { +"Reset" }
      button { +"JVM" }
      button { +"JS" }
    }

    styledDiv {
      css { + Styles.infoBlock }
      +"pixel info"
    }

    styledDiv {
      css { + Styles.linkBlock}
      a {
        + "Open JVM image"
      }
    }
  }
}
