package view.editor.forms.storyNodes

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.forms.EditorForm
import view.editor.okButtonListener.storyNodes.DeleteStoryNodeOkListener
import view.form.{Form, FormBuilder}

object DeleteStoryNode {
  val StoryNodeIdIndex: Integer = 0
}

/** @inheritdoc */
case class DeleteStoryNode() extends EditorForm {

  override def show(editorController: EditorController): Unit = {
    val targetNodes = editorController.nodesControls.getNodesIds(n =>
      editorController.nodesControls.isStoryNodeDeletable(n.id)
    )
    if(targetNodes.nonEmpty){
      val form: Form = FormBuilder()
        .addComboField(
          "Which node you want to delete? (id)",
          targetNodes.map(id => id.toString)
        )
        .get(editorController)
      form.setOkButtonListener(DeleteStoryNodeOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There aren't deletable nodes")
    }
  }

}
