package view.editor.forms

import controller.editor.EditorController
import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.FilledForm

object EditPathway {

  def showEditPathwayForm(editorController: EditorController): Unit = {

    def showEditPathwayFormFields(startNodeId: Int, endNodeId: Int, oldDescription: String): Unit = {
      val form: FilledForm = new UiBooster()
        .createForm("Edit pathway")
        .addTextArea("What description should the pathway show?", oldDescription)
        .show()
      editorController.editExistingPathway(startNodeId, endNodeId, form.getByIndex(0).asString())
    }

    val form: FilledForm = new UiBooster()
      .createForm("Edit pathway")
      .addText("Which story node the pathway starts from? (id)")
      .addText("Which story node the pathway ends to? (id)")
      .show()
    showEditPathwayFormFields(
      form.getByIndex(0).asInt(),
      form.getByIndex(1).asInt(),
      editorController.getPathway(form.getByIndex(0).asInt(), form.getByIndex(1).asInt()).description
    )
  }

}
