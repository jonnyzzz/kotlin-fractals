package org.jetbrains.demo.kotlinfractals

import kotlin.math.max
import kotlin.math.min


data class ComplexNumber(val re: Double, val im: Double) {
  companion object {
    val ZERO = ComplexNumber(0.0, 0.0)
    val i = ComplexNumber(0.0, 1.0)
  }
}

operator fun ComplexNumber.plus(n: ComplexNumber)
        = ComplexNumber(this.re + n.re, this.im + n.im)

operator fun ComplexNumber.minus(n: ComplexNumber)
        = ComplexNumber(this.re - n.re, this.im - n.im)

operator fun ComplexNumber.times(n: ComplexNumber)
        = ComplexNumber(this.re * n.re - this.im * n.im, this.im * n.re + this.re * n.im)

val ComplexNumber.mod2 get() = re * re + im * im


infix fun ComplexNumber.to(that: ComplexNumber) = Rect(
        min(this.re, that.re),
        min(this.im, that.im),
        max(this.re, that.re),
        max(this.im, that.im)
)
