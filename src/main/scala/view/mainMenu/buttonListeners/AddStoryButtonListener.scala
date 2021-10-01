package view.mainMenu.buttonListeners

import controller.ApplicationController
import view.mainMenu.MainMenu
import view.mainMenu.buttonListeners.MainMenuButtonListeners.AddStoryChooserButtonListener
import java.awt.event.ActionEvent
import java.io.File

import controller.util.ResourceNames.FileExtensions.StoryFileExtension
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * [[view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener]] used for the Add Story Button
 * in the Main Menu.
 *
 * @param applicationController The Application Controller.
 * @param mainMenu              the reference to the Main Menu.
 */
case class AddStoryButtonListener(override val applicationController: ApplicationController, mainMenu: MainMenu)
  extends AddStoryChooserButtonListener(applicationController) {

  override def actionPerformed(e: ActionEvent): Unit = {
    addStoryFileChooser.setFileFilter(new FileNameExtensionFilter(StoryFileExtension.toUpperCase, StoryFileExtension))
    addStoryFileChooser.showOpenDialog(mainMenu)
    val file: Option[File] = Option(addStoryFileChooser.getSelectedFile)
    if (file.nonEmpty) {
      applicationController.addNewStory(file.get)
    }
  }

}
