package view.mainMenu.forms

import controller.ApplicationController
import view.form.{Form, OkFormButtonListener}
import view.mainMenu.forms.DeleteStory.showDeleteStoryForm
import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.{SqSwingDialog, SqYesNoSwingDialog}

import java.awt.event.ActionEvent

case class DeleteStoryOkButtonListener(override val form: Form,
                                       applicationController: ApplicationController,
                                       comboElements: List[String])
  extends OkFormButtonListener(form, applicationController) {

  override def performAction(): Unit = {
    SqYesNoSwingDialog("Delete story", "Are you sure to delete the story and all progress?",
      (_: ActionEvent) => {
        applicationController.deleteExistingStory(form.elements.head.value)
        SqSwingDialog("Story deleted", "Story " + form.elements.head.value + " deleted successfully",
          List(SqSwingButton("ok", _ => {})))
        showDeleteStoryForm(applicationController, comboElements.filter(e => e != form.elements.head.value))
      }, _ => {})
  }

  override def inputConditions: List[(Boolean, String)] =
    List((form.elements.head.value != null, "No story is selected"))

  override def stateConditions: List[(Boolean, String)] = List()
}
