package view.info.forms

import controller.InfoController
import view.form.{Form, FormBuilder}
import view.info.okButtonListener.StoryWalkthroughFromIdOkListener

object StoryWalkthroughFromId {
  def showStoryWalkthroughFromIdForm(infoController: InfoController): Unit = {
    val form: Form = FormBuilder()
      .addIntegerField("Select the origin story node id")
      .get(infoController)
    form.setOkButtonListener(StoryWalkthroughFromIdOkListener(form, infoController))
    form.render()
  }
}
