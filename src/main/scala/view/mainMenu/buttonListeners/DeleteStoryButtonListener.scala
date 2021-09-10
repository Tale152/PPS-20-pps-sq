package view.mainMenu.buttonListeners

import controller.ApplicationController
import controller.util.Resources.ResourceName.MainDirectory.RootGameDirectory
import controller.util.Resources.ResourceName.storyDirectoryPath
import view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener
import view.mainMenu.forms.DeleteStory.showDeleteStoryForm

import java.awt.event.ActionEvent
import java.io.File

case class DeleteStoryButtonListener(override val applicationController: ApplicationController)
  extends MainMenuButtonListener(applicationController) {

  override def actionPerformed(e: ActionEvent): Unit = {
    showDeleteStoryForm(applicationController,
      new File(storyDirectoryPath(RootGameDirectory)).listFiles().toList.map(f => f.getName))
  }

}
