package view.form

import controller.Controller
import view.form.FormBuilderConst.DefaultSpinnerNumberMax
import view.form.formElements._

import scala.collection.mutable.ListBuffer

/**
 * Factory class used to create a [[view.form.Form]].
 */
case class FormBuilder() {

  private val listBuffer: ListBuffer[FormElement] = ListBuffer()

  def addTextField(label: String): FormBuilder =
    addField(TextInputElement(label))

  def addTextAreaField(label: String, oldText: String = ""): FormBuilder =
    addField(TextAreaInputElement(label, oldText))

  def addIntegerField(label: String): FormBuilder =
    addField(IntegerInputElement(label))

  def addComboField(label: String, comboElement: List[String]): FormBuilder =
    addField(ComboBoxElement(label, comboElement))

  def addSpinnerNumberField(label: String, max: Int = DefaultSpinnerNumberMax): FormBuilder =
    addField(SpinnerNumberElement(label, max))

  private def addField(formElement: FormElement): FormBuilder = {
    listBuffer += formElement
    this
  }

  def get(controller: Controller): Form = Form(controller, listBuffer.toList)

}

private object FormBuilderConst {
  val DefaultSpinnerNumberMax = 1000
}
