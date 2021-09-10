package view.mainMenu.buttonListeners

import controller.ApplicationController
import view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener
import view.util.scalaQuestSwingComponents.dialog.SqYesNoSwingDialog

import java.awt.event.ActionEvent

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
