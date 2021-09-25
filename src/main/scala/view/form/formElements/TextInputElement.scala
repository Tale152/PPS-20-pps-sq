package view.form.formElements

import javax.swing.JTextField

/**
 * Form Element that represents a TextField.
 *
 * @param textLabel the label that describe the FormElement.
 */
case class TextInputElement(textLabel: String, oldText: String) extends FormElement(textLabel) {

  val jTextField: JTextField = new JTextField()
  jTextField.setText(oldText)
  this.add(jTextField)

  override def value: String = jTextField.getText
}
