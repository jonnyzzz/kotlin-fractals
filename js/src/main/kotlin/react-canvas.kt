package org.jetbrains.demo.kotlinfractals

import Underscore
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.createRef
import react.setState
import styled.css
import styled.styledCanvas
import kotlin.browser.window

class AutoResizeCanvasControl : RComponent<RProps, AutoResizeCanvasControl.CanvasState>() {
  init {
    println("Init called")
    state.updateSizeImpl()
  }

  interface CanvasState : RState {
    var height: Int?
    var width: Int?
  }

  private val canvas = createRef<HTMLCanvasElement>()

  override fun RBuilder.render() {
    styledCanvas {
      ref = canvas

      css {
        +Styles.canvas
      }

      attrs {
        width = state.width.toString()
        height = state.height.toString()
      }
    }
  }

  private val onResize = Underscore.debounce(100) { _: Event ->
    setState {
      updateSizeImpl()
    }
  }

  private fun CanvasState.updateSizeImpl() {
    width = window.innerWidth - Styles.canvasBorder * 2
    height = window.innerHeight - - Styles.canvasBorder * 2 - Styles.canvasOffsetBottom - Styles.canvasOffsetBottom
  }

  override fun componentDidMount() {
    window.addEventListener("resize", onResize)
  }

  override fun componentWillUnmount() {
    window.removeEventListener("resize", onResize)
  }
}
