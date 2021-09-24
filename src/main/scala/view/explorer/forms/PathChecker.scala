package view.explorer.forms

import controller.ExplorerController
import view.form.{Form, FormBuilder}
import view.explorer.okButtonListener.forms

object PathChecker {

  def showPathCheckerForm(explorerController: ExplorerController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Select the origin story node id")
      .addIntegerField("Select the end story node id")
      .get(explorerController)
    form.setOkButtonListener(forms.PathCheckerListener(form, explorerController))
    form.render()
  }

}