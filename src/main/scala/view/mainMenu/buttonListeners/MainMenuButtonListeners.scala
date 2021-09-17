package view.mainMenu.buttonListeners

import controller.ApplicationController
import view.util.scalaQuestSwingComponents.SqSwingFileChooser

import java.awt.event.ActionListener
import javax.swing.JFileChooser

/**
 * Object that contains the [[java.awt.event.ActionListener]] abstract classes used to create the
 * MainMenu Button Listeners.
 */
object MainMenuButtonListeners {

  /**
   * A basic [[java.awt.event.ActionListener]] that uses a [[controller.ApplicationController]] in its
   * [[java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)]] method.
   * @param applicationController The Application Controller.
   */
  private[mainMenu] abstract class MainMenuButtonListener(val applicationController: ApplicationController)
    extends ActionListener

  /**
   * A [[view.mainMenu.buttonListeners.MainMenuButtonListeners.MainMenuButtonListener]] that also has a
   * [[javax.swing.JFileChooser]] used to load a Story.
   * @param applicationController The Application Controller.
   */
  private[mainMenu] abstract class LoadStoryChooserButtonListener
    (override val applicationController: ApplicationController)
    extends MainMenuButtonListener(applicationController) {

    val loadStoryFileChooser: JFileChooser = SqSwingFileChooser("Load story")

  }
}
