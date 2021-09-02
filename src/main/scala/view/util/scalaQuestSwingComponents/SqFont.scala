package view.util.scalaQuestSwingComponents

import view.Frame
import view.util.scalaQuestSwingComponents.SqRetroFont.{DefaultFontSize, RetroFont}

import java.awt.Font
import java.awt.GraphicsEnvironment

/**
 * object that represents the imported font for the game,
 * added to the list of all predefined fonts.
 */
private object SqRetroFont {
  val DefaultFontSize = 15
  lazy val RetroFont: Font = Frame.loadFont(getClass.getClassLoader.getResourceAsStream("retroFont.ttf"))
  private val Ge: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment
  Ge.registerFont(RetroFont)
}

case class SqFont(bold: Boolean, fontSize: Int = DefaultFontSize) extends Font(RetroFont.getFontName, if (bold) {
  Font.BOLD
} else {
  Font.PLAIN
}, fontSize)
