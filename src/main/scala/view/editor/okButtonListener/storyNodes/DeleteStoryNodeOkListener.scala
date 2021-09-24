package view.editor.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.forms.storyNodes.DeleteStoryNode.StoryNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListenerUnconditional
import view.form.Form
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

case class DeleteStoryNodeOkListener(override val form: Form, override val controller: EditorController)
  extends EditorOkFormButtonListenerUnconditional(form, controller) {

  override def editorControllerAction(): Unit = {
    def deleteStoryNode(): Unit =
      controller.nodesControls.deleteExistingStoryNode(form.elements(StoryNodeIdIndex).value.toInt)

    if (controller.nodesControls.getStoryNode(form.elements(StoryNodeIdIndex).value.toInt).get.pathways.nonEmpty) {
      SqYesNoSwingDialog("Really delete this node?",
        "All subsequent unreachable nodes will also be deleted in cascade",
        (_: ActionEvent) => deleteStoryNode(),
        (_: ActionEvent) => {})
    } else {
      deleteStoryNode()
    }
  }

}
