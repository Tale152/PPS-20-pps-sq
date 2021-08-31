package view.util.scalaQuestSwingComponents

import view.Frame
import view.util.scalaQuestSwingComponents.SqRetroFont.{DefaultFontSize, RetroFont}

import java.awt.Font
import javax.swing.JList
import java.awt.GraphicsEnvironment
import java.nio.file.Paths

/**
 * object that represents the imported font for the game,
 * added to the list of all predefined fonts.
 */
object SqRetroFont {
  val DefaultFontSize = 15
  lazy val RetroFont: Font = Frame.loadFont(Paths.get(getClass.getClassLoader.getResource("retroFont.ttf")
    .toURI).toFile.getAbsolutePath)
  val Ge: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment
  Ge.registerFont(RetroFont)
  val Fonts = new JList(Ge.getAvailableFontFamilyNames)
}


case class SqFont(bold: Boolean, fontSize: Int = DefaultFontSize) extends Font(RetroFont.getFontName, if (bold) {
  Font.BOLD
} else {
  Font.PLAIN
}, fontSize)
