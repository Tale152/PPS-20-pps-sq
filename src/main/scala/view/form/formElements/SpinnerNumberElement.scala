package view.form.formElements

import view.form.formElements.SpinnerNumberElementConst._

import javax.swing.{JSpinner, SpinnerNumberModel}

case class SpinnerNumberElement(textLabel: String, max: Int) extends FormElement(textLabel) {

  private val spinner = new JSpinner(new SpinnerNumberModel(Start, Min, max, Step))
  this.add(spinner)

  override def value: String = spinner.getValue.asInstanceOf[String]
}

private object SpinnerNumberElementConst {
  val Start = 0
  val Min = 0
  val Step = 1
}
