package view.explorer.forms

import controller.ExplorerController
import view.form.{Form, FormBuilder}
import view.explorer.okButtonListener.forms

object OutcomeFromId {

  def showOutcomeFromIdForm(explorerController: ExplorerController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Select the origin story node id")
      .get(explorerController)
    form.setOkButtonListener(forms.OutcomeFromIdListener(form, explorerController))
    form.render()
  }

}
