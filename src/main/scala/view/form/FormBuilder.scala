package view.form

import controller.editor.EditorController

import scala.collection.mutable.ListBuffer

object FormBuilder {

  class FormBuilder() {

    private val listBuffer: ListBuffer[FormElement] = ListBuffer()

    def addTextField(label: String): FormBuilder =
      addField(TextInputElement(label))

    def addTextAreaField(label: String, oldText: String = ""): FormBuilder =
      addField(TextAreaInputElement(label, oldText))

    def addIntegerField(label: String): FormBuilder =
      addField(IntegerInputElement(label))

    private def addField(formElement: FormElement): FormBuilder = {
      listBuffer += formElement
      this
    }

    def get(editorController: EditorController): Form = {
      Form(editorController, listBuffer.toList)
    }

  }

  def apply(): FormBuilder = new FormBuilder()

}
