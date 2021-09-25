package view.mainMenu.buttonListeners

import controller.ApplicationController
import controller.util.ResourceNames.MainDirectory.RootGameDirectory
import controller.util.ResourceNames.storyDirectoryPath
import view.form.{Form, FormBuilder}
import view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener
import view.mainMenu.forms.DeleteStoryConfirmListener

import java.awt.event.ActionEvent
import java.io.File

/**
 *  [[view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener]] used for the Delete Story Button
 *  in the Main Menu.
 * @param applicationController The Application Controller.
 */
case class DeleteStoryButtonListener(override val applicationController: ApplicationController)
  extends MainMenuButtonListener(applicationController) {

  override def actionPerformed(e: ActionEvent): Unit = {
    showDeleteStoryForm(applicationController,
      new File(storyDirectoryPath(RootGameDirectory)).listFiles().toList.map(f => f.getName))
  }

  private def showDeleteStoryForm(applicationController: ApplicationController, comboElements: List[String]): Unit = {
    val form: Form = FormBuilder()
      .addComboField("Select the story to delete:", comboElements)
      .get(applicationController)
    form.setOkButtonListener(DeleteStoryConfirmListener(form, applicationController, comboElements))
    form.render()
  }

}
