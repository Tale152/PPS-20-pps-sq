package controller.editor.graph.util

import org.graphstream.graph.Element

object ElementLabel {

  private val LabelAttribute: String = "ui.label"

  def putLabelOnElement(element: Element, flag: Boolean)(onTrue: String, onFalse: String): Unit = {
    if (flag) {
      element.setAttribute(LabelAttribute, onTrue)
    }
    else {
      element.setAttribute(LabelAttribute, onFalse)
    }
  }
}
