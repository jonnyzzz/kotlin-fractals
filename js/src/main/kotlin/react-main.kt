package org.jetbrains.demo.kotlinfractals

import react.dom.a
import react.dom.button
import react.dom.h1
import react.dom.render
import styled.css
import styled.styledDiv
import kotlin.browser.document


fun renderReactMain() {
  injectStyles()

  document.title = "Fractals in Kotlin"

  render(document.getElementById("root")) {
    h1 { +"Kotlin Fractals" }

    child(AutoResizeCanvasControl::class) {
      attrs {
        renderImage = {
          fill(Color(32,45,234))
          commit()
        }
      }
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

