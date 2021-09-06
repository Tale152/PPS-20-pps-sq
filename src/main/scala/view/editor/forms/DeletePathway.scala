package view.editor.forms

import controller.editor.EditorController
import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.Form

object DeletePathway {

  def showDeletePathwayForm(editorController: EditorController): Unit = {
    val form: Form = new UiBooster()
      .createForm("Delete pathway")
      .addText("Which story node the pathway starts from? (id)")
      .addText("Which story node the pathway ends to? (id)")
      .show()
    editorController.deleteExistingPathway(form.getByIndex(0).asInt(), form.getByIndex(1).asInt())
  }

}
