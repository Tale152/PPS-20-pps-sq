package controller.util.serialization

/**
 * Utility object used for string manipulation.
 */
object StringUtil {

  /**
   * Implicit class used to enrich the String class.
   * @param string the string used by the implicit class.
   */
  implicit class RichString(string: String){

    /**
     * @param regex a regex.
     * @return the same string but with the content in regex swapped with a space.
     */
    def withoutContent(regex: String): String =  string.replaceAll(regex, " ")

    /**
     * @return the string without new lines or escape characters.
     */
    def withoutNewLine: String = withoutContent("[\\t\\n\\r]+")

  }

  def traversableFormattedLikeArray[A](list: Traversable[A]): String = "[" + list.mkString(",") + "]"

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

    /**
     * @param string the input string
     * @return the string replacing all underscores and dashes with a single space and converting all to upper case
     */
    def standardize(string: String): String =
      string.replaceAll("_", " ").replaceAll("-", " ").toUpperCase

  }

}
