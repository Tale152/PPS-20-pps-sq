package view.editor.forms

import controller.editor.EditorController
import view.editor.forms.okButtonListener.pathways
import view.form.{Form, FormBuilder}

object NewPathway {

  def showNewPathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node is the starting node? (id)")
      .addIntegerField("Which story node is the ending node? (id)")
      .addTextAreaField("What description should the pathway show?")
      .get(editorController)
    form.setOkButtonListener(pathways.NewPathwayOkListener(form, editorController))
    form.render()
  }
}
