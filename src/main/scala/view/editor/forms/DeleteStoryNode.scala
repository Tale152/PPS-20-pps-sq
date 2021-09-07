package view.editor.forms

import controller.editor.EditorController
import view.editor.FormConditionValues.ConditionDescriptions.InvalidIDMessage
import view.editor.FormConditionValues.Conditions.NonEmptyString
import view.editor.{Form, FormBuilder, OkFormButtonListener}

object DeleteStoryNode {

  def showDeleteStoryNodeForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which node you want to delete? (id)")
      .get(editorController)
    form.setOkButtonListener(new OkFormButtonListener {
      /**
       * The action to perform in case of success.
       *
       * @see [[view.editor.OkFormButtonListener#approvalCondition()]]
       */
      override def performAction(): Unit = editorController.deleteExistingStoryNode(form.elements.head.value.toInt)

      /**
       * Specify the conditions and describe them.
       * If ALL are satisfied (&&) call [[view.editor.OkFormButtonListener#performAction()]].
       *
       * @return a List containing a condition and it's textual description.
       */
      override def conditions: List[(Boolean, String)] = List(
        (NonEmptyString(form.elements.head.value), InvalidIDMessage)
        //TODO check that node is present, place it inside [[view.editor.FormConditionValues.ConditionDescriptions]]
      )
    })
    form.render()
  }

}
