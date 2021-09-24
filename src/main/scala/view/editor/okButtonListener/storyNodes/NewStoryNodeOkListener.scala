package view.editor.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects._
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.EditorConditionValues.InputPredicates.NonEmptyString
import view.editor.forms.storyNodes.NewStoryNode.{NewNodeNarrativeIndex, NewPathwayDescriptionIndex, StartingNodeIndex}
import view.editor.okButtonListener.EditorOkFormButtonListenerStateless
import view.form.Form

case class NewStoryNodeOkListener(override val form: Form, override val controller: EditorController)
  extends EditorOkFormButtonListenerStateless(form, controller) {

  override def editorControllerAction(): Unit =
    controller.nodesControls.addNewStoryNode(
      form.elements(StartingNodeIndex).value.toInt,
      form.elements(NewPathwayDescriptionIndex).value,
      form.elements(NewNodeNarrativeIndex).value
    )

  override def inputConditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements(NewPathwayDescriptionIndex).value), mustBeSpecified(TheDescription)),
      (NonEmptyString(form.elements(NewNodeNarrativeIndex).value), mustBeSpecified(TheNarrative))
    )
}
