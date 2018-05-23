import org.jetbrains.demo.kotlinfractals.*


fun main(args: Array<String>) {
  println("Kotlin Fractals Native!")

  val image = NativeImage(80, 40)

  MandelbrotRender.justRender(
          image = image,
          maxIterations = 100,
          area = Rect(-2.0, -.9, .9, .9)
  )

  image.toConsole()
}
