package org.jetbrains.demo.kotlinfractals

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
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

  watchScreenSize { screen ->
    render(document.getElementById("root")) {
      child(MainComponent::class) {
        attrs {
          canvasSize = screen.toCanvasSize()
        }
      }
    }
  }
}

interface MainComponentProps : RProps {
  var canvasSize : ScreenInfo
}

class MainComponent : RComponent<MainComponentProps, MainComponent.MainComponentState>() {

  interface MainComponentState : RState {
    var mousePixel: PixelInfo?
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
        canvasSize = props.canvasSize

        renderImage = {

          MandelbrotRender.justRender(maxIterations = 200,
                  image = this,
                  area = state.fractalRect)

          commit()
        }

        onMouseMove = setStateAction {
          mousePixel = it
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
        canvasSize = props.canvasSize
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

