package controller.util.serialization

import view.util.scalaQuestSwingComponents.SqSwingButton
import view.util.scalaQuestSwingComponents.dialog.SqSwingDialog

object DeserializerChecker {

  /**
   * Checks if a file matches certain characteristics,
   * otherwise displays an [[view.util.scalaQuestSwingComponents.dialog.SqSwingDialog]]
   *
   * @param action      The action the caller is trying to perform.
   * @param dialogTitle the title of the dialog displayed in case the action throws exceptions.
   */
  def checkOnLoadingFile(action: () => Unit, failAction: () => Unit): Unit = {
    try {
      action()
    } catch {
      case _: Exception => failAction()
    }
  }

}
