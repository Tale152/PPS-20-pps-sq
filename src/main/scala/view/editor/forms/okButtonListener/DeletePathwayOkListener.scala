package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.editor.FormConditionValues.ConditionDescriptions.Subjects.{TheEndingId, ThePathway, TheStartingId}
import view.editor.FormConditionValues.ConditionDescriptions.{isNotValid, mustBeSpecified}
import view.editor.FormConditionValues.InputPredicates.NonEmptyString

case class DeletePathwayOkListener(override val form: Form, override val editorController: EditorController)
  extends OkFormButtonListener(form, editorController) {

  override def performAction(): Unit =
    editorController.deleteExistingPathway(form.elements.head.value.toInt, form.elements(1).value.toInt)

  override def inputConditions: List[(Boolean, String)] = {
    List(
      (NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)),
      (NonEmptyString(form.elements(1).value), mustBeSpecified(TheEndingId))
    )
  }

  override def stateConditions: List[(Boolean, String)] =
    List(
        (editorController.pathwayExists(form.elements.head.value.toInt, form.elements(1).value.toInt),
        isNotValid(ThePathway))
    )
}
