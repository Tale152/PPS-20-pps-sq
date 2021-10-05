package view.explorer.okButtonListener.forms

import controller.ExplorerController
import view.form.{Form, OkFormButtonListener}
import view.explorer.ExplorerDialogs.noSolutionDialog
import view.explorer.okButtonListener.ExplorerButtonListener

/**
 * Abstract class used to show always the same dialog on error in all the Form Listeners.
 *
 * @param form               The form.
 * @param explorerController A [[controller.ExplorerController]]
 */
abstract class ExplorerFormButtonListener(form: Form, explorerController: ExplorerController)
  extends OkFormButtonListener(form, explorerController) with ExplorerButtonListener {

  override def showError(): Unit = noSolutionDialog(explorerController)

}
