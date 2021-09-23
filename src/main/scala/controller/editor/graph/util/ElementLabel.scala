package controller.editor.graph.util

import org.graphstream.graph.Element

/**
 * Utility object used to put a label on a GraphStream's Graph element.
 */
object ElementLabel {

  private val LabelAttribute: String = "ui.label"

  /**
   * Used to put a label on a GraphStream's Graph element.
   * @param element the GraphStream's Graph element to put a label on
   * @param flag Boolean used to determine which string to use as the label
   * @param onTrue String that will be used as label if flag is true
   * @param onFalse String that will be used as label if flag is false
   */
  def putLabelOnElement(element: Element, flag: Boolean)(onTrue: String, onFalse: String): Unit = {
    if (flag) {
      element.setAttribute(LabelAttribute, onTrue)
    } else {
      element.setAttribute(LabelAttribute, onFalse)
    }
  }
}
