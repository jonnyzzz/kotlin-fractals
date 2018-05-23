package org.jetbrains.demo.kotlinfractals

import kotlinext.js.invoke
import kotlinx.css.BorderStyle
import kotlinx.css.CSSBuilder
import kotlinx.css.Color
import kotlinx.css.Float
import kotlinx.css.Float.right
import kotlinx.css.Position
import kotlinx.css.Position.*
import kotlinx.css.RuleSet
import kotlinx.css.TagSelector
import kotlinx.css.body
import kotlinx.css.button
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.border
import kotlinx.css.properties.lh
import kotlinx.css.px
import kotlinx.css.rgb
import styled.StyleSheet
import styled.StyledComponents

object Styles : StyleSheet("jonnyzzz", isStatic = true) {
  val status by css {
    position = absolute
    bottom = 0.px
    paddingBottom = 12.px
  }

  val infoBlock by css {
    position = absolute
    bottom = 0.px
    right = 0.px

    padding(bottom = 12.px, right = 12.px)
  }

  val canvas by css {
    position = absolute

    border(width = 1.px, style = BorderStyle.solid, color = Color.black)
  }
}

fun injectStyles() {
  val css = CSSBuilder().apply {
    operator fun TagSelector.plus(s: TagSelector) = listOf(this, s)
    operator fun List<TagSelector>.plus(block: RuleSet) = forEach { it(block) }

    body {
      fontFamily = "-apple-system,BlinkMacSystemFont,Helvetica Neue,Arial,sans-serif"
      fontSize = 12.px

      backgroundColor = Color.darkGray
    }

    button {
      lineHeight = 24.px.lh
      borderRadius = 3.px
      borderColor = rgb(68, 68, 68)
      padding(left = 16.px, right = 16.px)
      margin(left = 8.px, right = 8.px)
    }

  }.toString()

  println("My styles\n$css")

  //does not work
  StyledComponents.injectGlobal(css)
}

