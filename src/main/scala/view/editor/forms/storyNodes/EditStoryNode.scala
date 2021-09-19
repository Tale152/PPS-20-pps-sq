package view.editor.forms.storyNodes

import controller.editor.EditorController
import view.editor.okButtonListener.storyNodes.EditStoryNodeOkListener
import view.form.{Form, FormBuilder}

object EditStoryNode {

  val NodeToEditIdIndex: Int = 0

  def showEditStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which story node would you like to edit? (id)",
        editorController.nodesControls.getNodesIds(_ => true).map(id => id.toString)
      )
      .get(editorController)
    form.setOkButtonListener(EditStoryNodeOkListener(form, editorController))
    form.render()
  }

}
