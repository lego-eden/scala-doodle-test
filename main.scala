//> using dep org.creativescala::doodle::0.22.0

// Colors and other useful stuff
import doodle.core.*
// Extension methods
import doodle.syntax.all.*
// Render to a window using Java2D (must be running in the JVM)
import doodle.java2d.*
// Need the Cats Effect runtime to run everything
import cats.effect.unsafe.implicits.global

@main
def main(): Unit =
  val pictures = Vector(
    hut
      .beside(scaledHut)
      .beside(rotatedHut)
      .beside(translatedHut)
      .beside(verticallyReflectedHut),
    sizeExample,
    basicExample,
    layoutExample,
    overlappingCircles,
    rollingCirclesMargin,
    basicStyle,
    strokeStyle,
  )

  val frame =
    Frame.default.withSize(500, 500).withBackground(Color.midnightBlue)
  val canvas = frame.canvas()
  pictures(0).drawWithFrame(frame)

def basicExample: Picture[?] =
  val blackSquare = Picture.rectangle(30, 30).fillColor(Color.black)
  val redSquare = Picture.rectangle(30, 30).fillColor(Color.red)

  val twoByTwo = 
    (redSquare beside blackSquare) above (blackSquare beside redSquare)
  
  val fourByFour =
    (twoByTwo beside twoByTwo) above (twoByTwo beside twoByTwo)

  val chessboard =
    (fourByFour beside fourByFour) above (fourByFour beside fourByFour)

  val basicShapes = 
    Picture
      .circle(200)
      .strokeColor(Color.white)
      .on(Picture.square(125).strokeColor(Color.white))
      .on(Picture.triangle(100, 100).strokeColor(Color.white))
      .strokeWidth(5.0)

  val feather =
    ClosedPath.empty
      .lineTo(100, 100)
      .curveTo(90, 75, 90, 25, 10, 10)
      .moveTo(100, 100)
      .curveTo(75, 90, 25, 90, 10, 10)
      .path
      .strokeWidth(5.0)

  val sinePoints =
    for (x <- 0 to 360) yield Point(x, x.degrees.sin * 100)
  val sineCurve = Picture.interpolatingSpline(sinePoints).debug

  // (basicShapes on chessboard).beside(feather).above(sineCurve)
  sineCurve

def layoutExample: Picture[?] =
  val debugLayout =
    Picture
      .circle(100)
      .debug
      .beside(Picture.regularPolygon(5, 30).debug)
      .above(
        Picture.circle(100).beside(Picture.regularPolygon(5, 30)).debug
      )
  
  val atAndOriginAt =
    Picture.circle(100).at(25, 25).debug
      .beside(Picture.circle(100).originAt(25, 25).debug)

  val pentagon =
    val circ = Picture.circle(10)
    circ.at(50, 0.degrees)
      .on(circ.at(50, 72.degrees))
      .on(circ.at(50, 144.degrees))
      .on(circ.at(50, 216.degrees))
      .on(circ.at(50, 288.degrees))
    
  debugLayout
    .above(atAndOriginAt)
    .beside(pentagon)
    .scale(2, 2)

def overlappingCircles: Picture[?] =
  Picture
    .circle(100)
    .strokeColor(Color.purple)
    .originAt(Landmark(Coordinate.percent(50), Coordinate.percent(-50))).debug
    .on(
      Picture
        .circle(100)
        .strokeColor(Color.red)
        .originAt(Landmark(Coordinate.percent(-50), Coordinate.percent(-50))).debug
    )
    .on(
      Picture
        .circle(100)
        .strokeColor(Color.blue)
        .originAt(Landmark(Coordinate.percent(-50), Coordinate.percent(50))).debug
    )
    .on(
      Picture
        .circle(100)
        .strokeColor(Color.deepPink)
        .originAt(Landmark(Coordinate.percent(50), Coordinate.percent(50))).debug
    ).scale(2, 2)

val circle = Picture.circle(50)

def rollingCirclesMargin: Picture[?] =
  circle.margin(25).debug
    .beside(circle.margin(15).debug)
    .beside(circle.debug)
    .beside(circle.margin(-15).debug)
    .beside(circle.margin(-25).debug)

def basicStyle: Picture[?] =
  Picture
    .circle(100)
    .beside(
      Picture
        .circle(100)
        .strokeColor(Color.purple)
        .strokeWidth(5.0)
        .fillColor(Color.lavender)
    )

def strokeStyle =
  Picture
    .star(5, 50, 25)
    .strokeWidth(5.0)
    .strokeColor(Color.limeGreen)
    .strokeJoin(Join.bevel)
    .strokeCap(Cap.round)
    .strokeDash(Array(9.0, 7.0))
    .beside(
      Picture
        .star(5, 50, 25)
        .strokeWidth(5.0)
        .strokeColor(Color.greenYellow)
        .strokeJoin(Join.miter)
        .strokeCap(Cap.square)
        .strokeDash(Array(12.0, 9.0))
    )
    .scale(4, 4)

def fillStyle =
  Picture
    .square(100)
    .fillGradient(
      Gradient.linear(
        Point(-50, 0),
        Point(50, 0),
        List((Color.red, 0.0), (Color.yellow, 1.0)),
        Gradient.CycleMethod.repeat
      )
    )
    .strokeWidth(5.0)
    .margin(0.0, 5.0, 0.0, 0.0)
    .beside(
      Picture
        .circle(100)
        .fillGradient(
          Gradient.dichromaticRadial(Color.limeGreen, Color.darkBlue, 50)
        )
        .strokeWidth(5)
    )
    .scale(3, 3)

val hut =
  Picture
    .triangle(50, 50)
    .fillColor(Color.black)
    .strokeColor(Color.red)
    .above(Picture.rectangle(50, 50).fillColor(Color.blue))

val rotatedHut = hut.rotate(45.degrees)
val scaledHut = hut.scale(1.5, 1.5)
val translatedHut = hut.translate(500, 50)
val verticallyReflectedHut = hut.verticalReflection

def sizeExample =
  val circle =
    Picture
      .circle(100)
      .strokeColor(Color.midnightBlue)
  
  val circleBoundingBox: Picture[BoundingBox] =
    circle.boundingBox

  val boundingBox =
    circleBoundingBox.flatMap(bb => 
      Picture
        .roundedRectangle(bb.width, bb.height, 3.0)
        .strokeColor(Color.hotpink)
        .strokeWidth(3.0)
    )

  val picture = circle.on(boundingBox)

  picture.scale(3, 3)