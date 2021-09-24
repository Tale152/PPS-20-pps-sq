package view.explorer.okButtonListener.noForms

import controller.ExplorerController
import view.explorer.ExplorerDialogs.NoSolutionDialog
import view.explorer.okButtonListener.ExplorerButtonListener

import java.awt.event.{ActionEvent, ActionListener}

/**
 * Abstract class used to structure the behaviour of the [[java.awt.event.ActionListener]] in the Explorer page
 * that does not require a Form.
 *
 * @param explorerController An ExplorerController.
 */
abstract class ExplorerWithoutFormButtonListener(explorerController: ExplorerController)
  extends ActionListener with ExplorerButtonListener {

  /**
   * Just run the [[view.explorer.okButtonListener.ExplorerButtonListener#performAction()]].
   * No Forms are present here.
   *
   * @param e the action event.
   */
  override def actionPerformed(e: ActionEvent): Unit = performAction()

  override def showError(): Unit = NoSolutionDialog(explorerController)

}

