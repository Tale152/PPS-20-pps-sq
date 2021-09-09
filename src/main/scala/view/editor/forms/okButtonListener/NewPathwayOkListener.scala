package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form

case class NewPathwayOkListener(override val form: Form, override val editorController: EditorController)
  extends EditorOkFormButtonListener(form, editorController) {

  override def editorControllerAction(): Unit =
    editorController.addNewPathway(
      form.elements.head.value.toInt,
      form.elements(1).value.toInt,
      form.elements(2).value)

  override def inputConditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements.head.value), mustBeSpecified(TheStartingId)),
      (NonEmptyString(form.elements(1).value), mustBeSpecified(TheEndingId)),
      (NonEmptyString(form.elements(2).value), mustBeSpecified(TheDescription))
    )

  override def stateConditions: List[(Boolean, String)] = List(
    (editorController.storyNodeExists(form.elements.head.value.toInt), doesNotExists(TheStartingStoryNode)),
    (editorController.storyNodeExists(form.elements(1).value.toInt), doesNotExists(TheEndStoryNode)),
    checkLoop(form.elements.head.value.toInt, form.elements(1).value.toInt)
  )

  def checkLoop(startId: Int, endId: Int): (Boolean, String) = {
    if (editorController.storyNodeExists(startId) && editorController.storyNodeExists(endId)) {
      (editorController.isNewPathwayValid(form.elements.head.value.toInt, form.elements(1).value.toInt),
        "Creating this pathway results in a loop in the story, cannot create this pathway.")
    } else {
      (true, "")
    }
  }

}
