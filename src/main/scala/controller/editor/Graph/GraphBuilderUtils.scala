package controller.editor.Graph

protected object GraphBuilderUtils {

  private val MaxStringLength: Int = 20

  def buildLabel(id: String, narrative: String): String = "[" + id + "] " + narrative

  def truncateString(s: String): String = {
    var maxChar = s.substring(0, Math.min(s.length(), MaxStringLength))
    if (maxChar.length < s.length && maxChar.contains(" ")){
      maxChar = maxChar.substring(0, maxChar.lastIndexOf(" ")) + "..."
    }
    maxChar
  }

  def getEdgeStyle(color: String): String = "fill-color: " + color + ";"

  def getCorrectEdgeColor(active: Boolean, onTrue: String, onFalse: String): String =
    if (active) {
      getEdgeStyle(onTrue)
    } else {
      getEdgeStyle(onFalse)
    }

  def getNodeStyle(shape: String): String =
    "shape:" + shape + "; text-alignment: under; size: 20px;"

  def getCorrectNodeShape(active: Boolean, onTrue: String, onFalse: String): String =
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
