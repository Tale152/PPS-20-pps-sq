package view.util

/**
 * Utility Object used to style strings in Java Swing.
 */
object StringFormatUtil {

  /**
   * Contains HTML tags used to format a formatted string.
   */
  object FormatElements {
    val NewLine: String= "<br/>"
  }

  /**
   * @param string the input string.
   * @return a string that can be decorated with html tags for styling.
   */
  def formatted(string: String): String = "<html>" + string + "</html>"

}
