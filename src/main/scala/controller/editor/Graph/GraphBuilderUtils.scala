package controller.editor.Graph

protected object GraphBuilderUtils {

  private val MaxStringLength: Int = 20

  def buildLabel(id: String, narrative: String): String = "[" + id + "] " + narrative

  def truncateString(s: String): String = {
    var maxChar = s.substring(0, Math.min(s.length(), MaxStringLength))
    if (maxChar.contains(" ")){
      maxChar = maxChar.substring(0, maxChar.lastIndexOf(" "))
    }
    maxChar + "..."
  }

  def getNodeStyle(color: String): String = "shape:circle;fill-color: " + color + "; text-alignment: under;"

  def getCorrectColor(active: Boolean, onTrue: String, onFalse: String): String =
    if (active) {
      getNodeStyle(onTrue)
    } else {
      getNodeStyle(onFalse)
    }

  def getCorrectString(active: Boolean, onTrue: String, onFalse: String): String =
    if (active) {
    onTrue
  } else {
    onFalse
  }
}
