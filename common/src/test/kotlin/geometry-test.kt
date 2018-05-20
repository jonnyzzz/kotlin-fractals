package org.jetbrains.demo.kotlinfractals

import kotlin.math.absoluteValue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GeometryTest {

  @Test
  fun test1() {
    val t = Transformation(
            Rect(0, 0, 10, 10),
            Rect(-1.0, -1.0, 1.0, 1.0)
    )

    assertEquals(-1.0, -1.0, t.toComplex(0, 0))
    assertEquals(1.0, 1.0, t.toComplex(10, 10))
    assertEquals(0.0, 0.0, t.toComplex(5, 5))
  }
}

fun assertEqualsE(expected: Double, actual: Double) {
  assertTrue("Expected = $expected, actual = $actual") {
    (actual - expected).absoluteValue < 1e-5
  }
}

fun assertEquals(re: Double, im: Double, actual: Complex) {
  assertEqualsE(re, actual.re)
  assertEqualsE(im, actual.im)
}

fun assertEquals(x: Int, y: Int, actual: Pixel) {
  assertEquals(x, actual.x)
  assertEquals(y, actual.y)
}
