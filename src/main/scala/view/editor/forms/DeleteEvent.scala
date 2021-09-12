package view.editor.forms

import controller.editor.EditorController
import view.editor.forms.okButtonListener.DeleteEventOkListener.SelectStoryNodeOkListener
import view.form.{Form, FormBuilder}

object DeleteEvent {

  def showDeleteEventForm(editorController: EditorController): Unit = {
    val form: Form = FormBuilder()
      .addComboField(
        "Which story node contains the event? (id)",
        editorController.getNodesIds(n => n.events.nonEmpty).map(id => id.toString)
      )
      .get(editorController)
    form.setOkButtonListener(SelectStoryNodeOkListener(form, editorController))
    form.render()
  }
}
