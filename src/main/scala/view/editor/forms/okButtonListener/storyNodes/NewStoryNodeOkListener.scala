package view.editor.forms.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.okButtonListener.EditorOkFormButtonListener
import view.form.Form

case class NewStoryNodeOkListener(override val form: Form, override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit =
    controller.addNewStoryNode(
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
    List((controller.getStoryNode(form.elements.head.value.toInt).isDefined, doesNotExists(TheStoryNode)))

}
