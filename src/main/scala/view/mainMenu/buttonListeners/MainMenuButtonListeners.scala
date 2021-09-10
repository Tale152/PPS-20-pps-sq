package view.mainMenu.buttonListeners

import controller.ApplicationController
import view.util.SqFileChooser

import java.awt.event.ActionListener
import javax.swing.JFileChooser

object MainMenuButtonListeners {

  private[mainMenu] abstract class MainMenuButtonListener(val applicationController: ApplicationController)
    extends ActionListener

  abstract class MainMenuChooserButtonListener(override val applicationController: ApplicationController)
    extends MainMenuButtonListener(applicationController) {

    val fileChooser: JFileChooser = SqFileChooser.getFileChooser("Load story")

  }
}
