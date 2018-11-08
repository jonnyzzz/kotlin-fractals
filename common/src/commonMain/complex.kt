package org.jetbrains.demo.kotlinfractals

import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min


data class Complex(val re: Double, val im: Double) {
  companion object {
    val ZERO = Complex(0.0, 0.0)
    val i = Complex(0.0, 1.0)
  }

  override fun toString(): String {
    return "$re " + if (im < 0)
      "- ${im.absoluteValue}i"
    else
      "+ ${im.absoluteValue}i"
  }
}

operator fun Complex.plus(n: Complex)
        = Complex(this.re + n.re, this.im + n.im)

operator fun Complex.minus(n: Complex) = Complex(
        re = this.re - n.re,
        im = this.im - n.im
)

operator fun Complex.times(n: Complex) = Complex(
        re = this.re * n.re - this.im * n.im,
        im = this.im * n.re + this.re * n.im
)

val Complex.mod2 get() = re * re + im * im


infix fun Complex.to(that: Complex) = Rect(
        min(this.re, that.re),
        min(this.im, that.im),
        max(this.re, that.re),
        max(this.im, that.im)
)
