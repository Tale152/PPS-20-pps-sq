package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects.{TheEndingId, ThePathway, TheStartingId}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{isNotValid, mustBeSpecified}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form

case class DeletePathwayOkListener(override val form: Form, override val editorController: EditorController)
  extends EditorOkFormButtonListener(form, editorController) {

  override def editorControllerAction(): Unit =
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
