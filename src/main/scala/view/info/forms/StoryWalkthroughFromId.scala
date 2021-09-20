package view.info.forms

import controller.InfoController
import view.form.{Form, FormBuilder}
import view.info.okButtonListener.forms

object StoryWalkthroughFromId {
  def showStoryWalkthroughFromIdForm(infoController: InfoController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Select the origin story node id")
      .get(infoController)
    form.setOkButtonListener(forms.StoryWalkthroughFromIdListener(form, infoController))
    form.render()
  }
}
