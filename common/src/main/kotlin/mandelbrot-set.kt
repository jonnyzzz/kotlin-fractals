package org.jetbrains.demo.kotlinfractals


class MandelbrotPointIteration(
        val c: Complex,
        val z: Complex = Complex.ZERO,
        val iteration: Int = 0

) : Iterator<MandelbrotPointIteration> {
  val mod2 = z.mod2

  override fun next() = MandelbrotPointIteration(
          c = c,
          z = z * z + c,
          iteration = iteration + 1
  )

  override fun hasNext() = mod2 < 4
}
