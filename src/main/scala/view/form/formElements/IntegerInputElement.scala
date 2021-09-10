package view.form.formElements

import java.text.NumberFormat
import javax.swing.JFormattedTextField
import javax.swing.text.NumberFormatter

/**
 * Form Element that represents a TextField which can only accept integer values.
 *
 * @param textLabel the label that describe the FormElement.
 */
case class IntegerInputElement(textLabel: String) extends FormElement(textLabel) {
  val format: NumberFormat = NumberFormat.getInstance
  val formatter = new NumberFormatter(format)
  formatter.setValueClass(classOf[Integer])
  formatter.setMinimum(0)
  formatter.setMaximum(Integer.MAX_VALUE)
  formatter.setAllowsInvalid(false)
  val jFormattedTextField: JFormattedTextField = new JFormattedTextField(formatter)
  this.add(jFormattedTextField)

  override def value: String = jFormattedTextField.getText
}
