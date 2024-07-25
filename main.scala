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
  val pic =
    layoutExample()

  pic.draw()

def basicExample(): Picture[?] =
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

def layoutExample(): Picture[?] =
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