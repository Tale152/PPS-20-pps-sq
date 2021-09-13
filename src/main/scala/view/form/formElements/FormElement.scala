package view.form.formElements

import view.util.scalaQuestSwingComponents.{SqSwingBoxPanel, SqSwingLabel}
import javax.swing._

/**
 * Abstract class that describe a FormElement. Every Form Element has a label that describes it's functionality
 * and can eventually return a value.
 *
 * @param textLabel the label that describe the FormElement.
 */
abstract class FormElement(textLabel: String) extends SqSwingBoxPanel(BoxLayout.Y_AXIS) {

  private val jLabel: SqSwingLabel = SqSwingLabel(textLabel)
  this.add(jLabel)

  /**
   * @return the value of the element, for example the text in case of a TextField.
   */
  def value: String
}
