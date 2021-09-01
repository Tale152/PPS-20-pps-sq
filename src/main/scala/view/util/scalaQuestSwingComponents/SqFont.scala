package view.util.scalaQuestSwingComponents

import view.Frame
import view.util.scalaQuestSwingComponents.SqRetroFont.{DefaultFontSize, RetroFont}

import java.awt.Font
import javax.swing.JList
import java.awt.GraphicsEnvironment

/**
 * object that represents the imported font for the game,
 * added to the list of all predefined fonts.
 */
object SqRetroFont {
  val DefaultFontSize = 15
  lazy val RetroFont: Font = Frame.loadFont(getClass.getClassLoader.getResourceAsStream("retroFont.ttf"))
  private val Ge: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment
  Ge.registerFont(RetroFont)
  new JList(Ge.getAvailableFontFamilyNames)
}


case class SqFont(bold: Boolean, fontSize: Int = DefaultFontSize) extends Font(RetroFont.getFontName, if (bold) {
  Font.BOLD
} else {
  Font.PLAIN
}, fontSize)
