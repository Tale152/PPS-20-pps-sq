package view.editor.forms

import controller.editor.EditorController
import view.editor.forms.okButtonListener.EditPathwayOkListener.SelectPathwayOkListener
import view.editor.{Form, FormBuilder}

object EditPathway {

  def showEditPathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node the pathway starts from? (id)")
      .addIntegerField("Which story node the pathway ends to? (id)")
      .get(editorController)
    form.setOkButtonListener(SelectPathwayOkListener(form, editorController))
    form.render()
  }

}
