package org.jetbrains.demo.kotlinfractals

import kotlinx.html.js.onClickFunction
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
  var canvasSize: ScreenInfo
}

class MainComponent : RComponent<MainComponentProps, MainComponent.MainComponentState>() {
  enum class RenderMode { JVM, JS, MIXED }

  interface MainComponentState : RState {
    var mousePixel: PixelInfo?
    var mouseDownPixel: PixelInfo?
    var fractalRect: Rect<Double>
    var renderMode: RenderMode
  }

  init {
    state.reset()
  }

  fun MainComponentState.reset() {
    fractalRect = MandelbrotRender.initialArea
    renderMode = RenderMode.MIXED
    resetMouse()
  }

  fun MainComponentState.resetMouse() {
    mouseDownPixel = null
    mousePixel = null
  }

  override fun componentWillUnmount() {
    setState {
      resetMouse()
    }
  }

  override fun RBuilder.render() {
    h1 { +"Kotlin Fractals" }

    child(AutoResizeCanvasControl::class) {
      attrs {
        canvasSize = props.canvasSize

        renderMode = listOf(state.renderMode, state.fractalRect)

        renderImage = {
          ReactRenderer.apply {
            when (state.renderMode) {
              RenderMode.JS -> renderJS(it, state.fractalRect)
              RenderMode.JVM -> renderJVM(it, state.fractalRect)
              RenderMode.MIXED -> renderMixed(it, state.fractalRect)
            }
          }
          println("Rendering completed")
        }

        onMouseMove = setStateAction { mousePixel = it }

        onMouseClick = setStateAction {
          if (mouseDownPixel == null) {
            mouseDownPixel = it
          } else {
            val fromC = toComplex(canvasSize, fractalRect, mouseDownPixel ?: return@setStateAction)
            val toC = toComplex(canvasSize, fractalRect, it)

            fractalRect = fromC to toC

            resetMouse()
          }
        }
      }
    }

    styledDiv {
      css { +Styles.status }

      button {
        attrs {
          onClickFunction = setStateAction { reset() }
        }

        +"Reset"
      }

      button {
        attrs {
          onClickFunction = setStateAction { renderMode = RenderMode.JVM }
        }

        +"JVM"
      }

      button {
        attrs {
          onClickFunction = setStateAction { renderMode = RenderMode.JS }
        }

        +"JS"
      }
    }

    child(PixelInfoComponent::class) {
      attrs {
        pixelInfo = state.mousePixel
        fractalRect = state.fractalRect
        canvasSize = props.canvasSize
      }
    }

    styledDiv {
      css { +Styles.linkBlock }
      a(href = BackendRender.jvmClientURL(props.canvasSize, state.fractalRect)) {
        +"Open JVM image"
      }
    }

    val mouseDownPixel = state.mouseDownPixel
    val mousePixel = state.mousePixel
    if (mouseDownPixel != null && mousePixel != null) {
      styledDiv {
        css {
          Styles.apply { canvasZoom(mousePixel.toScreen(), mouseDownPixel.toScreen()) }
        }
      }
    }
  }
}

