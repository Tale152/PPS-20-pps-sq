package view.util.scalaQuestSwingComponents

import view.util.scalaQuestSwingComponents.SqRetroFont.RetroFont
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.InputStream

import view.util.StringUtil.DefaultFontSize

/**
 * Object that represents the imported font for the game,
 * added to the list of all predefined fonts.
 */
private object SqRetroFont {
  lazy val RetroFont: Font = loadFont(getClass.getClassLoader.getResourceAsStream("retroFont.ttf"))
  private val Ge: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment
  Ge.registerFont(RetroFont)

  def loadFont(is: InputStream): Font = {
    try {
      val myFont = Font.createFont(Font.TRUETYPE_FONT, is)
      myFont.deriveFont(Font.PLAIN)
    } catch {
      case _: Exception =>
        println("Error on loading external Font, loading default one..")
        Font.getFont("Arial")
    }
  }
}

/**
 * Represents a custom font for ScalaQuest.
 *
 * @param bold     true if the font needs to be bold.
 * @param fontSize the size of the font.
 */
case class SqFont(bold: Boolean, fontSize: Int = DefaultFontSize) extends Font(RetroFont.getFontName, if (bold) {
  Font.BOLD
} else {
  Font.PLAIN
}, fontSize)
