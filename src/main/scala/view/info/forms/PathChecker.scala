package view.info.forms

import controller.InfoController
import view.form.{Form, FormBuilder}
import view.info.okButtonListener.forms

object PathChecker {

  def showPathCheckerForm(infoController: InfoController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Select the origin story node id")
      .addIntegerField("Select the end story node id")
      .get(infoController)
    form.setOkButtonListener(forms.PathCheckerListener(form, infoController))
    form.render()
  }

}