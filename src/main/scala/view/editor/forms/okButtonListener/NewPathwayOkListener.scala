package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form

case class NewPathwayOkListener(override val form: Form, override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit =
    controller.addNewPathway(
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
    (controller.getStoryNode(form.elements.head.value.toInt).isDefined, doesNotExists(TheStartingStoryNode)),
    (controller.getStoryNode(form.elements(1).value.toInt).isDefined, doesNotExists(TheEndStoryNode)),
    checkLoop(form.elements.head.value.toInt, form.elements(1).value.toInt)
  )

  def checkLoop(startId: Int, endId: Int): (Boolean, String) = {
    if (controller.getStoryNode(startId).isDefined && controller.getStoryNode(endId).isDefined) {
      (controller.isNewPathwayValid(startId, endId),
        "Creating this pathway results in a loop in the story, cannot create this pathway.")
    } else {
      (true, "")
    }
  }

}
