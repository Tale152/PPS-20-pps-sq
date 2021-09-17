package view.info.forms

import controller.InfoController
import view.form.{Form, FormBuilder}
import view.info.okButtonListener.OutcomeFromIdOkListener

object OutcomeFromId {

  def showOutcomeFromIdForm(infoController: InfoController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Select the origin story node id")
      .get(infoController)
    form.setOkButtonListener(OutcomeFromIdOkListener(form, infoController))
    form.render()
  }

}
