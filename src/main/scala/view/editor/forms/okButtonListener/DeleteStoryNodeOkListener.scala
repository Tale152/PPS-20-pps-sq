package view.editor.forms.okButtonListener

import controller.editor.EditorController
import view.editor.Form
import view.editor.FormConditionValues.ConditionDescriptions.{InvalidIDMessage, StoryNodeDoesNotExists}
import view.editor.FormConditionValues.Conditions.NonEmptyString
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

case class DeleteStoryNodeOkListener(override val form: Form, override val editorController: EditorController)
  extends OkFormButtonListener(form, editorController) {

  override def performAction(): Unit = {
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

  override def conditions: List[(Boolean, String)] = List(
    (NonEmptyString(form.elements.head.value), InvalidIDMessage),
    (editorController.storyNodeExists(form.elements.head.value.toInt), StoryNodeDoesNotExists),
    (form.elements.head.value.toInt != 0, "The original starting node can't be deleted")
  )
}


