package view.mainMenu.buttonListeners

import controller.ApplicationController
import view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

/**
 *  [[view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener]] used for the Quit Button
 *  in the Main Menu.
 * @param applicationController The Application Controller.
 */
case class QuitButtonListener(override val applicationController: ApplicationController)
  extends MainMenuButtonListener(applicationController) {

  override def actionPerformed(e: ActionEvent): Unit = {
    SqYesNoSwingDialog(
      "Exit confirm",
      "Do you really want to exit the game?",
      _ => applicationController.close()
    )
  }
}
