package view.editor.okButtonListener.storyNodes

import controller.editor.EditorController
import view.editor.forms.storyNodes.DeleteStoryNode.StoryNodeIdIndex
import view.editor.okButtonListener.EditorOkFormButtonListenerUnconditional
import view.form.Form
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog


case class DeleteStoryNodeOkListener(override val form: Form, override val controller: EditorController)
  extends EditorOkFormButtonListenerUnconditional(form, controller) {

  private def deleteStoryNode(): Unit =
    controller.nodesControls.deleteExistingStoryNode(form.elements(StoryNodeIdIndex).value.toInt)

  override def editorControllerAction(): Unit =
    if (controller.nodesControls.storyNode(form.elements(StoryNodeIdIndex).value.toInt).get.pathways.nonEmpty) {
      SqYesNoSwingDialog(
        "Really delete this node?",
        "All subsequent unreachable nodes will also be deleted in cascade",
        _ => deleteStoryNode(),
        _ => { /*user chooses no, does nothing*/ }
      )
    } else {
      deleteStoryNode()
    }
}
