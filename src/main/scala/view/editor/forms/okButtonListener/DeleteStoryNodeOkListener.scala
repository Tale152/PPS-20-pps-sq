package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.forms.EditorConditionValues.ConditionDescriptions.Subjects.{TheId, TheStoryNode}
import view.editor.forms.EditorConditionValues.ConditionDescriptions.{doesNotExists, mustBeSpecified}
import view.editor.forms.EditorConditionValues.InputPredicates.NonEmptyString
import view.form.Form
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

case class DeleteStoryNodeOkListener(override val form: Form, override val editorController: EditorController)
  extends EditorOkFormButtonListener(form, editorController) {

  override def editorControllerAction(): Unit = {
    if (editorController.getStoryNode(form.elements.head.value.toInt).pathways.nonEmpty) {
      SqYesNoSwingDialog("Really delete this node?",
        "All the subsequent nodes will also be deleted in cascade",
        (_: ActionEvent) => _deleteStoryNode(),
        (_: ActionEvent) => {})
    } else {
      _deleteStoryNode()
    }

    def _deleteStoryNode(): Unit = editorController.deleteExistingStoryNode(form.elements.head.value.toInt)
  }

  override def inputConditions: List[(Boolean, String)] =
    List(
      (NonEmptyString(form.elements.head.value), mustBeSpecified(TheId))
    )

  override def stateConditions: List[(Boolean, String)] =
    List(
      (editorController.storyNodeExists(form.elements.head.value.toInt), doesNotExists(TheStoryNode)),
      (form.elements.head.value.toInt != 0, "The original starting node can't be deleted")
    )

}


