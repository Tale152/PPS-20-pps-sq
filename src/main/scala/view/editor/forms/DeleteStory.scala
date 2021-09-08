package view.editor.forms

import controller.ApplicationController
import view.editor.{Form, FormBuilder, OkFormButtonListener}
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

object DeleteStory {

  def showDeleteStoryForm(applicationController: ApplicationController, comboElements: List[String]): Unit = {
    val form: Form = FormBuilder()
      .addComboField("Select the story to delete:", comboElements)
      .get(applicationController)


    form.setOkButtonListener(new OkFormButtonListener {
      /**
       * The action to perform in case of success.
       *
       * @see [[view.editor.OkFormButtonListener#approvalCondition()]]
       */
      override def performAction(): Unit = {
        SqYesNoSwingDialog("Delete story", "Are you sure to delete the story and all progress?",
          (_: ActionEvent) => {
            applicationController.deleteExistingStory(form.elements.head.value)
            showDeleteStoryForm(applicationController, comboElements.filter(e => e != form.elements.head.value))
          }, _ => {})
      }

      /**
       * Specify the conditions and describe them.
       * If ALL are satisfied (&&) call [[view.editor.OkFormButtonListener#performAction()]].
       *
       * @return a List containing a condition and it's textual description.
       */
      override def conditions: List[(Boolean, String)] = {
        List((form.elements.head.value != null, "No story is selected"))
      }
    })

    form.render()
  }

}
