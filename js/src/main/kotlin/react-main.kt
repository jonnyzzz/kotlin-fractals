package org.jetbrains.demo.kotlinfractals

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
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

  @InternalCoroutinesApi
  private suspend fun CoroutineScope.renderImage(img: JSFractalImage) = ReactRenderer.apply {
    when (state.renderMode) {
      RenderMode.JS -> renderJS(img, state.fractalRect)
      RenderMode.JVM -> renderJVM(img, state.fractalRect)
      RenderMode.MIXED -> renderMixed(img, state.fractalRect)
    }
    println("Rendering completed")
  }

  private fun MainComponentState.handleMouseClick(it : PixelInfo) {
    if (mouseDownPixel == null) {
      mouseDownPixel = it
      return
    }

    val canvasSize = props.canvasSize
    val fromC = toComplex(canvasSize, fractalRect, mouseDownPixel ?: return)
    val toC = toComplex(canvasSize, fractalRect, it)
    fractalRect = fromC to toC

    resetMouse()
  }

  @InternalCoroutinesApi
  override fun RBuilder.render() {
    h1 { +"Kotlin Fractals" }

    child(AutoResizeCanvasControl::class) {
      attrs {
        canvasSize = props.canvasSize
        renderMode = listOf(state.renderMode, state.fractalRect)
        renderImage = { renderImage(it) }
        onMouseMove = setStateAction { mousePixel = it }
        onMouseClick = setStateAction { handleMouseClick(it) }
      }
    }

    styledDiv {
      css { +Styles.status }

      button {
        attrs.onClickFunction = setStateAction { reset() }
        +"Reset"
      }

      button {
        attrs.onClickFunction = setStateAction { renderMode = RenderMode.JVM }
        +"JVM"
      }

      button {
        attrs.onClickFunction = setStateAction { renderMode = RenderMode.JS }
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

