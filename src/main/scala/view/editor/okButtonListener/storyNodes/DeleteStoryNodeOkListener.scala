package view.editor.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.EditorConditionValues.ConditionDescriptions.Subjects.TheId
import view.editor.EditorConditionValues.ConditionDescriptions.mustBeSpecified
import view.editor.okButtonListener.EditorOkFormButtonListener
import view.form.Form
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

case class DeleteStoryNodeOkListener(override val form: Form, override val controller: EditorController)
  extends EditorOkFormButtonListener(form, controller) {

  override def editorControllerAction(): Unit = {
    def deleteStoryNode(): Unit = controller.nodesControls.deleteExistingStoryNode(form.elements.head.value.toInt)

    if (controller.nodesControls.getStoryNode(form.elements.head.value.toInt).get.pathways.nonEmpty) {
      SqYesNoSwingDialog("Really delete this node?",
        "All subsequent unreachable nodes will also be deleted in cascade",
        (_: ActionEvent) => deleteStoryNode(),
        (_: ActionEvent) => {})
    } else {
      deleteStoryNode()
    }
  }

  override def inputConditions: List[(Boolean, String)] =
    List((form.elements.head.value != null, mustBeSpecified(TheId)))

  override def stateConditions: List[(Boolean, String)] = List()
}
