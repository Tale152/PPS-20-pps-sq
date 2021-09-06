package view.editor.forms

import controller.editor.EditorController
import controller.editor.util.FormBuilderUtil.BetterFormBuilder
import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.Form

object DeleteStoryNode {

  def showDeleteStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = new UiBooster()
      .createForm("Delete story node")
      .addInt("Which node you want to delete? (id)")
      .show()
    editorController.deleteExistingStoryNode(form.getByIndex(0).asInt())
  }

}
