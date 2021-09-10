package view.form.formElements

import javax.swing.JComboBox

/**
 * Form Element that represents a ComboBoxElement.
 *
 * @param textLabel the label that describe the FormElement.
 */
case class ComboBoxElement(textLabel: String, comboElements: List[String]) extends FormElement(textLabel) {
  val comboBox = new JComboBox[String]()
  comboElements.foreach(e => comboBox.addItem(e))
  this.add(comboBox)

  override def value: String = comboBox.getSelectedItem.asInstanceOf[String]
}
