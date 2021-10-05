package controller.util

/**
 * Utility object used for string manipulation.
 */
object StringUtil {

  /**
   * Implicit class used to enrich the String class.
   *
   * @param string the string used by the implicit class.
   */
  implicit class RichString(string: String) {

    /**
     * @param regex a regex.
     * @return the same string but with the content in regex swapped with a space.
     */
    def withoutContent(regex: String): String = string.replaceAll(regex, " ")

    /**
     * @return the string without new lines or escape characters.
     */
    def withoutNewLine: String = withoutContent("[\\t\\n\\r]+")

  }

  def traversableFormattedLikeArray[A](list: Traversable[A]): String = "[" + list.mkString(",") + "]"

}
