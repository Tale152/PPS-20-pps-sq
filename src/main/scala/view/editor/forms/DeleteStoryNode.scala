package view.editor.forms

import controller.editor.EditorController
import view.editor.forms.okButtonListener.DeleteStoryNodeOkListener
import view.editor.{Form, FormBuilder}

object DeleteStoryNode {

  def showDeleteStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which node you want to delete? (id)")
      .get(editorController)
    form.setOkButtonListener(DeleteStoryNodeOkListener(form, editorController))
    form.render()
  }

}
