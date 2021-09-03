package view.editor.forms

import controller.editor.EditorController
import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.FilledForm

object DeleteStoryNode {

  def showDeleteStoryNodeForm(editorController: EditorController): Unit = {
    val form: FilledForm = new UiBooster()
      .createForm("Delete story node")
      .addText("Which node you want to delete? (id)")
      .show()
    editorController.deleteExistingStoryNode(form.getByIndex(0).asInt())
  }

}
