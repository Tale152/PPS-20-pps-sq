package controller.editor.util

import de.milchreis.uibooster.model.formelements.TextFormElement

class IntFormElement(val labelText: String) extends TextFormElement(labelText, "", false) {

  override def setValue(value: Any): Unit = {
    if(isNumeric(value.toString)){
      super.setValue(value)
    }
  }

  def isNumeric(strNum: String): Boolean = {
    import java.util.regex.Pattern
    if (strNum != null) {
      Pattern.compile("-?\\d+(\\.\\d+)?").matcher(strNum).matches
    }
    false
  }
}
