package view.editor.forms.storyNodes

import controller.editor.EditorController
import view.editor.EditorView
import view.editor.okButtonListener.storyNodes.DeleteStoryNodeOkListener
import view.form.{Form, FormBuilder}

object DeleteStoryNode {

  def showDeleteStoryNodeForm(editorController: EditorController): Unit = {
    if(editorController.getNodesIds(n => editorController.isStoryNodeDeletable(n.id)).nonEmpty){
      val form: Form = FormBuilder()
        .addComboField(
          "Which node you want to delete? (id)",
          editorController.getNodesIds(n => editorController.isStoryNodeDeletable(n.id)).map(id => id.toString)
        )
        .get(editorController)
      form.setOkButtonListener(DeleteStoryNodeOkListener(form, editorController))
      form.render()
    } else {
      EditorView.showForbiddenActionDialog("There aren't deletable nodes")
    }
  }

}
