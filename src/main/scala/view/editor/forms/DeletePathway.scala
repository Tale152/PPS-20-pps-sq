package view.editor.forms

import controller.editor.EditorController
import view.editor.FormConditionValues.ConditionDescriptions.{InvalidEndingIDMessage, InvalidStartingIDMessage}
import view.editor.FormConditionValues.Conditions.NonEmptyString
import view.editor.{Form, FormBuilder, OkFormButtonListener}

object DeletePathway {

  def showDeletePathwayForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Which story node the pathway starts from? (id)")
      .addIntegerField("Which story node the pathway ends to? (id)")
      .get(editorController)
    form.setOkButtonListener(new OkFormButtonListener {
      /**
       * The action to perform in case of success.
       *
       * @see [[view.editor.OkFormButtonListener#approvalCondition()]]
       */
      override def performAction(): Unit =
        editorController.deleteExistingPathway(form.elements.head.value.toInt, form.elements(1).value.toInt)

      /**
       * Specify the conditions and describe them.
       * If ALL are satisfied (&&) call [[view.editor.OkFormButtonListener#performAction()]].
       *
       * @return a List containing a condition and it's textual description.
       */
      override def conditions: List[(Boolean, String)] = List(
        (NonEmptyString(form.elements.head.value), InvalidStartingIDMessage),
        (NonEmptyString(form.elements(1).value), InvalidEndingIDMessage),
      )
    })
    form.render()
  }

}
