package view.editor.forms

import controller.editor.EditorController
import view.editor.{Form, FormBuilder}

import java.awt.event.ActionEvent

object NewStoryNode {

  def showNewStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addTextField("Which story node is the starting node? (id)")
      .get(editorController)
    form.setOkButtonListener((_: ActionEvent) => println(form.elements.head.value))
    form.render()

    /*
    val form: Form = new UiBooster()
      .createForm("Add new story node")
      .addInt("Which story node is the starting node? (id)")
      .addTextArea("What description should the pathway to the new story node show?")
      .addTextArea("What narrative should the new story node show?")
      .show()
      editorController.addNewStoryNode(
        form.getByIndex(0).asInt(),
        form.getByIndex(1).asString(),
        form.getByIndex(2).asString()
      )
      */

  }

}
