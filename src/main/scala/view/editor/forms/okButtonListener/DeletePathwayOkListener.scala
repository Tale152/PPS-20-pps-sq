package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.editor.FormConditionValues.ConditionDescriptions.{InvalidEndingIDMessage, InvalidStartingIDMessage}
import view.editor.FormConditionValues.Conditions.NonEmptyString

case class DeletePathwayOkListener(override val form: Form, override val editorController: EditorController)
  extends OkFormButtonListener(form, editorController) {

  override def performAction(): Unit =
    editorController.deleteExistingPathway(form.elements.head.value.toInt, form.elements(1).value.toInt)

  override def conditions: List[(Boolean, String)] = List(
      nodeIdConditions(),
      (NonEmptyString(form.elements(1).value), InvalidEndingIDMessage),
    )

  def nodeIdConditions(): (Boolean, String) = {
    val nonEmptyID: Boolean = NonEmptyString(form.elements.head.value)
    if (!nonEmptyID) {
      (nonEmptyID, InvalidStartingIDMessage)
    } else {
      (editorController.pathwayExists(form.elements.head.value.toInt, form.elements(1).value.toInt),
        "The chosen Pathway is not valid.")
    }
  }

}
