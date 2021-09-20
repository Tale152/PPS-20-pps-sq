package view.info.okButtonListener.noForms

import controller.InfoController
import view.info.dialog.InfoDialogs.NoSolutionDialog
import view.info.okButtonListener.InfoButtonListener

import java.awt.event.{ActionEvent, ActionListener}

/**
 * Abstract class used to structure the behaviour of the [[java.awt.event.ActionListener]] in the Info page
 * that does not require a Form.
 *
 * @param infoController A InfoController.
 */
abstract class InfoWithoutFormButtonListener(infoController: InfoController)
  extends ActionListener with InfoButtonListener {

  /**
   * Just run the [[view.info.okButtonListener.InfoButtonListener#performAction()]].
   * No Forms are present here.
   * @param e the action event.
   */
  override def actionPerformed(e: ActionEvent): Unit = performAction()

  override def showError(): Unit = NoSolutionDialog(infoController)

}

