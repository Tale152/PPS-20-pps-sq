package view.info.okButtonListener.forms

import controller.InfoController
import view.form.{Form, OkFormButtonListener}
import view.info.dialog.InfoDialogs.NoSolutionDialog
import view.info.okButtonListener.InfoButtonListener

/**
 * Abstract class used to show always the same dialog on error in all the Form Listeners.
 *
 * @param form           The form.
 * @param infoController A [[controller.InfoController]]
 */
abstract class InfoFormButtonListener(form: Form, infoController: InfoController)
  extends OkFormButtonListener(form, infoController) with InfoButtonListener {

  override def showError(): Unit = NoSolutionDialog(infoController)

}
