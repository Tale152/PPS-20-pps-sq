package view.form.formElements

import view.form.formElements.SpinnerNumberElementConst._

import javax.swing.event.{ChangeEvent, ChangeListener}
import javax.swing.{JFormattedTextField, JSpinner, SpinnerNumberModel}

case class SpinnerNumberElement(textLabel: String, start: Int) extends FormElement(textLabel) {

  private val spinner = new JSpinner(new SpinnerNumberModel(start, Min, Max, Step))
  //disabling keyboard input preventing problems related to focus and missed value update when forum confirm
  spinner.getEditor.asInstanceOf[JSpinner.DefaultEditor].getTextField.setEditable(false)
  this.add(spinner)

  //strange casting, but won't work if directly casted to String
  override def value: String = spinner.getValue.asInstanceOf[Integer].toString
}

private object SpinnerNumberElementConst {
  val Min: Integer = 0
  val Max: Integer = Integer.MAX_VALUE
  val Step: Integer = 1
}
