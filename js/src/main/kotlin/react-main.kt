package org.jetbrains.demo.kotlinfractals

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.button
import react.dom.h1
import react.dom.render
import react.setState
import styled.css
import styled.styledDiv
import kotlin.browser.document


fun renderReactMain() {
  injectStyles()

  document.title = "Fractals in Kotlin"

  render(document.getElementById("root")) {
    child(MainComponent::class) {

    }
  }
}

class MainComponent : RComponent<RProps, MainComponent.MainComponentState>() {

  interface MainComponentState : RState {
    var mousePixel: PixelInfo?
    var screenInfo: ScreenInfo?

    var fractalRect : Rect<Double>
  }

  init {
    state.apply {
      fractalRect = MandelbrotRender.initialArea
    }
  }

  override fun RBuilder.render() {
    h1 { +"Kotlin Fractals" }

    child(AutoResizeCanvasControl::class) {
      attrs {
        renderImage = {
          fill(Color(32,45,234))
          commit()
        }

        onMouseMove = { pixel, screen ->
          setState {
            mousePixel = pixel
            screenInfo = screen
          }
        }
      }
    }

    styledDiv {
      css { + Styles.status }

      button { +"Reset" }
      button { +"JVM" }
      button { +"JS" }
    }

    child(PixelInfoComponent::class) {
      attrs {
        pixelInfo = state.mousePixel
        fractalRect = state.fractalRect
        screenInfo = state.screenInfo
      }
    }

    styledDiv {
      css { + Styles.linkBlock}
      a {
        + "Open JVM image"
      }
    }
  }
}

