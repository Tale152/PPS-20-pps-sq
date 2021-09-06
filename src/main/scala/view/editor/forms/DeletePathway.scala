package view.editor.forms

import controller.editor.EditorController
import controller.editor.util.FormBuilderUtil.BetterFormBuilder
import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.Form

object DeletePathway {

  def showDeletePathwayForm(editorController: EditorController): Unit = {
    val form: Form = new UiBooster()
      .createForm("Delete pathway")
      .addInt("Which story node the pathway starts from? (id)")
      .addInt("Which story node the pathway ends to? (id)")
      .show()
    editorController.deleteExistingPathway(form.getByIndex(0).asInt(), form.getByIndex(1).asInt())
  }

}
