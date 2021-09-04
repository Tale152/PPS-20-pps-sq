package controller.editor.graph.util

object StringUtils {

  private val MaxStringLength: Int = 20

  def buildLabel(id: String, narrative: String): String = "[" + id + "] " + narrative

  def truncateString(s: String): String = {
    var maxChar = s.substring(0, Math.min(s.length(), MaxStringLength))
    if (maxChar.length < s.length && maxChar.contains(" ")) {
      maxChar = maxChar.substring(0, maxChar.lastIndexOf(" ")) + "..."
    }
    maxChar
  }

}
