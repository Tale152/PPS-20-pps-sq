package view.form

import controller.Controller
import view.form.formElements._

import scala.collection.mutable.ListBuffer

/**
 * Factory class used to create a [[view.form.Form]].
 */
case class FormBuilder() {

  private object FormBuilderConst {
    val DefaultSpinnerStart = 0
  }

  private val listBuffer: ListBuffer[FormElement] = ListBuffer()

  def addTextField(label: String, oldText: String = ""): FormBuilder =
    addField(TextInputElement(label, oldText))

  def addTextAreaField(label: String, oldText: String = ""): FormBuilder =
    addField(TextAreaInputElement(label, oldText))

  def addIntegerField(label: String): FormBuilder =
    addField(IntegerInputElement(label))

  def addComboField(label: String, comboElement: List[String]): FormBuilder =
    addField(ComboBoxElement(label, comboElement))

  import FormBuilderConst.DefaultSpinnerStart

  def addSpinnerNumberField(label: String, start: Int = DefaultSpinnerStart): FormBuilder =
    addField(SpinnerNumberElement(label, start))

  private def addField(formElement: FormElement): FormBuilder = {
    listBuffer += formElement
    this
  }

  def get(controller: Controller): Form = Form(controller, listBuffer.toList)

}

