package view.editor.forms

import controller.editor.EditorController
import view.editor.FormConditionValues.ConditionDescriptions.{InvalidDescriptionMessage, InvalidEndingIDMessage, InvalidStartingIDMessage}
import view.editor.FormConditionValues.Conditions.NonEmptyString
import view.editor.{Form, FormBuilder, OkFormButtonListener}

object NewPathway {

  def showNewPathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node is the starting node? (id)")
      .addIntegerField("Which story node is the ending node? (id)")
      .addTextAreaField("What description should the pathway show?")
      .get(editorController)
    form.setOkButtonListener(new OkFormButtonListener {
      /**
       * The action to perform in case of success.
       *
       * @see [[view.editor.OkFormButtonListener#approvalCondition()]]
       */
      override def performAction(): Unit = {
        editorController.addNewPathway(
          form.elements.head.value.toInt,
          form.elements(1).value.toInt,
          form.elements(2).value)
      }

      /**
       * Specify the conditions and describe them.
       * If ALL are satisfied (&&) call [[view.editor.OkFormButtonListener#performAction()]].
       *
       * @return a List containing a condition and it's textual description.
       */
      override def conditions: List[(Boolean, String)] =
        List(
          (NonEmptyString(form.elements.head.value), InvalidStartingIDMessage),
          (NonEmptyString(form.elements(1).value), InvalidEndingIDMessage),
          (NonEmptyString(form.elements(2).value), InvalidDescriptionMessage),
          (editorController.isNewPathwayValid(form.elements.head.value.toInt, form.elements(1).value.toInt),
            "Creating this pathway results in a loop in the story, cannot create this pathway"),
        )
    })
    form.render()
  }
}
