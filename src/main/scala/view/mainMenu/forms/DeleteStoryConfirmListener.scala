package view.mainMenu.forms

import controller.ApplicationController
import view.form.{Form, OkFormButtonListener}
import view.mainMenu.forms.DeleteStory.showDeleteStoryForm
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.{SqSwingDialog, SqYesNoSwingDialog}

import java.awt.event.ActionEvent

/**
 * [[view.form.OkFormButtonListener]]used to confirm the intent of deleting a selected story.
 * @param form the Form.
 * @param applicationController the Application Controller.
 * @param stories a List containing the stories that are possible to delete.
 */
case class DeleteStoryConfirmListener(override val form: Form,
                                      applicationController: ApplicationController,
                                      stories: List[String])
  extends OkFormButtonListener(form, applicationController) {

  override def performAction(): Unit = {
    SqYesNoSwingDialog("Delete story", "Are you sure to delete the story and all progress?",
      (_: ActionEvent) => {
        applicationController.deleteExistingStory(form.elements.head.value)
        SqSwingDialog("Story deleted", "Story " + form.elements.head.value + " deleted successfully",
          List(SqSwingButton("ok", _ => {})))
        showDeleteStoryForm(applicationController, stories.filter(e => e != form.elements.head.value))
      }, _ => {})
  }

  override def inputConditions: List[(Boolean, String)] =
    List((form.elements.head.value != null, "No story is selected"))

  override def stateConditions: List[(Boolean, String)] = List()

}
