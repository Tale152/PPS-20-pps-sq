package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.editor.FormConditionValues.ConditionDescriptions._
import view.editor.FormConditionValues.Conditions.NonEmptyString

case class NewPathwayOkListener(override val form: Form, override val editorController: EditorController)
  extends OkFormButtonListener(form, editorController) {

  override def performAction(): Unit = {
    editorController.addNewPathway(
      form.elements.head.value.toInt,
      form.elements(1).value.toInt,
      form.elements(2).value)
  }

  override def conditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements.head.value), InvalidStartingIDMessage),
      (NonEmptyString(form.elements(1).value), InvalidEndingIDMessage),
      (NonEmptyString(form.elements(2).value), InvalidDescriptionMessage),
      (editorController.isNewPathwayValid(form.elements.head.value.toInt, form.elements(1).value.toInt),
        "Creating this pathway results in a loop in the story, cannot create this pathway"),
    )
}
