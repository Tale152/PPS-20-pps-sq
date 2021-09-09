package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.editor.FormConditionValues.ConditionDescriptions.Subjects._
import view.editor.FormConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.FormConditionValues.InputPredicates.NonEmptyString

case class NewStoryNodeOkListener(override val form: Form, override val editorController: EditorController)
  extends EditorOkFormButtonListener(form, editorController) {

  override def editorControllerAction(): Unit =
    editorController.addNewStoryNode(
      form.elements.head.value.toInt,
      form.elements(1).value,
      form.elements(2).value
    )

  override def inputConditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements.head.value), mustBeSpecified(TheId)),
      (NonEmptyString(form.elements(1).value), mustBeSpecified(TheDescription)),
      (NonEmptyString(form.elements(2).value), mustBeSpecified(TheNarrative)),
    )

  override def stateConditions: List[(Boolean, String)] =
    List((editorController.storyNodeExists(form.elements.head.value.toInt), doesNotExists(TheStoryNode)))

}
