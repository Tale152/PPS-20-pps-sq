package view.editor.forms.storyNodes

import controller.editor.EditorController
import view.editor.okButtonListener.storyNodes.EditStoryNodeOkListener.SelectStoryNodeOkListener
import view.form.{Form, FormBuilder}

object EditStoryNode {

  def showEditStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node would you like to edit? (id)")
      .get(editorController)
    form.setOkButtonListener(SelectStoryNodeOkListener(form, editorController))
    form.render()
  }

}
