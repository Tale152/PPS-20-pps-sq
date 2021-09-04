package view.editor.forms

import controller.editor.EditorController
import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.FilledForm

object NewStoryNode {

  def showNewStoryNodeForm(editorController: EditorController): Unit = {
    val form: FilledForm = new UiBooster()
      .createForm("Add new story node")
      .addText("Which story node is the starting node? (id)")
      .addTextArea("What description should the pathway to the new story node show?")
      .addTextArea("What narrative should the new story node show?")
      .show()
    editorController.addNewStoryNode(
      form.getByIndex(0).asInt(),
      form.getByIndex(1).asString(),
      form.getByIndex(2).asString()
    )
  }

}
