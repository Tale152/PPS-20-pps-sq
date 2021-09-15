package view.editor.forms

import controller.editor.EditorController
import view.editor.forms.okButtonListener.pathways
import view.form.{Form, FormBuilder}

object DeletePathway {

  def showDeletePathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node the pathway starts from? (id)")
      .addIntegerField("Which story node the pathway ends to? (id)")
      .get(editorController)
    form.setOkButtonListener(pathways.DeletePathwayOkListener(form, editorController))
    form.render()
  }

}
