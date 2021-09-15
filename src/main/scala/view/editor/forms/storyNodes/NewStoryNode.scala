package view.editor.forms.storyNodes

import controller.editor.EditorController
import view.editor.okButtonListener.storyNodes
import view.form.{Form, FormBuilder}

object NewStoryNode {

  def showNewStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node is the starting node? (id)")
      .addTextField("What description should the pathway to the new story node show?")
      .addTextAreaField("What narrative should the new story node show?")
      .get(editorController)
    form.setOkButtonListener(storyNodes.NewStoryNodeOkListener(form, editorController))
    form.render()
  }


}
