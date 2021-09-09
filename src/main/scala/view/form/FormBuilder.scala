package view.form

import controller.Controller
import scala.collection.mutable.ListBuffer

/**
 * Factory Object used to create a [[view.form.Form]].
 */
object FormBuilder {

  class FormBuilder() {

    private val listBuffer: ListBuffer[FormElement] = ListBuffer()

    def addTextField(label: String): FormBuilder =
      addField(TextInputElement(label))

    def addTextAreaField(label: String, oldText: String = ""): FormBuilder =
      addField(TextAreaInputElement(label, oldText))

    def addIntegerField(label: String): FormBuilder =
      addField(IntegerInputElement(label))

    def addComboField(label: String, comboElement: List[String]): FormBuilder =
      addField(ComboBoxElement(label, comboElement))

    private def addField(formElement: FormElement): FormBuilder = {
      listBuffer += formElement
      this
    }

    def get(controller: Controller): Form = Form(controller, listBuffer.toList)

  }

  def apply(): FormBuilder = new FormBuilder()

}
