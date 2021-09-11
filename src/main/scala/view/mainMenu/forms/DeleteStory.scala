package view.mainMenu.forms

import controller.ApplicationController
import view.form.{Form, FormBuilder}

object DeleteStory {

  def showDeleteStoryForm(applicationController: ApplicationController, comboElements: List[String]): Unit = {
    val form: Form = FormBuilder()
      .addComboField("Select the story to delete:", comboElements)
      .get(applicationController)
    form.setOkButtonListener(DeleteStoryConfirmListener(form, applicationController, comboElements))
    form.render()
  }

}
