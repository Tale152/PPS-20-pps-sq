package view.editor.forms

import controller.editor.EditorController
import view.editor.FormConditionValues.ConditionDescriptions._
import view.editor.FormConditionValues.Conditions.NonEmptyString
import view.editor.{Form, FormBuilder, OkFormButtonListener}

object NewStoryNode {

  def showNewStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node is the starting node? (id)")
      .addTextField("What description should the pathway to the new story node show?")
      .addTextAreaField("What narrative should the new story node show?")
      .get(editorController)
    form.setOkButtonListener(
      new OkFormButtonListener {

         override def performAction(): Unit = {
            editorController.addNewStoryNode(
              form.elements.head.value.toInt,
              form.elements(1).value,
              form.elements(2).value
            )
           editorController.execute()
         }

        override def conditions: List[(Boolean, String)] = {
          List(
            (NonEmptyString(form.elements.head.value), InvalidIDMessage),
            (NonEmptyString(form.elements(1).value), InvalidDescriptionMessage),
            (NonEmptyString(form.elements(2).value), InvalidNarrativeMessage),
            (
              editorController.getStoryNode(form.elements.head.value.toInt).nonEmpty,
              nodeDoesNotExist(form.elements.head.value.toInt)
            )
          )
        }
      })
    form.render()
  }


}
